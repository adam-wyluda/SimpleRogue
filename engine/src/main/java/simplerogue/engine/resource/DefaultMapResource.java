package simplerogue.engine.resource;

import simplerogue.engine.platform.Storage;

/**
 * @author Adam Wyłuda
 */
public class DefaultMapResource extends DefaultFileResource implements MapResource
{
    public DefaultMapResource(String path, Storage storage)
    {
        super(path, storage);
    }

    @Override
    public int[][] readMap(int height, int width)
    {
        int[] ints = storage.readFileToInts(getPath());

        int[][] map = new int[height][width];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                map[y][x] = ints[y * width + x];
            }
        }

        return map;
    }

    @Override
    public void saveMap(int[][] map)
    {
        final int height = map.length;
        final int width = height > 0 ? map[0].length : 0;

        int[] content = new int[height * width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                content[y * width + x] = map[y][x];
            }
        }

        storage.writeIntsToFile(getPath(), content);
    }
}
