package simplerogue.world.ai;

import simplerogue.engine.ai.AbstractItemHandler;
import simplerogue.engine.level.Actor;
import simplerogue.engine.object.ObjectInstance;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.item.Potion;

/**
 * @author Adam Wyłuda
 */
public class PotionHandler extends AbstractItemHandler<Potion>
{
    public static final String NAME = "potion_handler";

    @Override
    public void useItem(Potion potion, Actor user, ObjectInstance target)
    {
        if (target.is(Creature.class))
        {
            Creature creature = target.reify(Creature.class);

            int hp = creature.getHp();
            hp += potion.getHpModifier();
            hp = hp > creature.getMaxHp() ? creature.getMaxHp() : hp;

            creature.setHp(hp);
        }

        user.getItems().remove(potion);
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
