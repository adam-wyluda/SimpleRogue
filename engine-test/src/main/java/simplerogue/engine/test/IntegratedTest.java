package simplerogue.engine.test;

import simplerogue.engine.game.GameManager;
import simplerogue.engine.EngineConfigurator;
import simplerogue.engine.manager.Managers;

/**
 * Test in real engine environment.
 *
 * @author Adam Wyłuda
 */
public class IntegratedTest extends EngineTest
{
    @Override
    protected void registerManagers()
    {
        EngineConfigurator.registerDefaultManagers();
    }

    @Override
    protected void afterInit()
    {
        super.afterInit();

        GameManager gameManager = Managers.get(GameManager.class);
        gameManager.loadWorld(TestHelper.getWorld());
    }
}
