package simplerogue.engine.levelgenerator.util;

import com.google.common.collect.Lists;
import lombok.Data;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.Point;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.util.RandomUtil;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
public class BinaryAreaRoom extends AbstractRoom implements Room
{
    private final BinaryArea area;
    private final Point startingPoint;

    private final Prototype fillPrototype;
    private final Prototype borderPrototype;

    private BinaryAreaRoom(Level level, BinaryArea area, Point startingPoint,
                           Prototype fillPrototype, Prototype borderPrototype) {
        super(level);

        this.area = area;
        this.startingPoint = startingPoint;
        this.fillPrototype = fillPrototype;
        this.borderPrototype = borderPrototype;
    }

    @Override
    public boolean isInRoom(int y, int x)
    {
        return area.get(y, x);
    }

    @Override
    public Point randomPoint()
    {
        return area.randomPoint().translate(startingPoint);
    }

    @Override
    public Point randomBorderPoint(Direction direction)
    {
        List<Point> candidates = area.borderFrom(direction);

        return RandomUtil.randomElement(candidates);
    }

    @Override
    public void fill()
    {
        List<Point> borderPoints = area.borderPoints();

        for (Point point : borderPoints) {
            Point translated = point.translate(startingPoint);
            Field field = borderPrototype.create();
            getLevel().putField(field, translated.getY(), translated.getX());
        }

        List<Point> fillPoints = Lists.newArrayList(area.getSetPoints());
        fillPoints.removeAll(borderPoints);

        for (Point point : fillPoints) {
            Point translated = point.translate(startingPoint);
            Field field = fillPrototype.create();
            getLevel().putField(field, translated.getY(), translated.getX());
        }
    }
}
