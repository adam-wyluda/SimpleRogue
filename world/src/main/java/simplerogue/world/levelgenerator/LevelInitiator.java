package simplerogue.world.levelgenerator;

import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.world.level.DefaultLevel;

/**
 * @author Adam Wyłuda
 */
public class LevelInitiator extends AbstractLevelProcessor<DefaultLevelRequest>
{
    private static final String NAME = "level_initator";

    @Override
    public void process(DefaultLevelRequest request)
    {
        DefaultLevel level = request.getLevel().reify(DefaultLevel.class);

        int levelNumber = request.getLevelNumber();
        level.setLevelNumber(levelNumber);
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
