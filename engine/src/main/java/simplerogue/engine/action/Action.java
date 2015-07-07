package simplerogue.engine.action;

import simplerogue.engine.object.ObjectInstance;
import simplerogue.engine.object.Reifiable;

/**
 * Action performed in game. It allows actors to be aware of other actors activities.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.action.ActionManager
 * @see simplerogue.engine.level.Actor
 */
public interface Action<T extends ObjectInstance> extends Reifiable
{
    /**
     * @return Performer of the action.
     */
    T getOrigin();

    /**
     * Perform the action.
     */
    void perform();
}
