package simplerogue.world.level.effect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Fire extends ExpandingEffect
{
    public static final String NAME = "fire";

    private static final String TEMPERATURE = "temperature";
    private static final String IGNITE_CHANCE = "ignite_chance";
    private static final String IGNITE_INTENSITY_MODIFIER = "ignite_intensity_modifier";

    private int temperature;
    private double igniteChance;
    private int igniteIntensityModifier;

    @Override
    public void load(Model model)
    {
        super.load(model);

        temperature = model.getInt(TEMPERATURE);
        igniteChance = model.getDouble(IGNITE_CHANCE);
        igniteIntensityModifier = model.getInt(IGNITE_INTENSITY_MODIFIER);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(TEMPERATURE, temperature);
        save.put(IGNITE_CHANCE, igniteChance);
        save.put(IGNITE_INTENSITY_MODIFIER, igniteIntensityModifier);

        return save;
    }
}
