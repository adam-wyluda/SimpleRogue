package simplerogue.engine.ai;

import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.object.Named;

/**
 * @author Adam Wyłuda
 */
public interface FieldHandler<T extends Field> extends Named, Reifiable
{
    void handle(T field, FieldElement element);
}
