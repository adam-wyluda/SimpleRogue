package simplerogue.engine.manager;

import simplerogue.engine.object.Reifiable;

/**
 * Singleton service accessible via {@link Managers#get(Class)}.
 *
 * @author Adam Wyłuda
 */
public interface Manager extends Reifiable
{
    /**
     * Called after all managers have been registered. Therefore it's possible to perform initiation dependent on other
     * services.
     */
    void init();
}
