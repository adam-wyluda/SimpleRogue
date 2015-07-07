package simplerogue.engine.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor(staticName = "create")
public class Color
{
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);

    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color YELLOW = new Color(255, 255, 0);

    public static final Color ORANGE = new Color(255, 127, 0);

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color LIGHT_GRAY = new Color(191, 191, 191);
    public static final Color GRAY = new Color(127, 127, 127);
    public static final Color DARK_GRAY = new Color(63, 63, 63);
    public static final Color BLACK = new Color(0, 0, 0);

    private static final String RGB_PATTERN = "[0-9]+,[0-9]+,[0-9]+";
    private static final String NAME_PATTERN = "[A-Z_]+";

    private final int red;
    private final int green;
    private final int blue;

    public static Color create(String colorText)
    {
        if (colorText.matches(RGB_PATTERN))
        {
            String[] split = colorText.split(",");
            int red = Integer.parseInt(split[0]);
            int green = Integer.parseInt(split[1]);
            int blue = Integer.parseInt(split[2]);

            return Color.create(red, green, blue);
        }
        else if (colorText.matches(NAME_PATTERN))
        {
            try
            {
                Field field = Color.class.getField(colorText);
                Color color = (Color) field.get(null);

                return color;
            }
            catch (Exception e)
            {
                throw new RuntimeException("Can't parse color text: " + colorText + ", message: " + e.getMessage());
            }
        }
        else
        {
            throw new RuntimeException("Can't parse color text: " + colorText);
        }
    }

    public Color negate()
    {
        return create(255 - red, 255 - green, 255 - blue);
    }

    @Override
    public String toString()
    {
        return String.format("%s,%s,%s", red, green, blue);
    }
}
