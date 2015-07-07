package simplerogue.engine.test.ai;

import simplerogue.engine.action.Action;
import simplerogue.engine.ai.AIInterest;
import simplerogue.engine.ai.AbstractAI;
import simplerogue.engine.level.Actor;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestAI extends AbstractAI<Actor>
{
    @Override
    public AIInterest getInterest()
    {
        return AIInterest.GLOBAL;
    }

    @Override
    public void makeTurn(Actor actor)
    {
    }

    @Override
    public void actionPerformed(Actor actor, Action action)
    {
    }

    @Override
    public String getName()
    {
        return TestConsts.TEST_AI_NAME;
    }
}
