package simplerogue.engine.test.isolated;

import org.junit.Test;
import simplerogue.engine.levelgenerator.DefaultLevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelGenerator;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.test.IsolatedTest;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.TestHelper;

import static org.junit.Assert.assertEquals;

/**
 * @author Adam Wyłuda
 */
public class LevelGeneratorIsolatedTest extends IsolatedTest
{
    private DefaultLevelGeneratorManager levelGeneratorManager;

    @Override
    protected void beforeInit()
    {
        super.beforeInit();

        levelGeneratorManager = new DefaultLevelGeneratorManager();
        Managers.register(LevelGeneratorManager.class, levelGeneratorManager);
    }

    @Override
    protected void afterInit()
    {
        super.afterInit();

        levelGeneratorManager.worldChanged(TestHelper.getWorld());
    }

    @Test
    public void testGetLevelGenerator()
    {
        LevelRequest request = Managers.get(LevelGeneratorManager.class).createRequest(TestConsts.TEST_GENERATOR_NAME);
        LevelGenerator generator = levelGeneratorManager.getGenerator(request);

        assertEquals(TestConsts.TEST_GENERATOR_NAME, generator.getName());
    }
}
