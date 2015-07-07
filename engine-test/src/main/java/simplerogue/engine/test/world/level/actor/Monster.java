package simplerogue.engine.test.world.level.actor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Actor;
import simplerogue.engine.object.Model;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Monster extends Actor
{
    private int size;

    @Override
    public void load(Model model)
    {
        super.load(model);

        size = model.getInt(TestConsts.MONSTER_SIZE);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(TestConsts.MONSTER_SIZE, size);

        return model;
    }

    public void growSize(int size)
    {
        setSize(getSize() + size);
    }
}
