package simplerogue.engine.view;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor
public class CharArea
{
    private Char[][] chars;

    public static CharArea create(int height, int width)
    {
        Char[][] chars = new Char[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                chars[y][x] = Char.DEFAULT_CHAR;
            }
        }

        return new CharArea(chars);
    }

    public int getHeight()
    {
        return chars.length;
    }

    public int getWidth()
    {
        return getHeight() >= 1 ? chars[0].length : 0;
    }

    public void draw(CharArray array, int y, int x)
    {
        if (y < getHeight())
        {
            int width = getWidth();
            int length = array.getArray().length;
            Char[] arrayChars = array.getArray();

            for (int i = 0; i < length; i++)
            {
                int px = i + x;

                if (px >= width)
                {
                    break;
                }

                chars[y][px] = arrayChars[i];
            }
        }
    }

    public void draw(CharArea area, int y, int x)
    {
        Char[][] areaChars = area.chars;

        for (int py = 0; py < area.getHeight(); py++)
        {
            for (int px = 0; px < area.getWidth(); px++)
            {
                chars[y + py][x + px] = areaChars[py][px];
            }
        }
    }

    public CharArea getPart(int posY, int posX, int height, int width)
    {
        Char[][] part = new Char[height][width];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                part[y][x] = chars[y + posY][x + posX];
            }
        }

        CharArea newFieldArea = new CharArea(part);
        return newFieldArea;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++)
        {
            Char[] array = chars[i];
            String string = CharArray.create(array).toString();
            sb.append(string);
            sb.append("\n");
        }

        return sb.toString();
    }
}
