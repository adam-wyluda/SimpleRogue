package simplerogue.engine.test;

import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;

/**
 * Test that requires loaded game.
 *
 * @author Adam Wyłuda
 */
public class GameTest extends IntegratedTest
{
    @Override
    protected void afterInit()
    {
        super.afterInit();

        GameManager gameManager = Managers.get(GameManager.class);
        gameManager.loadGame(TestConsts.SAVE_NAME);
    }
}
