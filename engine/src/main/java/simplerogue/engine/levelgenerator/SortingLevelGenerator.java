package simplerogue.engine.levelgenerator;

import com.google.common.collect.Lists;
import simplerogue.engine.level.Level;
import simplerogue.engine.levelgenerator.processor.LevelProcessor;

import java.util.List;

/**
 * Level generator that resolves all dependencies between processors.
 *
 * @author Adam Wyłuda
 */
public abstract class SortingLevelGenerator<T extends LevelRequest> extends AbstractLevelGenerator<T>
{
    private final List<LevelProcessor> processors;

    private SortingLevelGenerator(String name, LevelProcessor... processors)
    {
        super(name);
        this.processors = Lists.newArrayList(processors);
    }

    @Override
    public Level createLevel(T request)
    {
        // TODO: Resolve all dependencies between level processors

        return request.getLevel();
    }
}
