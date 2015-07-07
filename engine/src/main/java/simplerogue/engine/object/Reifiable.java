package simplerogue.engine.object;

/**
 * Type that can be transformed to another.
 *
 * @author Adam Wyłuda
 */
public interface Reifiable
{
    <T> boolean is(Class<T> type);

    <T> T reify(Class<T> type);
}
