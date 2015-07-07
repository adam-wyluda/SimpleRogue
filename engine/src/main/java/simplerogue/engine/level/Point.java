package simplerogue.engine.level;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor(staticName = "create")
public class Point
{
    private final int y;
    private final int x;

    public static List<Point> horizontalRange(final int y, int startX, int endX)
    {
        List<Point> result = Lists.newArrayListWithExpectedSize(endX - startX);

        for (int x = startX; x < endX; x++)
        {
            Point point = Point.create(y, x);

            result.add(point);
        }

        return result;
    }

    public static List<Point> verticalRange(final int x, int startY, int endY)
    {
        List<Point> result = Lists.newArrayListWithExpectedSize(endY - startY);

        for (int y = startY; y < endY; y++)
        {
            Point point = Point.create(y, x);

            result.add(point);
        }

        return result;
    }
}
