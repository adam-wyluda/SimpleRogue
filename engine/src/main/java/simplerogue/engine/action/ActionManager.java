package simplerogue.engine.action;

import simplerogue.engine.manager.Manager;

/**
 * Performs actions, notifies all listeners.
 *
 * @author Adam Wyłuda
 */
public interface ActionManager extends Manager
{
    /**
     * Calls {@link simplerogue.engine.ai.AI#makeTurn(simplerogue.engine.level.Actor)} for all registered
     * objects with ais/handlers.
     *
     * @see simplerogue.engine.ai.AIManager
     */
    void makeTurn();

    /**
     * Performs given action. After doing so, notifies all interested AIs by calling
     * {@link simplerogue.engine.ai.AI#actionPerformed(simplerogue.engine.level.Actor, Action)}.
     */
    void submitAction(Action action);

    void addListener(Listener listener);

    void addTurnPerformer(TurnPerformer performer);

    public interface Listener
    {
        void turnPerformed();
    }

    /**
     * Custom turn performer.
     */
    public interface TurnPerformer
    {
        void perform();
    }
}
