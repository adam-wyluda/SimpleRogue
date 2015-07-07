package simplerogue.engine.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.ai.AI;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Actor extends FieldElement
{
    public static final String NAME = "actor";

    private static final String AI_TYPE = "ai_type";
    private static final String ITEMS = "items";

    private AI ai;
    private List<Item> items;

    @Override
    public void load(Model model)
    {
        super.load(model);

        String aiType = model.getString(AI_TYPE);
        ai = Managers.get(AIManager.class).getAI(aiType);

        items = model.getInstances(ITEMS);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(AI_TYPE, ai.getName());
        model.putInstances(ITEMS, items);

        return model;
    }
}
