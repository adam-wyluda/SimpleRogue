package simplerogue.engine.resource;

/**
 * Represents binary map resource.
 *
 * @author Adam Wyłuda
 */
public interface MapResource extends FileResource
{
    int[][] readMap(int height, int width);

    void saveMap(int[][] map);
}
