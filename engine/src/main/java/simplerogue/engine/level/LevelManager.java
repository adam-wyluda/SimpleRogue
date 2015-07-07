package simplerogue.engine.level;

import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Manager;

/**
 * Manages levels.
 *
 * @author Adam Wyłuda
 */
public interface LevelManager extends Manager
{
    /**
     * Creates level of given type.
     */
    Level createLevel(String type);

    /**
     * Generates new level for given request and returns entry portal.
     */
    Portal generateLevel(LevelRequest request);

    /**
     * Moves given field element to another level.
     */
    void moveElement(FieldElement element, Portal nextPortal);

    /**
     * Registers given level with its generator.
     */
    void registerLevel(Level level);

    /**
     * Returns all registered levels.
     */
    Iterable<Level> getLevels();

    /**
     * Returns level with given generator.
     */
    Level getLevel(String name);

    /**
     * Generates unique generator for given generator generator.
     */
    String generateLevelName(String generator);
}
