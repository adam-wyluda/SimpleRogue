package simplerogue.world.ai;

import lombok.Data;
import lombok.NoArgsConstructor;
import simplerogue.engine.object.Loadable;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
@Data
@NoArgsConstructor(staticName = "create")
public class MonsterAIData implements Loadable
{
    public enum Mode
    {
        WANDERING, CHASING_PLAYER;
    }

    private static final String MODE = "mode";
    private static final String FATIGUE = "fatigue";

    private Mode mode = Mode.WANDERING;
    private double fatigue = 0.0;

    public static MonsterAIData create(Model model)
    {
        MonsterAIData instance = create();
        instance.load(model);

        return instance;
    }

    @Override
    public void load(Model model)
    {
        mode = model.getEnum(Mode.class, MODE);
        fatigue = model.getInt(FATIGUE);
    }

    @Override
    public Model save()
    {
        Model save = new Model();

        save.put(MODE, mode);
        save.put(FATIGUE, fatigue);

        return save;
    }
}
