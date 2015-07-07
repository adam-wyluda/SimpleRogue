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
public class Armor extends Item
{
    public static final String NAME = "armor";

    private static final String DEFENSE = "defense";

    private int defense;

    @Override
    public void load(Model model)
    {
        super.load(model);

        defense = model.getInt(DEFENSE);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(DEFENSE, defense);

        return save;
    }
}
