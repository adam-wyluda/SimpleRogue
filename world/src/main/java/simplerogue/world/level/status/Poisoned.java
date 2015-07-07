package simplerogue.world.level.status;

import lombok.Data;
import simplerogue.engine.object.Model;
import simplerogue.world.action.CreatureHelper;
import simplerogue.world.level.creature.Creature;

/**
 * @author Adam Wyłuda
 */
@Data
public class Poisoned extends Modifier
{
    public static final String NAME = "poisoned";

    private static final String STRENGTH = "strength";
    private static final String CONCENTRATION = "concentration";

    private int strength;
    private int concentration;

    private Poisoned()
    {
        setName(NAME);
    }

    public static Poisoned create()
    {
        return new Poisoned();
    }

    public static Poisoned create(Model model)
    {
        Poisoned poisoned = create();
        poisoned.load(model);

        return poisoned;
    }

    @Override
    public boolean apply(Creature creature)
    {
        if (creature.is(Creature.class))
        {
            CreatureHelper.dealDamage(creature.reify(Creature.class), strength);
        }

        concentration--;

        return concentration > 0;
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        strength = model.getInt(STRENGTH);
        concentration = model.getInt(CONCENTRATION);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(STRENGTH, strength);
        save.put(CONCENTRATION, concentration);

        return save;
    }
}
