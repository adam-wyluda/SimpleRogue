package simplerogue.world.ui;

import com.google.common.base.Optional;
import simplerogue.engine.action.Action;
import simplerogue.engine.ai.util.vision.FOVUtil;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.LevelUtil;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.game.GameLayer;
import simplerogue.engine.view.Key;
import simplerogue.world.action.AttackAction;
import simplerogue.world.game.DefaultGameConfiguration;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.creature.Player;
import simplerogue.world.level.item.Bow;

/**
 * @author Adam Wyłuda
 */
public class GameActionProvider implements GameLayer.ActionProvider<Player>
{
    @Override
    public Optional<Action> interpret(Player player, Key key)
    {
        Optional<Action> actionOptional = Optional.absent();

        if (Direction.isDirection(key))
        {
            Direction direction = Direction.fromKey(key);

            actionOptional = attack(player, direction);
        }

        if (!actionOptional.isPresent() && key == Key.ACTION_1)
        {
            actionOptional = rangedAttack(player);
        }

        return actionOptional;
    }

    private Optional<Action> rangedAttack(Player player)
    {
        Optional<Action> actionOptional = Optional.absent();

        if (player.getEquippedWeapon().isPresent() && player.getEquippedWeapon().get().is(Bow.class))
        {
            GameManager gameManager = Managers.get(GameManager.class);
            DefaultGameConfiguration config = gameManager.getGame().getConfig().reify(DefaultGameConfiguration.class);

            Optional<Creature> selectedCreature = config.getSelectedCreature();

            if (selectedCreature.isPresent() && isTargetOnSight(player, selectedCreature.get()))
            {
                Creature creature = selectedCreature.get();
                Action action = AttackAction.create(player, creature);
                actionOptional = Optional.of(action);
            }
        }

        return actionOptional;
    }

    private boolean isTargetOnSight(Player player, Creature target)
    {
        Bow bow = player.getEquippedWeapon().get().reify(Bow.class);

        return LevelUtil.euclideanDistance(player, target) <= bow.getRange() &&
                FOVUtil.isOnSight(player, target);
    }

    private Optional<Action> attack(Player player, Direction direction)
    {
        Action action = AttackAction.create(player, direction);

        return Optional.of(action);
    }
}
