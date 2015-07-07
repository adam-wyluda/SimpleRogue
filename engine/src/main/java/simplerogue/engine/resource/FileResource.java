package simplerogue.engine.resource;

/**
 * Represents raw file.
 *
 * @author Adam Wyłuda
 */
public interface FileResource extends Resource
{
    int[] read();

    void save(int[] data);
}
