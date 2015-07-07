package simplerogue.engine.ai;

import simplerogue.engine.level.Item;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
public abstract class AbstractItemHandler <T extends Item> extends AbstractReifiable implements ItemHandler<T>
{
}
