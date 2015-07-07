package simplerogue.engine.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.level.ActiveField;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.Item;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.level.LevelUtil;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectInstance;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.ObjectUtil;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.save.GameSave;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.world.World;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultGameManager extends AbstractManager implements GameManager
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultGameManager.class);

    private LevelManager levelManager;
    private ObjectManager objectManager;
    private SaveManager saveManager;

    // World
    private World world;
    private Map<String, GameConfigurator> configurators;

    // Game
    private Game game;

    @Override
    public void init()
    {
        levelManager = Managers.get(LevelManager.class);
        objectManager = Managers.get(ObjectManager.class);
        saveManager = Managers.get(SaveManager.class);
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public void loadWorld(World world)
    {
        LOG.info("Loading world {}", world.getName());

        this.world = world;
        GameUtil.notifyAboutWorldChange(world);

        world.configureGame();

        configurators = Maps.newHashMap();
        for (GameConfigurator configurator : world.getConfigurators())
        {
            String name = configurator.getName();

            configurators.put(name, configurator);
        }
    }

    @Override
    public List<GameConfigurator> getConfigurators()
    {
        return Lists.newArrayList(configurators.values());
    }

    @Override
    public GameConfigurator getConfigurator()
    {
        String name = game.getConfig().getName();
        GameConfigurator configurator = getConfigurator(name);

        return configurator;
    }

    @Override
    public GameConfigurator getConfigurator(String name)
    {
        return configurators.get(name);
    }

    @Override
    public Game getGame()
    {
        return game;
    }

    @Override
    public <T extends Actor> T getPlayer()
    {
        Actor player = game.getConfig().getPlayer();

        return (T) player;
    }

    @Override
    public Game newGame(GameConfiguration config)
    {
        LOG.info("Starting new game");

        objectManager.cleanup();

        String name = config.getName();
        GameConfigurator configurator = getConfigurator(name);

        Game game = configurator.configureGame(config);
        this.game = game;

        return game;
    }

    @Override
    public Game loadGame(String saveName)
    {
        LOG.info("Loading game {}", saveName);

        objectManager.cleanup();

        GameSave save = saveManager.loadGame(saveName);
        String configuratorName = GameUtil.getConfiguratorName(save);
        GameConfigurator configurator = getConfigurator(configuratorName);

        GameConfiguration config = configurator.createInitialConfiguration();
        Map<String, Level> levelMap = Maps.newHashMap();

        Game game = new Game();
        this.game = game;
        game.setName(saveName);
        game.setLevelMap(levelMap);
        game.setConfig(config);

        // Instantiate levels and objects
        for (Map.Entry<String, GameSave.LevelSave> entry : save.getLevelSaves().entrySet())
        {
            String name = entry.getKey();
            GameSave.LevelSave levelSave = entry.getValue();

            Level level = instantiateLevel(name, levelSave);
            levelMap.put(name, level);
        }

        // After all objects have been registered, load their properties
        List<ObjectInstance> objects = objectManager.getAllObjects();
        Map<Integer, Model> models = getAllModels(save.getLevelSaves().values());
        loadObjects(objects, models);

        config.load(save.getProperties());

        return game;
    }

    private Level instantiateLevel(String name, GameSave.LevelSave levelSave)
    {
        Model properties = levelSave.getProperties();
        int[][] map = levelSave.getMap();
        Map<Integer, Model> objectProperties = levelSave.getObjectProperties();

        String type = LevelUtil.getLevelType(properties);
        Level level = levelManager.createLevel(type);
        level.setName(name);
        level.load(properties);

        Map<Integer, Map<Integer, ActiveField>> activeFields = Maps.newHashMap();

        readFieldArea(level, map, activeFields);
        instantiateObjects(level, objectProperties, activeFields);

        return level;
    }

    private void readFieldArea(Level level,
                               int[][] map,
                               // FIXME How about Map<Point, ActiveField>?
                               Map<Integer, Map<Integer, ActiveField>> activeFields)
    {
        int height = LevelUtil.getHeight(map);
        int width = LevelUtil.getWidth(map);
        FieldArea fieldArea = FieldArea.create(height, width);

        level.setArea(fieldArea);

        for (int y = 0; y < map.length; y++)
        {
            for (int x = 0; x < map[0].length; x++)
            {
                int fieldId = map[y][x];
                Field field = objectManager.createField(fieldId);

                level.putField(field, y, x);

                if (field.is(ActiveField.class))
                {
                    ActiveField activeField = field.reify(ActiveField.class);

                    if (!activeFields.containsKey(y))
                    {
                        activeFields.put(y, Maps.<Integer, ActiveField>newHashMap());
                    }
                    activeFields.get(y).put(x, activeField);
                }
            }
        }
    }

    private void instantiateObjects(Level level,
                                    Map<Integer, Model> objectProperties,
                                    Map<Integer, Map<Integer, ActiveField>> activeFields)
    {
        for (Map.Entry<Integer, Model> entry : objectProperties.entrySet())
        {
            int id = entry.getKey();
            Model model = entry.getValue();
            ObjectInstance object = null;

            int posY = LevelUtil.getPosY(model);
            int posX = LevelUtil.getPosX(model);

            // If this object is ActiveField
            Map<Integer, ActiveField> xFields = activeFields.get(posY);
            if (xFields != null)
            {
                object = xFields.get(posX);
            }

            // FIXME Load objects which might be active field elements!
            // if (object == null || object.type != type from model) ??
            if (object == null)
            {
                String prototypeName = ObjectUtil.getPrototypeName(model);
                object = objectManager.createObject(prototypeName, false);
            }

            object.setId(id);
            objectManager.registerObject(object);

            if (object.is(FieldElement.class) && posX >= 0)
            {
                FieldElement fieldElement = object.reify(FieldElement.class);

                level.putElement(fieldElement, posY, posX);
            }
        }
    }

    private void loadObjects(List<ObjectInstance> objects, Map<Integer, Model> models)
    {
        for (ObjectInstance object : objects)
        {
            int id = object.getId();
            Model objectModel = models.get(id);

            Prototype prototype = ObjectUtil.getPrototype(objectModel);
            Model prototypeModel = prototype.getModel();

            Model finalModel = prototypeModel.merge(objectModel);

            object.load(finalModel);
        }
    }

    private Map<Integer, Model> getAllModels(Collection<GameSave.LevelSave> levelSaves)
    {
        Map<Integer, Model> result = Maps.newHashMap();

        for (GameSave.LevelSave levelSave : levelSaves)
        {
            result.putAll(levelSave.getObjectProperties());
        }

        return result;
    }

    @Override
    public void saveGame()
    {
        GameSave save = new GameSave();

        if (!game.isSaved())
        {
            String configurator = game.getConfig().getName();
            String newName = saveManager.generateSaveName(configurator);

            game.setName(newName);
        }

        LOG.debug("Saving game {}", game.getName());
        save.setName(game.getName());

        Model properties = game.getConfig().save();
        save.setProperties(properties);

        Map<String, GameSave.LevelSave> levels = saveLevels();
        save.setLevelSaves(levels);

        saveManager.saveGame(save);
    }

    @Override
    public void gameOver()
    {
        LOG.info("Game over");

        if (game.isSaved())
        {
            saveManager.deleteSave(game.getName());
        }
    }

    @Override
    public boolean isGameLoaded()
    {
        return game != null;
    }

    private Map<String, GameSave.LevelSave> saveLevels()
    {
        Map<String, GameSave.LevelSave> result = Maps.newHashMap();

        for (Level level : levelManager.getLevels())
        {
            String name = level.getName();
            GameSave.LevelSave levelSave = saveLevel(level);

            result.put(name, levelSave);
        }

        return result;
    }

    private GameSave.LevelSave saveLevel(Level level)
    {
        GameSave.LevelSave levelSave = new GameSave.LevelSave();

        Model properties = level.save();
        LevelUtil.setLevelType(properties, level.getType());
        levelSave.setProperties(properties);

        int[][] map = readMap(level);
        levelSave.setMap(map);

        Map<Integer, Model> objectProperties = readObjectProperties(level);
        levelSave.setObjectProperties(objectProperties);

        return levelSave;
    }

    private int[][] readMap(Level level)
    {
        FieldArea fieldArea = level.getArea();
        int height = fieldArea.getHeight();
        int width = fieldArea.getWidth();

        int[][] map = new int[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Field field = fieldArea.getFieldAt(y, x);
                int fieldId = field.getFieldId();

                map[y][x] = fieldId;
            }
        }

        return map;
    }

    private Map<Integer, Model> readObjectProperties(Level level)
    {
        Map<Integer, Model> result = Maps.newHashMap();

        List<ObjectInstance> objects = level.getObjects();
        for (ObjectInstance object : objects)
        {
            int id = object.getId();
            Model model = readObjectProperties(object);

            result.put(id, model);

            if (object.is(Actor.class))
            {
                Actor actor = object.reify(Actor.class);

                for (Item item : actor.getItems())
                {
                    int itemId = item.getId();
                    Model itemModel = readObjectProperties(item);

                    result.put(itemId, itemModel);
                }
            }
        }

        return result;
    }

    private Model readObjectProperties(ObjectInstance object)
    {
        int id = object.getId();
        Model model = object.save();

        Prototype prototype = object.getPrototype();
        Model prototypeModel = prototype.getModel();
        Model finalModel = model.diff(prototypeModel);

        // These properties must be retained after diff operation
        ObjectUtil.setId(finalModel, id);
        ObjectUtil.setPrototypeName(finalModel, prototype.getName());

        return finalModel;
    }
}
