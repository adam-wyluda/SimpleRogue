package simplerogue.engine.levelgenerator;

import simplerogue.engine.manager.Manager;

/**
 * Provides {@link simplerogue.engine.levelgenerator.LevelGenerator} objects for requests.
 *
 * @author Adam Wyłuda
 */
public interface LevelGeneratorManager extends Manager
{
    /**
     * Creates request for given generator name.
     *
     * @see LevelGenerator#createRequest()
     */
    <T extends LevelRequest> T createRequest(String name);

    /**
     * Provides a LevelGenerator for given request.
     */
    LevelGenerator getGenerator(String name);

    /**
     * Provides a LevelGenerator for given request.
     */
    LevelGenerator getGenerator(LevelRequest request);
}
