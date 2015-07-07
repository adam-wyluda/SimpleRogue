package simplerogue.engine.test.world.level.item;

import lombok.Data;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.Model;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
@Data
public class Sword extends Item
{
    private int attack;

    @Override
    public void load(Model model)
    {
        super.load(model);

        attack = model.getInt(TestConsts.SWORD_ATTACK);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(TestConsts.SWORD_ATTACK, attack);

        return model;
    }
}
