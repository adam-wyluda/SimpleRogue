package simplerogue.engine.level;

import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;

/**
 * Renders {@link simplerogue.engine.level.FieldArea} parts. <p/>
 *
 * Useful for testing and debugging.
 *
 * @author Adam Wyłuda
 */
public class TestLevelRenderer
{
    public static String renderToString(FieldArea area)
    {
        StringBuilder render = new StringBuilder();

        for (int y = 0; y < area.getHeight(); y++)
        {
            for (int x = 0; x < area.getWidth(); x++)
            {
                Field field = area.getFieldAt(y, x);
                Character fieldChar = field.getCharacter().getCharacter();

                render.append(fieldChar);
            }
            render.append('\n');
        }

        return render.toString();
    }
}
