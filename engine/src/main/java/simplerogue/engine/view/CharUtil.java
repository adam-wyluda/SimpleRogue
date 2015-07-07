package simplerogue.engine.view;

import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;

/**
 * @author Adam Wyłuda
 */
public class CharUtil
{
    public static CharArea charArraysToArea(CharArray... arrays)
    {
        Char[][] area = new Char[arrays.length][arrays[0].getArray().length];

        for (int y = 0; y < arrays.length; y++)
        {
            area[y] = arrays[y].getArray();
        }

        return new CharArea(area);
    }

    public static CharArea transform(FieldArea fieldArea)
    {
        Char[][] area = new Char[fieldArea.getHeight()][fieldArea.getWidth()];

        for (int y = 0; y < fieldArea.getHeight(); y++)
        {
            for (int x = 0; x < fieldArea.getWidth(); x++)
            {
                Field field = fieldArea.getFieldAt(y, x);
                area[y][x] = field.getCharacter();
            }
        }

        return new CharArea(area);
    }

    public static void drawRectangle(CharArea area, Char frameChar, Char fillChar)
    {
        drawRectangle(area, frameChar, fillChar, 0, 0, area.getHeight(), area.getWidth());
    }

    public static void drawRectangle(CharArea area,
                                     Char frameChar, Char fillChar,
                                     int posY, int posX, int height, int width)
    {
        Char[][] chars = area.getChars();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Char ch;

                if (y == 0 || x == 0 || y == height -1 || x == width - 1)
                {
                    ch = frameChar;
                }
                else
                {
                    ch = fillChar;
                }

                chars[posY + y][posX + x] = ch;
            }
        }
    }
}
