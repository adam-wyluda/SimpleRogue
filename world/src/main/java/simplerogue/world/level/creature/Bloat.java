package simplerogue.world.level.creature;

import simplerogue.engine.level.Effect;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.Prototype;

/**
 * @author Adam Wyłuda
 */
public class Bloat extends Creature
{
    public static final String NAME = "bloat";

    private static final String BLOAT_EFFECT = "bloat_effect";

    private Prototype bloatEffectPrototype;

    public Effect createEffect()
    {
        return bloatEffectPrototype.create();
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        bloatEffectPrototype = model.getPrototype(BLOAT_EFFECT);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.putPrototype(BLOAT_EFFECT, bloatEffectPrototype);

        return save;
    }
}
