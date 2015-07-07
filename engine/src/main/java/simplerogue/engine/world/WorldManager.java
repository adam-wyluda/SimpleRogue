package simplerogue.engine.world;

import simplerogue.engine.manager.Manager;

import java.util.List;

/**
 * Provides game implementation.
 *
 * @author Adam Wyłuda
 */
public interface WorldManager extends Manager
{
    /**
     * @return List of worlds.
     */
    List<World> getWorlds();
}
