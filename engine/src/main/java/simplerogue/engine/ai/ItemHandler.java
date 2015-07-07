package simplerogue.engine.ai;

import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.ObjectInstance;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.object.Named;

/**
 * Handler that is called when Actor decides to use the item.
 *
 * @author Adam Wyłuda
 */
public interface ItemHandler<I extends Item> extends Named, Reifiable
{
    /**
     * Uses item from user on given object.
     */
    void useItem(I item, Actor user, ObjectInstance target);
}
