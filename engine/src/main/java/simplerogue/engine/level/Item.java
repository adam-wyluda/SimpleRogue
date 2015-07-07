package simplerogue.engine.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.ai.WasteItemHandler;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.ItemHandler;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;

/**
 * Object that actors can store.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.level.Actor#getItems()
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends FieldElement
{
    public static final String NAME = "item";

    private static final String HANDLER = "item_handler";
    private static final String ITEM_NAME = "name";

    private static final String DEFAULT_NAME = "Default";

    /**
     * If not defined, it will return {@link simplerogue.engine.ai.WasteItemHandler}.
     */
    private ItemHandler handler;

    private String name;

    @Override
    public void load(Model model)
    {
        super.load(model);

        String handlerName = model.has(HANDLER) ? model.getString(HANDLER) : WasteItemHandler.NAME;
        handler = Managers.get(AIManager.class).getItemHandler(handlerName);

        name = model.has(ITEM_NAME) ? model.getString(ITEM_NAME) : DEFAULT_NAME;
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(HANDLER, handler.getName());
        model.put(ITEM_NAME, name);

        return model;
    }
}
