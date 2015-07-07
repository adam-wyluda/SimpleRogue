package simplerogue.engine.save;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import simplerogue.engine.level.Level;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectUtil;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.resource.Directory;
import simplerogue.engine.resource.MapResource;
import simplerogue.engine.resource.Resource;
import simplerogue.engine.resource.ResourceManager;
import simplerogue.engine.resource.Resources;
import simplerogue.engine.resource.TreeResource;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultSaveManager extends AbstractManager implements SaveManager
{
    private ResourceManager resourceManager;

    @Override
    public void init()
    {
        resourceManager = Managers.get(ResourceManager.class);
    }

    @Override
    public String generateSaveName(String configurator)
    {
        int number = 1;

        List<String> saves = listSaves();
        for (String name : saves)
        {
            if (name.equals(configurator + number))
            {
                number++;
            }
        }

        return configurator + number;
    }

    @Override
    public List<String> listSaves()
    {
        List<Resource> directories = resourceManager.listResources(Resources.SAVES);
        List<String> result = Lists.newArrayListWithCapacity(directories.size());

        for (Resource dir : directories)
        {
            if (dir.is(Directory.class))
            {
                String name = dir.getName();
                result.add(name);
            }
        }

        return result;
    }

    @Override
    public GameSave loadGame(String saveName)
    {
        GameSave save = new GameSave();
        save.setName(saveName);

        Directory saveDirectory = getSaveDirectory(saveName);

        TreeResource saveProperties = saveDirectory.getChild(Resources.SAVE_PROPERTIES).reify(TreeResource.class);
        Model saveModel = saveProperties.readModel();
        save.setProperties(saveModel);

        Map<String, GameSave.LevelSave> levelSaves = Maps.newHashMap();
        Directory levelsDirectory = saveDirectory.getChild(Resources.LEVELS).reify(Directory.class);
        for (Resource resource : levelsDirectory.getChildren())
        {
            Directory levelDirectory = resource.reify(Directory.class);
            String name = levelDirectory.getName();
            GameSave.LevelSave levelSave = readLevelSave(levelDirectory);

            levelSaves.put(name, levelSave);
        }
        save.setLevelSaves(levelSaves);

        return save;
    }

    private GameSave.LevelSave readLevelSave(Directory levelDirectory)
    {
        GameSave.LevelSave levelSave = new GameSave.LevelSave();

        TreeResource propertiesResource = levelDirectory.getChild(Resources.LEVEL_PROPERTIES).reify(TreeResource.class);
        Model properties = propertiesResource.readModel();
        levelSave.setProperties(properties);

        MapResource mapResource = levelDirectory.getChild(Resources.LEVEL_MAP).reify(MapResource.class);
        int height = properties.getInt(Level.HEIGHT);
        int width = properties.getInt(Level.WIDTH);
        int[][] map = mapResource.readMap(height, width);
        levelSave.setMap(map);

        TreeResource objectsResource = levelDirectory.getChild(Resources.LEVEL_OBJECTS).reify(TreeResource.class);
        List<Model> objectModels = objectsResource.readModelArray();
        Map<Integer, Model> objectProperties = Maps.newHashMap();
        for (Model model : objectModels)
        {
            int id = ObjectUtil.getId(model);

            objectProperties.put(id, model);
        }
        levelSave.setObjectProperties(objectProperties);

        return levelSave;
    }

    @Override
    public void saveGame(GameSave save)
    {
        String savePath = getSavePath(save.getName());
        Directory saveDirectory = resourceManager.createResource(savePath).reify(Directory.class);

        TreeResource saveProperties = saveDirectory.getChild(Resources.SAVE_PROPERTIES).reify(TreeResource.class);
        Model saveModel = save.getProperties();
        saveProperties.saveModel(saveModel);

        for (Map.Entry<String, GameSave.LevelSave> entry : save.getLevelSaves().entrySet())
        {
            String levelName = entry.getKey();
            GameSave.LevelSave levelSave = entry.getValue();
            Directory levelDirectory = getLevelDirectory(save.getName(), levelName);

            writeLevelSave(levelSave, levelDirectory);
        }
    }

    @Override
    public void deleteSave(String name)
    {
        String savePath = getSavePath(name);
        Directory saveDirectory = resourceManager.createResource(savePath).reify(Directory.class);
        saveDirectory.remove();
    }

    private void writeLevelSave(GameSave.LevelSave levelSave, Directory levelDirectory)
    {
        TreeResource propertiesResource = levelDirectory.getChild(Resources.LEVEL_PROPERTIES).reify(TreeResource.class);
        Model properties = levelSave.getProperties();
        propertiesResource.saveModel(properties);

        MapResource mapResource = levelDirectory.getChild(Resources.LEVEL_MAP).reify(MapResource.class);
        int[][] map = levelSave.getMap();
        mapResource.saveMap(map);

        TreeResource objectsResource = levelDirectory.getChild(Resources.LEVEL_OBJECTS).reify(TreeResource.class);
        List<Model> objectProperties = Lists.newArrayList(levelSave.getObjectProperties().values());
        objectsResource.saveModel(objectProperties);
    }

    private Directory getSaveDirectory(String saveName)
    {
        String savePath = getSavePath(saveName);
        Directory saveDirectory = resourceManager.createResource(savePath).reify(Directory.class);

        return saveDirectory;
    }

    private String getSavePath(String saveName)
    {
        return Resources.SAVES + saveName + "/";
    }

    private Directory getLevelDirectory(String saveName, String levelName)
    {
        String levelPath = getLevelPath(saveName, levelName);
        Directory levelDirectory = resourceManager.createResource(levelPath).reify(Directory.class);

        return levelDirectory;
    }

    private String getLevelPath(String saveName, String levelName)
    {
        return getSavePath(saveName) + Resources.LEVELS + levelName + "/";
    }

    @Override
    public List<Prototype> loadPrototypes(String worldName)
    {
        List<Prototype> result = Lists.newArrayList();

        List<Prototype> actors = loadPrototypes(worldName, Resources.ACTOR_PROTOTYPES);
        List<Prototype> effects = loadPrototypes(worldName, Resources.EFFECT_PROTOTYPES);
        List<Prototype> fields = loadPrototypes(worldName, Resources.FIELD_PROTOTYPES);
        List<Prototype> items = loadPrototypes(worldName, Resources.ITEM_PROTOTYPES);

        result.addAll(actors);
        result.addAll(effects);
        result.addAll(fields);
        result.addAll(items);

        return result;
    }

    private List<Prototype> loadPrototypes(String worldName, String resourceName)
    {
        List<Prototype> result = Lists.newArrayList();

        Directory worldDirectory = getWorldDirectory(worldName);

        TreeResource prototypesResource = worldDirectory.getChild(resourceName).reify(TreeResource.class);
        List<Model> models = prototypesResource.readModelArray();

        for (Model model : models)
        {
            Prototype prototype = Prototype.create(model);

            result.add(prototype);
        }

        return result;
    }

    private Directory getWorldDirectory(String worldName)
    {
        String worldPath = getWorldPath(worldName);
        Directory worldDirectory = resourceManager.createResource(worldPath).reify(Directory.class);

        return worldDirectory;
    }

    private String getWorldPath(String worldName)
    {
        return Resources.PROTOTYPES + worldName + "/";
    }
}
