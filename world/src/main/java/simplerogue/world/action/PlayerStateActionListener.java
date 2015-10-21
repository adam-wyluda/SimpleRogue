package simplerogue.world.action;

import simplerogue.engine.action.ActionManager;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class PlayerStateActionListener implements ActionManager.Listener
{
    @Override
    public void turnPerformed()
    {
        checkIfPlayerGainedSkillPoint();
    }

    private void checkIfPlayerGainedSkillPoint()
    {
        Player player = Managers.get(GameManager.class).getPlayer();

        player.tryLevelUp();
    }
}
