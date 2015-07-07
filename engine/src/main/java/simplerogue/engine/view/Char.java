package simplerogue.engine.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import simplerogue.engine.object.Model;

/**
 * Extends char primitive with additional data, like color.
 *
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Char
{
    public static final Char DEFAULT_CHAR = new Char(' ', Color.WHITE, Color.BLACK);

    private static final String CHAR = "char";
    private static final String COLOR = "color";
    private static final String BACKGROUND = "background";

    private final char character;
    private final Color color;
    private final Color backgroundColor;

    public static Char create(char character)
    {
        return create(character, DEFAULT_CHAR.getColor(), DEFAULT_CHAR.getBackgroundColor());
    }

    public static Char create(char character, Color color)
    {
        return create(character, color, DEFAULT_CHAR.getBackgroundColor());
    }

    public static Char create(char character, Color color, Color backgroundColor)
    {
        return new Char(character, color, backgroundColor);
    }

    public static Char create(Model model)
    {
        char character = DEFAULT_CHAR.getCharacter();
        Color color = DEFAULT_CHAR.getColor();
        Color backgroundColor = DEFAULT_CHAR.getBackgroundColor();

        if (model.has(CHAR))
        {
            character = model.getString(CHAR).charAt(0);
        }

        if (model.has(COLOR))
        {
            color = Color.create(model.getString(COLOR));
        }

        if (model.has(BACKGROUND))
        {
            backgroundColor = Color.create(model.getString(BACKGROUND));
        }

        return new Char(character, color, backgroundColor);
    }
}
