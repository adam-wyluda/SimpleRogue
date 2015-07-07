package simplerogue.world.ai;

import com.google.common.base.Optional;
import simplerogue.engine.action.Action;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.action.MoveAction;
import simplerogue.engine.ai.AbstractAI;
import simplerogue.engine.ai.util.pathfinding.PathUtil;
import simplerogue.engine.ai.util.vision.FOVUtil;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.LevelUtil;
import simplerogue.engine.level.Portal;
import simplerogue.engine.manager.Managers;
import simplerogue.world.action.AttackAction;
import simplerogue.world.level.creature.Monster;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class MonsterAI extends AbstractAI<Monster>
{
    public static final String NAME = "monster_ai";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void makeTurn(Monster monster)
    {
        ActionManager actionManager = Managers.get(ActionManager.class);
        Action action;

        switch (monster.getAiData().getMode())
        {
            default:
            case WANDERING:
                action = wander(monster);
                break;
            case CHASING_PLAYER:
                action = chasePlayer(monster);
                break;
        }

        actionManager.submitAction(action);
    }

    private Action wander(Monster monster)
    {
        Action action;

        Optional<Player> playerOptional = monster.getLevel().lookupElement(Player.class);

        if (playerOptional.isPresent() && FOVUtil.isOnSight(monster, playerOptional.get()))
        {
            playerSeen(monster);

            action = interactWithPlayer(monster, playerOptional.get());
        }
        else
        {
            action = randomMove(monster);
        }

        return action;
    }

    private Action chasePlayer(Monster monster)
    {
        Action action;

        Optional<Player> playerOptional = monster.getLevel().lookupElement(Player.class);

        if (playerOptional.isPresent())
        {
            Player player = playerOptional.get();

            if (LevelUtil.euclideanDistance(monster, player) <= monster.getPerception() &&
                    FOVUtil.isOnSight(monster, player))
            {
                playerSeen(monster);
            }
            else
            {
                increaseFatigue(monster);
            }

            action = interactWithPlayer(monster, player);
        }
        else
        {
            action = randomMove(monster);
        }

        // Monster gets bored
        if (isFatigued(monster))
        {
            monster.getAiData().setMode(MonsterAIData.Mode.WANDERING);
        }

        return action;
    }

    private Action interactWithPlayer(Monster monster, Player player)
    {
        Action action;

        Direction directionToPlayer = LevelUtil.directionFrom(monster, player);
        Field monsterField = monster.getField();
        Field nearField = directionToPlayer.transform(monsterField);

        if (nearField.getElements().contains(player))
        {
            action = AttackAction.create(monster, player);
        }
        else
        {
            action = moveTo(monster, player);
        }

        return action;
    }

    private Action moveTo(Monster monster, Player player)
    {
        MoveAction action;
        Optional<MoveAction> actionOptional = PathUtil.moveTo(monster, player);

        if (actionOptional.isPresent())
        {
            action = actionOptional.get();
        }
        else
        {
            action = randomMove(monster);
        }

        return action;
    }

    private MoveAction randomMove(Monster monster)
    {
        decreaseFatigue(monster);

        Direction direction = Direction.random();

        // Don't move into portals
        if (direction.transform(monster.getField()).is(Portal.class))
        {
            direction = Direction.C;
        }

        return MoveAction.create(monster, direction);
    }

    private void playerSeen(Monster monster)
    {
        monster.getAiData().setMode(MonsterAIData.Mode.CHASING_PLAYER);
    }

    private boolean isFatigued(Monster monster)
    {
        return monster.getAiData().getFatigue() > 1.0;
    }

    private void increaseFatigue(Monster monster)
    {
        double fatigue = monster.getAiData().getFatigue();
        fatigue += getFatigueModifer(monster);
        monster.getAiData().setFatigue(fatigue);
    }

    private void decreaseFatigue(Monster monster)
    {
        double fatigue = monster.getAiData().getFatigue();
        fatigue -= getFatigueModifer(monster);
        fatigue = Math.max(0, fatigue);
        monster.getAiData().setFatigue(fatigue);
    }

    private double getFatigueModifer(Monster monster)
    {
        return 1.0 / monster.getStamina();
    }
}
