package simplerogue.world.level.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Bow extends Weapon
{
    public static final String NAME = "bow";

    private static final String RANGE = "range";

    private int range;

    @Override
    public void load(Model model)
    {
        super.load(model);

        range = model.getInt(RANGE);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(RANGE, range);

        return save;
    }
}
