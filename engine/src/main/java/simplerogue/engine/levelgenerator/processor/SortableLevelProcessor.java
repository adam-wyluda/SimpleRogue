package simplerogue.engine.levelgenerator.processor;

/**
 * {@link simplerogue.engine.levelgenerator.processor.LevelProcessor} with dependencies.
 *
 * @author Adam Wyłuda
 */
public interface SortableLevelProcessor extends LevelProcessor
{
    Iterable<String> getRunBefore();

    Iterable<String> getRunAfter();
}
