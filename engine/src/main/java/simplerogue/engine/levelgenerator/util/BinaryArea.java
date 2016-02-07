package simplerogue.engine.levelgenerator.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Point;
import simplerogue.engine.util.RandomUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Adam Wyłuda
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BinaryArea
{
    private final boolean[][] area;
    private final List<Point> setPoints;

    /**
     * Creates new empty binary area with given height and width.
     */
    public static BinaryArea create(boolean[][] area)
    {
        boolean[][] areaCopy = new boolean[area.length][];
        for (int i = 0; i < area.length; i++)
        {
            areaCopy[i] = Arrays.copyOf(area[i], area[i].length);
        }

        List<Point> setPoints = Lists.newArrayList();

        for (int y = 0; y < area.length; y++)
        {
            for (int x = 0; x < area[0].length; x++)
            {
                if (area[y][x])
                {
                    setPoints.add(Point.create(y, x));
                }
            }
        }

        return new BinaryArea(areaCopy, setPoints);
    }

    public int getHeight()
    {
        return area.length;
    }

    public int getWidth()
    {
        return area[0].length;
    }

    public boolean get(int y, int x)
    {
        return area[y][x];
    }

    public List<Point> getSetPoints() {
        return ImmutableList.copyOf(setPoints);
    }

    public Point randomPoint()
    {
        return RandomUtil.randomElement(setPoints);
    }

    public List<Point> borderFrom(Direction direction)
    {
        List<Point> result = Collections.emptyList();

        switch (direction)
        {
            case N:
                result = borderFromNorth();
                break;
            case E:
                result = borderFromEast();
                break;
            case S:
                result = borderFromSouth();
                break;
            case W:
                result = borderFromWest();
                break;
        }

        return result;
    }

    private List<Point> borderFromNorth()
    {
        List<Point> result = Lists.newArrayList();

        for (int x = 0; x < getWidth(); x++)
        {
            for (int y = 0; y < getHeight(); y++)
            {
                if (area[y][x])
                {
                    result.add(Point.create(y, x));
                    break;
                }
            }
        }

        return result;
    }

    private List<Point> borderFromEast()
    {
        List<Point> result = Lists.newArrayList();

        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = getWidth() - 1; x >= 0; x--)
            {
                if (area[y][x])
                {
                    result.add(Point.create(y, x));
                }
            }
        }

        return result;
    }

    private List<Point> borderFromSouth()
    {
        List<Point> result = Lists.newArrayList();

        for (int x = 0; x < getWidth(); x++)
        {
            for (int y = getHeight() - 1; y >= 0; y--)
            {
                if (area[y][x])
                {
                    result.add(Point.create(y, x));
                    break;
                }
            }
        }

        return result;
    }

    private List<Point> borderFromWest()
    {
        List<Point> result = Lists.newArrayList();

        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {
                if (area[y][x])
                {
                    result.add(Point.create(y, x));
                }
            }
        }

        return result;
    }

    public List<Point> borderPoints()
    {
        List<Point> result = Lists.newArrayList();

        for (Point point : setPoints)
        {
            if (isBorder(point))
            {
                result.add(point);
            }
        }

        return result;
    }

    private boolean isBorder(Point point)
    {
        int y = point.getY();
        int x = point.getX();

        boolean neighborsSet = area[y - 1][x - 1] && area[y - 1][x] && area[y - 1][x + 1]
                && area[y][x - 1] && area[y][x + 1]
                && area[y + 1][x - 1] && area[y + 1][x] && area[y + 1][x + 1];

        return !neighborsSet;
    }
}
