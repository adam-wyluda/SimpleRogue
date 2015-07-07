package simplerogue.engine.level;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.game.Game;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.levelgenerator.LevelGenerator;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultLevelManager extends AbstractManager implements LevelManager, WorldListener
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultLevelManager.class);

    private LevelGeneratorManager levelGeneratorManager;
    private GameManager gameManager;

    private Map<String, Class<? extends Level>> levelTypeMap;

    @Override
    public void init()
    {
        levelGeneratorManager = Managers.get(LevelGeneratorManager.class);
        gameManager = Managers.get(GameManager.class);
    }

    @Override
    public void worldChanged(World world)
    {
        levelTypeMap = Maps.newHashMap();
        levelTypeMap.putAll(LevelTypes.getMapping());
        levelTypeMap.putAll(world.getLevelTypes());
    }

    @Override
    public Level createLevel(String type)
    {
        LOG.debug("Creating level of type {}", type);

        Level level;

        try
        {
            Class<? extends Level> typeClass = levelTypeMap.get(type);
            level = typeClass.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        level.setType(type);

        return level;
    }

    @Override
    public Portal generateLevel(LevelRequest request)
    {
        LOG.debug("Generating level by request with level type {}", request.getLevelType());

        LevelGenerator generator = levelGeneratorManager.getGenerator(request);

        Level level = generator.createLevel(request);
        registerLevel(level);

        return request.getEntryPortal();
    }

    @Override
    public void moveElement(FieldElement element, Portal nextPortal)
    {
        Level currentLevel = element.getLevel();
        Level nextLevel = nextPortal.getLevel();

        LOG.debug("Moving element {} to {}", element.getId(), nextLevel.getName());

        currentLevel.removeElement(element);

        boolean moved = false;
        for (Direction direction : Direction.NON_IDEMPOTENT)
        {
            Field targetField = direction.transform(nextPortal);

            if (!targetField.isBlocking())
            {
                nextLevel.putElement(element, targetField.getPosY(), targetField.getPosX());
                moved = true;
                break;
            }
        }

        // If all possible directions are blocked, move element to target portal (should send it back)
        if (!moved)
        {
            nextLevel.putElement(element, nextPortal.getPosY(), nextPortal.getPosX());
        }
    }

    @Override
    public void registerLevel(Level level)
    {
        LOG.debug("Registering level {}", level.getName());

        Game game = gameManager.getGame();

        game.getLevelMap().put(level.getName(), level);
    }

    @Override
    public Iterable<Level> getLevels()
    {
        Game game = gameManager.getGame();

        return game.getLevelMap().values();
    }

    @Override
    public Level getLevel(String name)
    {
        Game game = gameManager.getGame();

        if (game != null)
        {
            return game.getLevelMap().get(name);
        }

        return null;
    }

    @Override
    public String generateLevelName(String generator)
    {
        int number = 0;

        Level level;
        do
        {
            number++;
            level = getLevel(generator + number);
        }
        while (level != null);

        return generator + number;
    }
}
