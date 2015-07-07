package simplerogue.engine.levelgenerator.processor;

import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
public abstract class AbstractLevelProcessor<T extends LevelRequest>
        extends AbstractReifiable
        implements LevelProcessor<T>
{
}
