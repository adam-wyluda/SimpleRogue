package simplerogue.engine.levelgenerator.util;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.Data;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.Point;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.util.RandomUtil;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
public class RectangleRoom extends AbstractRoom implements Room
{
    private static final int MIN_SIZE = 5;

    private int y;
    private int x;
    private int height;
    private int width;

    private Prototype fillPrototype;
    private Prototype borderPrototype;

    public static RectangleRoom createRandom(int startY, int endY, int startX, int endX,
                                             Prototype fillPrototype, Prototype borderPrototype,
                                             Level level)
    {
        int wholeHeight = endY - startY;
        int wholeWidth = endX - startX;

        int height = RandomUtil.randomInt(MIN_SIZE, wholeHeight);
        int width = RandomUtil.randomInt(MIN_SIZE, wholeWidth);

        int y = startY + RandomUtil.randomInt(wholeHeight - height);
        int x = startX + RandomUtil.randomInt(wholeWidth - width);

        RectangleRoom result = new RectangleRoom();
        result.setY(y);
        result.setX(x);
        result.setHeight(height);
        result.setWidth(width);
        result.setFillPrototype(fillPrototype);
        result.setBorderPrototype(borderPrototype);
        result.setLevel(level);

        return result;
    }

    @Override
    public boolean isInRoom(int y, int x)
    {
        return y >= this.y && y < this.y + height &&
                x >= this.x && x < this.x + width;
    }

    @Override
    public Point randomPoint()
    {
        int randomY = RandomUtil.randomInt(y + 1, y + height - 1);
        int randomX = RandomUtil.randomInt(x + 1, x + width - 1);

        Point randomPoint = Point.create(randomY, randomX);

        return randomPoint;
    }

    @Override
    public Point randomBorderPoint(Direction direction)
    {
        List<Point> border = borderAtDirection(direction);
        Point randomPoint = RandomUtil.randomElement(border);

        return randomPoint;
    }

    @Override
    public void fill()
    {
        GeneratorUtil.drawRectangle(getLevel(), borderPrototype, Optional.fromNullable(fillPrototype),
                y, x, height, width);
    }

    private List<Point> borderAtDirection(Direction direction)
    {
        List<Point> result = Lists.newArrayListWithExpectedSize(Math.max(height, width));

        final int startY = y;
        final int midY = startY + height / 2;
        final int endY = startY + height;

        final int startX = x;
        final int midX = startX + width / 2;
        final int endX = startX + width;

        switch (direction)
        {
            case N:
                result.addAll(Point.horizontalRange(startY, startX + 1, endX - 1));
                break;
            case NE:
                result.addAll(Point.horizontalRange(startY, midX, endX));
                result.addAll(Point.verticalRange(startX, startY, midY));
                break;
            case E:
                result.addAll(Point.verticalRange(endX, startY + 1, endY - 1));
                break;
            case SE:
                result.addAll(Point.horizontalRange(endY, midX, endX));
                result.addAll(Point.verticalRange(endX, midY, endY));
                break;
            case S:
                result.addAll(Point.horizontalRange(endY, startX + 1, endX - 1));
                break;
            case SW:
                result.addAll(Point.horizontalRange(endY, startX, midX));
                result.addAll(Point.verticalRange(startX, midY, endY));
                break;
            case W:
                result.addAll(Point.verticalRange(startX, startY + 1, endY - 1));
                break;
            case NW:
                result.addAll(Point.horizontalRange(startY, startX, midX));
                result.addAll(Point.verticalRange(startX, startY, midY));
                break;
        }

        return result;
    }
}
