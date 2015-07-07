package simplerogue.world.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Level;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultLevel extends Level
{
    public static final String NAME = "default_level";

    private static final String LEVEL_NUMBER = "level_number";

    private int levelNumber = 1;

    @Override
    public void load(Model model)
    {
        super.load(model);

        levelNumber = model.getInt(LEVEL_NUMBER);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(LEVEL_NUMBER, levelNumber);

        return save;
    }
}
