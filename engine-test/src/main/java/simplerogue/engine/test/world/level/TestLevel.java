package simplerogue.engine.test.world.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Level;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestLevel extends Level
{
    private static final String ROOM_COUNT = "room_count";

    private int roomCount;

    @Override
    public void load(Model model)
    {
        super.load(model);

        roomCount = model.getInt(ROOM_COUNT);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(ROOM_COUNT, roomCount);

        return model;
    }
}
