package simplerogue.engine.ai;

import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.ObjectInstance;

/**
 * Item handler that does nothing, but removes the item.
 *
 * @author Adam Wyłuda
 */
public class WasteItemHandler extends AbstractItemHandler<Item>
{
    public static final String NAME = "waste";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void useItem(Item item, Actor user, ObjectInstance target)
    {
        user.getItems().remove(item);
    }
}
