package simplerogue.engine.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.FieldHandler;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectInstance;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveField extends Field implements ObjectInstance
{
    public static final String NAME = "active_field";

    private final static String FIELD_HANDLER = "field_handler";

    private int id;
    private FieldHandler fieldHandler;

    @Override
    public void load(Model model)
    {
        super.load(model);

        String fieldHandlerName = model.has(FIELD_HANDLER) ? model.getString(FIELD_HANDLER) : NullField.NAME;
        fieldHandler = Managers.get(AIManager.class).getFieldHandler(fieldHandlerName);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(FIELD_HANDLER, fieldHandler.getName());

        return model;
    }
}
