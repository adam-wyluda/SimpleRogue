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
public class Weapon extends Item
{
    public static final String NAME = "weapon";

    private static final String DAMAGE = "damage";

    private int damage;

    @Override
    public void load(Model model)
    {
        super.load(model);

        damage = model.getInt(DAMAGE);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(DAMAGE, damage);

        return save;
    }
}
