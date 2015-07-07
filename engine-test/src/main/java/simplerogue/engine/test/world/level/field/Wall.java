package simplerogue.engine.test.world.level.field;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Field;
import simplerogue.engine.object.Model;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Wall extends Field
{
    private int transparency;

    @Override
    public void load(Model model)
    {
        super.load(model);

        transparency = model.getInt(TestConsts.WALL_TRANSPARENCY);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(TestConsts.WALL_TRANSPARENCY, transparency);

        return model;
    }
}
