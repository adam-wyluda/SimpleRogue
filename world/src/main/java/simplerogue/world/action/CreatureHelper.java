package simplerogue.world.action;

import simplerogue.engine.game.GameUtil;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class CreatureHelper
{

    /**
     * Implements formula for dealing damage.
     *
     * @return If target was killed.
     */
    public static boolean dealDamage(Creature target, int damage)
    {
        int finalHp = target.getHp() - damage;
        target.setHp(finalHp);

        boolean killed = finalHp <= 0;
        if (killed)
        {
            GameUtil.removeFromGame(target);
        }

        return killed;
    }

    public static void levelUp(Player player)
    {
    }
}
