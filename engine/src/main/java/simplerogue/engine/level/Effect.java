package simplerogue.engine.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.EffectHandler;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;

/**
 * Level element that applies an effect to the field, like fire for example.
 *
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Effect extends FieldElement
{
    public static final String NAME = "effect";

    private static final String HANDLER = "effect_handler";

    private EffectHandler handler;

    @Override
    public void load(Model model)
    {
        super.load(model);

        String handlerName = model.getString(HANDLER);
        handler = Managers.get(AIManager.class).getEffectHandler(handlerName);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(HANDLER, handler.getName());

        return model;
    }
}
