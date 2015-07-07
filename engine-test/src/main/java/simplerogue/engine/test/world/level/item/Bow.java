package simplerogue.engine.test.world.level.item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.Model;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Bow extends Item
{
    private int range;

    @Override
    public void load(Model model)
    {
        super.load(model);

        range = model.getInt(TestConsts.BOW_RANGE);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(TestConsts.BOW_RANGE, range);

        return model;
    }
}
