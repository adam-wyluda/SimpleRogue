package simplerogue.world.level.creature;

import com.google.common.base.Optional;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;
import simplerogue.world.level.item.Armor;
import simplerogue.world.level.item.Weapon;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Humanoid extends Creature
{
    private static final String EXP_GAIN = "exp_gain";
    private static final String EQUIPPED_ITEM = "equipped_weapon";
    private static final String EQUIPPED_ARMOR = "equipped_armor";

    private int expGain;

    private Optional<Weapon> equippedWeapon;
    private Optional<Armor> equippedArmor;

    @Override
    public void load(Model model)
    {
        super.load(model);

        expGain = model.has(EXP_GAIN) ? model.getInt(EXP_GAIN) : 0;
        equippedWeapon = model.getInstanceOptional(EQUIPPED_ITEM);
        equippedArmor = model.getInstanceOptional(EQUIPPED_ARMOR);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(EXP_GAIN, expGain);
        save.putInstance(EQUIPPED_ITEM, equippedWeapon.orNull());
        save.putInstance(EQUIPPED_ARMOR, equippedArmor.orNull());

        return save;
    }
}
