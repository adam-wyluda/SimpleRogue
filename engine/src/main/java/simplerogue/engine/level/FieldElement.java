package simplerogue.engine.level;

import lombok.Data;
import lombok.Getter;
import simplerogue.engine.object.ObjectInstance;

/**
 * Mobile level element.
 *
 * @author Adam Wyłuda
 */
@Data
public class FieldElement extends LevelElement implements ObjectInstance
{
    public static final String NAME = "field_element";

    @Getter
    private int id;

    public Field getField()
    {
        Level level = getLevel();
        FieldArea area = level.getArea();
        Field fieldAt = area.getFieldAt(getPosY(), getPosX());

        return fieldAt;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FieldElement element = (FieldElement) o;

        if (id != element.id) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
