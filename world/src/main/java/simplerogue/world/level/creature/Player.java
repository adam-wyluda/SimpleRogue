package simplerogue.world.level.creature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Player extends Humanoid
{
    public static final String NAME = "player";

    private static final String EXP = "exp";
    private static final String SKILL_POINTS = "skill_points";

    private int exp;
    private int skillPoints;

    @Override
    public void load(Model model)
    {
        super.load(model);

        exp = model.has(EXP) ? model.getInt(EXP) : 0;
        skillPoints = model.has(EXP) ? model.getInt(SKILL_POINTS) : 0;
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(EXP, exp);
        save.put(SKILL_POINTS, skillPoints);

        return save;
    }
}
