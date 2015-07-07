package simplerogue.world.levelgenerator;

import simplerogue.engine.levelgenerator.ListLevelGenerator;
import simplerogue.engine.levelgenerator.processor.InitialLevelProcessor;

/**
 * @author Adam Wyłuda
 */
public class DefaultLevelGenerator extends ListLevelGenerator<DefaultLevelRequest>
{
    public static final String NAME = "default_level_generator";

    public DefaultLevelGenerator()
    {
        super(NAME);

        getProcessors().add(new InitialLevelProcessor());
        getProcessors().add(new LevelInitiator());
        getProcessors().add(new AreaProcessor());
        getProcessors().add(new PortalGenerator());
        getProcessors().add(new MonsterGenerator());
        getProcessors().add(new ItemGenerator());
    }

    @Override
    public DefaultLevelRequest createRequest()
    {
        DefaultLevelRequest request = new DefaultLevelRequest();
        request.setWidth(150);
        request.setHeight(100);

        return request;
    }
}
