package simplerogue.engine.ai;

import simplerogue.engine.action.Action;
import simplerogue.engine.level.Actor;

/**
 * AI that does nothing.
 *
 * @author Adam Wyłuda
 */
public class NullAI extends AbstractAI<Actor>
{
    private static final String NAME = "null";

    public static final NullAI INSTANCE = new NullAI();

    private NullAI()
    {
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public AIInterest getInterest()
    {
        return AIInterest.NONE;
    }

    @Override
    public void makeTurn(Actor actor)
    {
    }

    @Override
    public void actionPerformed(Actor actor, Action action)
    {
    }
}
