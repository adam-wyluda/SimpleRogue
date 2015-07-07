package simplerogue.engine.test.world.ai;

import simplerogue.engine.action.Action;
import simplerogue.engine.ai.AIInterest;
import simplerogue.engine.ai.AbstractAI;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.world.level.actor.Monster;

/**
 * @author Adam Wyłuda
 */
public class MonsterAI extends AbstractAI<Monster>
{
    @Override
    public AIInterest getInterest()
    {
        return AIInterest.LEVEL;
    }

    @Override
    public String getName()
    {
        return TestConsts.MONSTER_AI_NAME;
    }

    @Override
    public void makeTurn(Monster monster)
    {
        monster.growSize(2);
    }

    @Override
    public void actionPerformed(Monster monster, Action action)
    {
        monster.growSize(3);
    }
}
