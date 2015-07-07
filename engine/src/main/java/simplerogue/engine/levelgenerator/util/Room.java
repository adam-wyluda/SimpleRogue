package simplerogue.engine.levelgenerator.util;

import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.Point;
import simplerogue.engine.object.Reifiable;

/**
 * @author Adam Wyłuda
 */
public interface Room extends Reifiable
{
    boolean isInRoom(int y, int x);

    Point randomPoint();

    Point randomBorderPoint(Direction direction);

    void fill();

    void putField(Field field);

    void putElement(FieldElement element);
}
