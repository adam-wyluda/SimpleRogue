package simplerogue.engine.ai;

import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
public class NullFieldHandler<T extends Field> extends AbstractReifiable implements FieldHandler<T>
{
    private static final String NAME = "null";

    public static NullFieldHandler INSTANCE = new NullFieldHandler();

    private NullFieldHandler()
    {
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void handle(T field, FieldElement element)
    {
    }
}
