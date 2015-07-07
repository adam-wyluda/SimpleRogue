package simplerogue.engine.ai;

import simplerogue.engine.level.Effect;
import simplerogue.engine.object.Named;
import simplerogue.engine.object.Reifiable;

/**
 * Applies effect to a field.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.level.Effect
 * @see simplerogue.engine.level.Field
 */
public interface EffectHandler <E extends Effect> extends Named, Reifiable
{
    void apply(E effect);
}
