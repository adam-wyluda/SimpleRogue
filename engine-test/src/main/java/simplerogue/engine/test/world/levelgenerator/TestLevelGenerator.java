package simplerogue.engine.test.world.levelgenerator;

import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.ListLevelGenerator;
import simplerogue.engine.levelgenerator.processor.InitialLevelProcessor;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestLevelGenerator extends ListLevelGenerator
{
    public TestLevelGenerator()
    {
        super(TestConsts.TEST_GENERATOR_NAME);

        getProcessors().add(new InitialLevelProcessor());
        getProcessors().add(new TestFieldAreaProcessor());
        getProcessors().add(new TestPortalProcessor());
        getProcessors().add(new TestItemProcessor());
        getProcessors().add(new TestPlayerProcessor());
    }

    @Override
    public LevelRequest createRequest()
    {
        LevelRequest request = super.createRequest();

        request.setLevelType(TestConsts.TEST_LEVEL_TYPE);

        return request;
    }
}
