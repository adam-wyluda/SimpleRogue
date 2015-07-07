package simplerogue.engine.levelgenerator;

import com.google.common.collect.Lists;
import lombok.Getter;
import simplerogue.engine.level.Level;
import simplerogue.engine.levelgenerator.processor.LevelProcessor;

import java.util.List;

/**
 * Simple, list based level generator.
 *
 * @author Adam Wyłuda
 */
public abstract class ListLevelGenerator<T extends LevelRequest> extends AbstractLevelGenerator<T>
{
    @Getter
    private final List<LevelProcessor> processors;

    protected ListLevelGenerator(String name)
    {
        super(name);
        this.processors = Lists.newArrayList();
    }

    protected ListLevelGenerator(String name, LevelProcessor... processors)
    {
        super(name);
        this.processors = Lists.newArrayList(processors);
    }

    @Override
    public Level createLevel(T request)
    {
        for (LevelProcessor processor : processors)
        {
            processor.process(request);
        }

        return request.getLevel();
    }
}
