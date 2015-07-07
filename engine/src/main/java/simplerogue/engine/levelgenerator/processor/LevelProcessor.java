package simplerogue.engine.levelgenerator.processor;

import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.object.Named;

/**
 * @author Adam Wyłuda
 */
public interface LevelProcessor<T extends LevelRequest> extends Named, Reifiable
{
    void process(T request);
}
