package simplerogue.world.action;

import simplerogue.engine.action.ActionManager;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.ui.game.GameOverMenuLayer;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class GameOverActionListener implements ActionManager.Listener
{
    @Override
    public void turnPerformed()
    {
        checkIfGameOver();
    }

    private void checkIfGameOver()
    {
        Player player = Managers.get(GameManager.class).getPlayer();

        if (player.getHp() <= 0)
        {
            UIManager uiManager = Managers.get(UIManager.class);

            GameOverMenuLayer gameOverLayer = uiManager.getLayer(GameOverMenuLayer.NAME);
            gameOverLayer.setVisible(true);
            uiManager.setActiveLayer(gameOverLayer);

            Managers.get(GameManager.class).gameOver();
        }
    }
}
