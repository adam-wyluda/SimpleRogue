package simplerogue.world.level.effect;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Effect;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExpandingEffect extends Effect
{
    private static final String INTENSITY = "intensity";

    private int intensity;

    public void lowerIntensity()
    {
        intensity--;
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        intensity = model.getInt(INTENSITY);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(INTENSITY, intensity);

        return save;
    }
}
