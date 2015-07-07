package simplerogue.world.level.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Potion extends Item
{
    public static final String NAME = "potion";

    private static final String HP_MODIFIER = "hp_modifier";

    private int hpModifier;

    @Override
    public void load(Model model)
    {
        super.load(model);

        hpModifier = model.has(HP_MODIFIER) ? model.getInt(HP_MODIFIER) : 0;
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(HP_MODIFIER, hpModifier);

        return save;
    }
}
