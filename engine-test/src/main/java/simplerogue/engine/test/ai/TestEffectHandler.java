package simplerogue.engine.test.ai;

import simplerogue.engine.ai.AbstractEffectHandler;
import simplerogue.engine.level.Effect;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestEffectHandler extends AbstractEffectHandler<Effect>
{
    @Override
    public void apply(Effect effect)
    {
    }

    @Override
    public String getName()
    {
        return TestConsts.TEST_EFFECT_HANDLER_NAME;
    }
}
