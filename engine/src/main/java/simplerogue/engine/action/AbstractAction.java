package simplerogue.engine.action;

import simplerogue.engine.object.AbstractReifiable;
import simplerogue.engine.object.ObjectInstance;

/**
 * @author Adam Wyłuda
 */
public abstract class AbstractAction<T extends ObjectInstance> extends AbstractReifiable implements Action<T>
{
    private final T origin;

    protected AbstractAction(T origin)
    {
        this.origin = origin;
    }

    @Override
    public T getOrigin()
    {
        return origin;
    }
}
