package simplerogue.engine.levelgenerator.util;

import lombok.Data;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.Point;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
@Data
public abstract class AbstractRoom extends AbstractReifiable implements Room
{
    private Level level;

    @Override
    public void putField(Field field)
    {
        Point randomPoint = randomPoint();

        level.putField(field, randomPoint.getY(), randomPoint.getX());
    }

    @Override
    public void putElement(FieldElement element)
    {
        Point randomPoint = randomPoint();

        level.putElement(element, randomPoint.getY(), randomPoint.getX());
    }
}
