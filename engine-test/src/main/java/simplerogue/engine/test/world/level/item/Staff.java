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
public class Staff extends Item
{
    private int length;

    @Override
    public void load(Model model)
    {
        super.load(model);

        length = model.getInt(TestConsts.STAFF_LENGTH);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(TestConsts.STAFF_LENGTH, length);

        return model;
    }
}
