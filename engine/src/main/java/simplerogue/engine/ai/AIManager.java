package simplerogue.engine.ai;

import simplerogue.engine.manager.Manager;

/**
 * Provides AI implementations. Holds AI index by interest.
 *
 * @author Adam Wyłuda
 */
public interface AIManager extends Manager
{
    <T extends AI> T getAI(String name);

    <T extends EffectHandler> T getEffectHandler(String name);

    <T extends FieldHandler> T getFieldHandler(String name);

    <T extends ItemHandler> T getItemHandler(String name);
}
