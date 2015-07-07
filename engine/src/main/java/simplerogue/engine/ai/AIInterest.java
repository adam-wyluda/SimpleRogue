package simplerogue.engine.ai;

/**
 * Defines AI's interest scope.
 *
 * @author Adam Wyłuda
 */
public enum AIInterest
{
    /**
     * Don't listen to any actions.
     */
    NONE,
    /**
     * Listen only to action performed on the same level.
     */
    LEVEL,
    /**
     * Listen to all actions.
     */
    GLOBAL;
}
