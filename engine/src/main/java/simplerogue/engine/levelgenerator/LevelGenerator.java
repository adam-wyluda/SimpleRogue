package simplerogue.engine.levelgenerator;

import simplerogue.engine.level.Level;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.object.Named;

/**
 * Creates a {@link simplerogue.engine.level.Level}.
 *
 * @author Adam Wyłuda
 */
public interface LevelGenerator<T extends LevelRequest> extends Named, Reifiable
{
    // TODO Document
    T createRequest();

    /**
     * Creates level for specified {@link simplerogue.engine.levelgenerator.LevelRequest}.
     */
    Level createLevel(T request);
}
