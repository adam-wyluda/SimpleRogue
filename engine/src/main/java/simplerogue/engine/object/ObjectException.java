package simplerogue.engine.object;

/**
 * @author Adam Wyłuda
 */
public class ObjectException extends RuntimeException
{
    private ObjectException(String message)
    {
        super(message);
    }

    public static ObjectException prototypeNotFound(String name)
    {
        return new ObjectException(String.format("Prototype \"%s\" not found", name));
    }

    public static ObjectException fieldPrototypeNotFound(int id)
    {
        return new ObjectException(String.format("Field prototype with id \"%d\" not found", id));
    }

    public static ObjectException objectNotFound(int id)
    {
        return new ObjectException(String.format("Object with id %s not found", id));
    }

    public static ObjectException instantiationError(String typeClass)
    {
        return new ObjectException(String.format("Couldn't instantiate object of class %s", typeClass));
    }
}
