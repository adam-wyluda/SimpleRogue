package simplerogue.engine.test.isolated;

import org.junit.Test;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.DefaultAIManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.test.IsolatedTest;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.ai.TestAI;
import simplerogue.engine.test.ai.TestEffectHandler;
import simplerogue.engine.test.ai.TestFieldHandler;
import simplerogue.engine.test.ai.TestItemHandler;

import static org.junit.Assert.assertTrue;

/**
 * @author Adam Wyłuda
 */
public class AIIsolatedTest extends IsolatedTest
{
    private AIManager aiManager;

    @Override
    protected void beforeInit()
    {
        super.beforeInit();

        aiManager = new DefaultAIManager();
        Managers.register(AIManager.class, aiManager);
    }

    @Test
    public void testGetAIs()
    {
        assertTrue(aiManager.getAI(TestConsts.TEST_AI_NAME).is(TestAI.class));
        assertTrue(aiManager.getEffectHandler(TestConsts.TEST_EFFECT_HANDLER_NAME).is(TestEffectHandler.class));
        assertTrue(aiManager.getFieldHandler(TestConsts.TEST_FIELD_HANDLER_NAME).is(TestFieldHandler.class));
        assertTrue(aiManager.getItemHandler(TestConsts.TEST_ITEM_HANDLER_NAME).is(TestItemHandler.class));
    }
}
