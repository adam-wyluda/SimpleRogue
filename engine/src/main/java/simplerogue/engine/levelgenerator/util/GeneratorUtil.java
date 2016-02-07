package simplerogue.engine.levelgenerator.util;

import com.google.common.base.Optional;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.NullField;
import simplerogue.engine.level.Point;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.Prototype;

import java.util.List;

/**
 * Methods useful in level generation.
 *
 * @author Adam Wyłuda
 */
public class GeneratorUtil
{
    private GeneratorUtil()
    {
    }

    /**
     * Draws rectangle in given field area. Fill field is optional.
     */
    public static void drawRectangle(Level level,
                                     Prototype borderPrototype,
                                     Optional<Prototype> fillPrototype,
                                     int posY, int posX,
                                     int height, int width)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (y == 0 || y == height - 1 ||
                        x == 0 || x == width - 1)
                {
                    Field borderField = borderPrototype.create();
                    level.putField(borderField, y + posY, x + posX);
                }
                else if (fillPrototype.isPresent())
                {
                    Field fillField = fillPrototype.get().create();
                    level.putField(fillField, y + posY, x + posX);
                }
            }
        }
    }

    public static void surroundWithWall(Level level,
                                        Prototype wallPrototype,
                                        int y, int x)
    {
        for (Direction direction : Direction.NON_IDEMPOTENT)
        {
            int transformY = direction.transformY(y);
            int transformX = direction.transformX(x);
            Field nearField = level.getArea().getFieldAt(transformY, transformX);

            if (nearField.is(NullField.class) && level.getArea().isWithin(transformY, transformX))
            {
                Field newWall = wallPrototype.create();

                level.putField(newWall, transformY, transformX);
            }
        }
    }

    public static void drawLineVertical(Level level,
                                        Prototype floorPrototype,
                                        Optional<Prototype> wallPrototype,
                                        int x,
                                        int startY, int endY)
    {
        if (startY > endY)
        {
            int temp = startY;
            startY = endY;
            endY = temp;
        }

        for (int y = startY; y <= endY; y++)
        {
            Field floor = floorPrototype.create();
            level.putField(floor, y, x);

            if (wallPrototype.isPresent())
            {
                Prototype prototype = wallPrototype.get();
                surroundWithWall(level, prototype, y, x);
            }
        }
    }

    public static void drawLineHorizontal(Level level,
                                        Prototype floorPrototype,
                                        Optional<Prototype> wallPrototype,
                                        int y,
                                        int startX, int endX)
    {
        if (startX > endX)
        {
            int temp = startX;
            startX = endX;
            endX = temp;
        }

        for (int x = startX; x <= endX; x++)
        {
            Field floor = floorPrototype.create();
            level.putField(floor, y, x);

            if (wallPrototype.isPresent())
            {
                Prototype prototype = wallPrototype.get();
                surroundWithWall(level, prototype, y, x);
            }
        }
    }

    public static void drawCorridorVertical(Level level,
                                            Prototype floorPrototype,
                                            Optional<Prototype> wallPrototype,
                                            int splitY, int startX, int endX)
    {
        Point startPoint = findNonNullField(level, splitY, startX, Direction.N);
        Point endPoint = findNonNullField(level, splitY, endX, Direction.S);

        drawLineHorizontal(level, floorPrototype, wallPrototype,
                splitY, startX, endX);
        drawLineVertical(level, floorPrototype, wallPrototype,
                startX, splitY, startPoint.getY());
        drawLineVertical(level, floorPrototype, wallPrototype,
                endX, splitY, endPoint.getY());
    }

    public static void drawCorridorHorizontal(Level level,
                                            Prototype floorPrototype,
                                            Optional<Prototype> wallPrototype,
                                            int splitX, int startY, int endY)
    {
        Point startPoint = findNonNullField(level, startY, splitX, Direction.W);
        Point endPoint = findNonNullField(level, endY, splitX, Direction.E);

        drawLineVertical(level, floorPrototype, wallPrototype,
                splitX, startY, endY);
        drawLineHorizontal(level, floorPrototype, wallPrototype,
                startY, splitX, startPoint.getX());
        drawLineHorizontal(level, floorPrototype, wallPrototype,
                endY, splitX, endPoint.getX());
    }

    public static Point findNonNullField(Level level, int startY, int startX, Direction direction)
    {
        int resultY = startY;
        int resultX = startX;

        while (level.getArea().isWithin(resultY, resultX) &&
                level.getArea().getFieldAt(resultY, resultX).is(NullField.class))
        {
            resultY = direction.transformY(resultY);
            resultX = direction.transformX(resultX);
        }

        return Point.create(resultY, resultX);
    }
}
