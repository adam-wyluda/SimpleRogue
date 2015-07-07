package simplerogue.engine.ai.util.vision;

import com.google.common.base.Preconditions;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.FieldElement;

/**
 * @author Adam Wyłuda
 */
public class FOVUtil
{
    public static boolean isOnSight(FieldElement start, FieldElement end)
    {
        return isOnSight(start.getField(), end.getField());
    }

    public static boolean isOnSight(Field start, Field end)
    {
        Preconditions.checkArgument(start.getLevel() == end.getLevel(), "Fields must be on the same level");

        int y0 = start.getPosY();
        int x0 = start.getPosX();
        int y1 = end.getPosY();
        int x1 = end.getPosX();

        return isOnSight(start.getLevel().getArea(), y0, x0, y1, x1);
    }

    public static boolean isOnSight(FieldArea area, int y0, int x0, int y1, int x1)
    {
        // based on http://www.roguebasin.com/index.php?title=Bresenham%27s_Line_Algorithm

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        int t;

        if (steep)
        {
            // Swap (x0, y0)
            t = x0;
            x0 = y0;
            y0 = t;

            // Swap (x1, y1)
            t = x1;
            x1 = y1;
            y1 = t;
        }

        if (x0 > x1)
        {
            // Swap (x0, x1)
            t = x0;
            x0 = x1;
            x1 = t;

            // Swap (y0, y1)
            t = y0;
            y0 = y1;
            y1 = t;
        }

        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);
        int err = dx / 2;
        int ystep = y0 < y1 ? 1 : -1;

        int y = y0;
        boolean firstRun = true;

        for (int x = x0; x < x1; x++)
        {
            Field field;

            if (steep)
            {
                field = area.getFieldAt(x, y);
            }
            else
            {
                field = area.getFieldAt(y, x);
            }

            if (!firstRun && field.isBlocking())
            {
                return false;
            }
            firstRun = false;

            err -= dy;

            if (err < 0)
            {
                y += ystep;
                err += dx;
            }
        }

        return true;
    }
}
