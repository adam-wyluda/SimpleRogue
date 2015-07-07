package simplerogue.world.ai;

import com.google.common.base.Optional;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.LevelUtil;
import simplerogue.engine.util.RandomUtil;
import simplerogue.world.action.CreatureHelper;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.effect.Fire;

/**
 * @author Adam Wyłuda
 */
public class FireHandler extends ExpandingEffectHandler<Fire>
{
    public static final String NAME = "fire_handler";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    protected void applyEffect(Fire fire)
    {
        Field field = fire.getField();
        Optional<Creature> creatureOptional = field.lookupElement(Creature.class);

        if (creatureOptional.isPresent())
        {
            Creature creature = creatureOptional.get();

            int damage = fire.getTemperature();

            CreatureHelper.dealDamage(creature, damage);
        }
    }

    @Override
    protected void tryExpand(Fire fire, Field field)
    {
        int intensity = fire.getIntensity() - fire.getIgniteIntensityModifier();

        if (intensity > 0)
        {
            double igniteChance = fire.getIgniteChance();
            igniteChance = LevelUtil.enhanceChanceWithBlockingCount(field, igniteChance);

            if (RandomUtil.randomBoolean(igniteChance))
            {
                Fire newFire = fire.getPrototype().create();

                newFire.setIntensity(intensity);
                newFire.setTemperature(fire.getTemperature());
                newFire.setIgniteChance(fire.getIgniteChance());

                field.putElement(newFire);
            }
        }
    }
}
