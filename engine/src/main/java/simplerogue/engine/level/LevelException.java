package simplerogue.engine.level;

/**
 * @author Adam Wyłuda
 */
public class LevelException extends RuntimeException
{
    private LevelException(String message)
    {
        super(message);
    }

    public static LevelException levelElementIsCached(LevelElement element)
    {
        return new LevelException(String.format("Level element %s is cached", element));
    }
}
