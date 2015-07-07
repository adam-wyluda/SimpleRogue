package simplerogue.engine.ai;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.action.Action;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.level.Actor;
import simplerogue.engine.manager.Managers;

/**
 * Player's AI.
 *
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlayerAI extends AbstractAI<Actor>
{
    private static final Logger LOG = LoggerFactory.getLogger(PlayerAI.class);

    public static final String NAME = "player_ai";

    /**
     * Action performed by player in next turn.
     *
     * @see simplerogue.engine.game.GameManager#getPlayer()
     */
    private Action nextAction;

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public AIInterest getInterest()
    {
        return AIInterest.NONE;
    }

    @Override
    public void makeTurn(Actor player)
    {
        LOG.debug("Making turn for player, nextAction: {}", nextAction.getClass().getSimpleName());

        if (nextAction != null)
        {
            Managers.get(ActionManager.class).submitAction(nextAction);
        }
    }

    @Override
    public void actionPerformed(Actor player, Action action)
    {
    }
}
