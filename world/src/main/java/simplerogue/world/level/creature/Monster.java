package simplerogue.world.level.creature;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;
import simplerogue.world.ai.MonsterAIData;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Monster extends Humanoid
{
    public static final String NAME = "monster";

    private static final String AI_DATA = "ai_data";

    private MonsterAIData aiData;

    @Override
    public void load(Model model)
    {
        super.load(model);

        aiData = model.has(AI_DATA) ? MonsterAIData.create(model.getModel(AI_DATA)) : MonsterAIData.create();
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.putLoadable(AI_DATA, aiData);

        return save;
    }
}
