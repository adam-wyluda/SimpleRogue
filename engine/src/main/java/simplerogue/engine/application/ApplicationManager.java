package simplerogue.engine.application;

import simplerogue.engine.manager.Manager;

/**
 * Manages application.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.application.Version
 */
public interface ApplicationManager extends Manager
{
    /**
     * Returns engine version.
     */
    Version getEngineVersion();

    /**
     * Exits game.
     */
    void exit();
}
