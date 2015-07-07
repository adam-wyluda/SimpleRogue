package simplerogue.world.ai;

import simplerogue.engine.level.Field;
import simplerogue.engine.level.LevelUtil;
import simplerogue.engine.util.RandomUtil;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.effect.Gas;
import simplerogue.world.level.status.Poisoned;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class GasHandler extends ExpandingEffectHandler<Gas>
{
    public static final String NAME = "gas_handler";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    protected void applyEffect(Gas gas)
    {
        List<Creature> creatures = gas.getField().lookupElements(Creature.class);

        for (Creature creature : creatures)
        {
            Poisoned poisoned = Poisoned.create();
            poisoned.setStrength(gas.getStrength());
            poisoned.setConcentration(gas.getConcentration());

            creature.getModifiers().add(poisoned);
        }

        gas.lowerExpandIntensity();
    }

    @Override
    protected void tryExpand(Gas gas, Field field)
    {
        double expandChance = gas.getExpandChance();
        expandChance = LevelUtil.enhanceChanceWithBlockingCount(field, expandChance);

        if (gas.getExpandIntensity() > 0 && RandomUtil.randomBoolean(expandChance))
        {
            Gas newGas = gas.getPrototype().create();

            newGas.setIntensity(gas.getIntensity());
            newGas.setExpandIntensity(gas.getExpandIntensity());

            field.putElement(newGas);

            expand(newGas);
        }
    }
}
