package simplerogue.engine.ai;

import simplerogue.engine.action.Action;
import simplerogue.engine.level.Actor;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
public abstract class AbstractAI<T extends Actor> extends AbstractReifiable implements AI<T>
{
    @Override
    public void makeTurn(T actor)
    {
    }

    @Override
    public void actionPerformed(T actor, Action action)
    {
    }

    @Override
    public AIInterest getInterest()
    {
        return AIInterest.NONE;
    }
}
