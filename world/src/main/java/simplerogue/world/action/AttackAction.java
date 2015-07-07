package simplerogue.world.action;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import simplerogue.engine.action.AbstractAction;
import simplerogue.engine.game.GameUtil;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelUtil;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class AttackAction extends AbstractAction<Creature>
{
    private int targetY;
    private int targetX;

    private AttackAction(Creature attacker, int targetY, int targetX)
    {
        super(attacker);

        this.targetY = targetY;
        this.targetX = targetX;
    }

    public static AttackAction create(Creature attacker, int targetY, int targetX)
    {
        Preconditions.checkNotNull(attacker);

        return new AttackAction(attacker, targetY, targetX);
    }

    public static AttackAction create(Creature attacker, Direction direction)
    {
        Preconditions.checkNotNull(attacker);

        int targetY = direction.transformY(attacker.getPosY());
        int targetX = direction.transformX(attacker.getPosX());

        return new AttackAction(attacker, targetY, targetX);
    }

    public static AttackAction create(Creature attacker, Creature target)
    {
        Preconditions.checkNotNull(attacker);

        int targetY = target.getPosY();
        int targetX = target.getPosX();

        return new AttackAction(attacker, targetY, targetX);
    }

    @Override
    public void perform()
    {
        Creature attacker = getOrigin();

        Level level = attacker.getLevel();
        Field targetField = level.getArea().getFieldAt(targetY, targetX);
        Optional<Creature> targetOptional = targetField.lookupElement(Creature.class);

        if (targetOptional.isPresent())
        {
            Creature target = targetOptional.get();

            int damage = 0;

            // If target is just near attacker, add strength
            Direction directionToPlayer = LevelUtil.directionFrom(attacker, target);
            Field monsterField = attacker.getField();
            Field nearField = directionToPlayer.transform(monsterField);
            if (nearField.getElements().contains(target))
            {
                int attackerStrength = attacker.getStrength();
                damage += attackerStrength;
            }

            if (attacker.is(Player.class))
            {
                Player player = attacker.reify(Player.class);

                if (player.getEquippedWeapon().isPresent())
                {
                    damage += player.getEquippedWeapon().get().getDamage();
                }
            }

            if (target.is(Player.class))
            {
                Player player = target.reify(Player.class);

                if (player.getEquippedArmor().isPresent())
                {
                    damage -= player.getEquippedArmor().get().getDefense();
                    damage = Math.max(0, damage);
                }
            }

            String message = String.format(
                    "@(BLUE|)%s@(GRAY|) has dealt @(YELLOW|)%d@(GRAY|) damage to @(RED|)%s",
                    attacker.getClass().getSimpleName(),
                    damage,
                    target.getClass().getSimpleName());
            GameUtil.appendMessage(message);

            if (CreatureHelper.dealDamage(target, damage))
            {
                message = String.format(
                        "@(BLUE|)%s@(GRAY|) killed @(RED|)%s",
                        attacker.getClass().getSimpleName(),
                        target.getClass().getSimpleName());
                GameUtil.appendMessage(message);
            }
        }
    }
}
