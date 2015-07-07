package simplerogue.engine.test.util;

import simplerogue.engine.resource.Resources;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static simplerogue.engine.test.TestConsts.FLOOR_ID;
import static simplerogue.engine.test.TestConsts.LEVEL_HEIGHT;
import static simplerogue.engine.test.TestConsts.LEVEL_WIDTH;
import static simplerogue.engine.test.TestConsts.WALL_ID;
import static simplerogue.engine.test.TestConsts.WALL_POS_Y;

/**
 * Creates map binary. </p>
 *
 * Resulting <i>level.map</i> file should be
 *
 * @author Adam Wyłuda
 */
public class LevelMapGenerator
{
    public static void main(String[] args) throws Exception
    {
        int[][] map = new int[LEVEL_HEIGHT][LEVEL_WIDTH];

        // Draw empty fields
        for(int y = 0; y < LEVEL_HEIGHT; y++)
        {
            for(int x = 0; x < LEVEL_WIDTH; x++)
            {
                map[y][x] = FLOOR_ID;
            }
        }

        // Draw wall
        for (int x = 0; x < LEVEL_WIDTH; x++)
        {
            map[WALL_POS_Y][x] = WALL_ID;
        }

        // Write to file
        File target = new File(Resources.LEVEL_MAP);
        System.out.println("Writing to: " + target.getAbsolutePath());
        DataOutputStream stream = new DataOutputStream(new FileOutputStream(target));
        for(int y = 0; y < LEVEL_HEIGHT; y++)
        {
            for(int x = 0; x < LEVEL_WIDTH; x++)
            {
                stream.writeInt(map[y][x]);
            }
        }
    }
}
