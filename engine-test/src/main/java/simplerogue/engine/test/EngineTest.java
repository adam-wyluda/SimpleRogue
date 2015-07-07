package simplerogue.engine.test;

import org.junit.Before;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.PlatformManager;
import simplerogue.engine.test.platform.TestPlatformManager;
import simplerogue.engine.test.world.TestWorldManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldManager;

/**
 * Base class for engine tests.
 *
 * @author Adam Wyłuda
 */
public abstract class EngineTest
{
    @Before
    public void prepare()
    {
        Managers.register(PlatformManager.class, new TestPlatformManager());
        Managers.register(WorldManager.class, new TestWorldManager());
        registerManagers();

        beforeInit();
        Managers.initAll();
        afterInit();
    }

    protected abstract void registerManagers();

    /**
     * Called before managers have been initiated.
     */
    protected void beforeInit()
    {
    }

    /**
     * Called after managers have been initiated.
     */
    protected void afterInit()
    {
        // Load test world
        World world = Managers.get(WorldManager.class).getWorlds().get(0);
        Managers.get(GameManager.class).loadWorld(world);
    }
}
