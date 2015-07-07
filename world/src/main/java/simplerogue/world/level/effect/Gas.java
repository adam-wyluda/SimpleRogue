package simplerogue.world.level.effect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;
import simplerogue.engine.util.RandomUtil;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Gas extends ExpandingEffect
{
    public static final String NAME = "gas";

    private static final String INTENSITY = "intensity";
    private static final String INTENSITY_MODIFIER_RANGE = "intensity_modifier_range";
    private static final String STRENGTH = "strength";
    private static final String CONCENTRATION = "concentration";
    private static final String EXPAND_CHANCE = "expand_chance";
    private static final String EXPAND_INTENSITY = "expand_intensity";

    private int intensity;
    private int intensityModifierRange;
    private int strength;
    private int concentration;

    private int expandIntensity;
    private double expandChance;

    @Override
    public void lowerIntensity()
    {
        intensity -= RandomUtil.randomInt(intensityModifierRange);
    }

    public void lowerExpandIntensity()
    {
        expandIntensity--;
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        intensity = model.getInt(INTENSITY);
        intensityModifierRange = model.getInt(INTENSITY_MODIFIER_RANGE);
        strength = model.getInt(STRENGTH);
        concentration = model.has(CONCENTRATION) ? model.getInt(CONCENTRATION) : strength;
        expandIntensity = model.getInt(EXPAND_INTENSITY);
        expandChance = model.getDouble(EXPAND_CHANCE);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(INTENSITY, intensity);
        save.put(INTENSITY_MODIFIER_RANGE, intensityModifierRange);
        save.put(STRENGTH, strength);
        save.put(EXPAND_INTENSITY, expandIntensity);
        save.put(EXPAND_CHANCE, expandChance);

        return save;
    }
}
