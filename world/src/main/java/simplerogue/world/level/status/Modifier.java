package simplerogue.world.level.status;

import lombok.Data;
import simplerogue.engine.object.Loadable;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.Named;
import simplerogue.world.level.creature.Creature;

/**
 * @author Adam Wyłuda
 */
@Data
public abstract class Modifier implements Loadable, Named
{
    private static final String NAME = "name";

    private String name;

    public static <M extends Modifier> M create(Model model)
    {
        String name = model.getString(NAME);

        switch (name)
        {
            default:
            case Poisoned.NAME:
                return (M) Poisoned.create(model);
        }
    }

    /**
     * Apply modifier to player.
     *
     * @return false if modifier should be removed
     */
    public abstract boolean apply(Creature creature);

    @Override
    public void load(Model model)
    {
        name = model.getString(NAME);
    }

    @Override
    public Model save()
    {
        Model save = new Model();

        save.put(NAME, name);

        return save;
    }
}
