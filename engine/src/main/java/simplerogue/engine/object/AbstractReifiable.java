package simplerogue.engine.object;

/**
 * Basic implementation of {@link simplerogue.engine.object.Reifiable}, which works by plain Java casting.
 *
 * @author Adam Wyłuda
 */
public abstract class AbstractReifiable implements Reifiable
{
    @Override
    public <T> boolean is(Class<T> type)
    {
        return type.isInstance(this);
    }

    @Override
    public <T> T reify(Class<T> type)
    {
        return (T) this;
    }
}
