package simplerogue.engine.platform;

import simplerogue.engine.manager.Manager;

/**
 * Produces platform dependent objects.
 *
 * @author Adam Wyłuda
 */
public interface PlatformManager extends Manager
{
    Storage createStorage();

    View createView();
}
