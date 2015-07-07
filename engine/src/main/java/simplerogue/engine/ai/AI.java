package simplerogue.engine.ai;

import simplerogue.engine.action.Action;
import simplerogue.engine.level.Actor;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.object.Named;

/**
 * Mind of the {@link simplerogue.engine.level.Actor}.
 *
 * @author Adam Wyłuda
 */
public interface AI<T extends Actor> extends Named, Reifiable
{
    /**
     * @see simplerogue.engine.ai.AIInterest
     */
    AIInterest getInterest();

    /**
     * Perform turn for given actor.
     */
    void makeTurn(T actor);

    /**
     * AI is notified about action performed by other object.
     */
    void actionPerformed(T actor, Action action);
}
