package simplerogue.engine.level;

import lombok.Data;

/**
 * A 2D map of fields.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.level.Field
 */
@Data
public class FieldArea
{
    private Field[][] fields;

    /**
     * Creates empty field area with {@link simplerogue.engine.level.NullField} as elements.
     */
    public static FieldArea create(int height, int width)
    {
        FieldArea area = new FieldArea();

        area.fields = new Field[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                area.fields[y][x] = NullField.INSTANCE;
            }
        }

        return area;
    }

    public int getHeight()
    {
        return fields.length;
    }

    public int getWidth()
    {
        return getHeight() >= 1 ? fields[0].length : 0;
    }

    public Field getFieldAt(int y, int x)
    {
        if (y < 0 || x < 0
                || y > getHeight() - 1
                || x > getWidth() - 1)
        {
            return NullField.INSTANCE;
        }
        return fields[y][x];
    }

    protected void setFieldAt(Field field, int y, int x)
    {
        fields[y][x] = field;
    }

    public FieldArea getPart(int posY, int posX, int height, int width)
    {
        Field[][] part = new Field[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                part[y][x] = getFieldAt(y + posY, x + posX);
            }
        }

        FieldArea newFieldArea = new FieldArea();
        newFieldArea.setFields(part);
        return newFieldArea;
    }

    public boolean isWithin(int y, int x)
    {
        return y >= 0 && x >= 0
                && y < getHeight()
                && x < getWidth();
    }

    public boolean isWithin(Point point)
    {
        return isWithin(point.getY(), point.getX());
    }
}
