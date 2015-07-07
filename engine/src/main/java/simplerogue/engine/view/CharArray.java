package simplerogue.engine.view;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor(staticName = "create")
public class CharArray
{
    private static final Pattern FORMATTED_PATTERN = Pattern.compile("@\\([0-9,\\|A-Z_]*\\)");

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

    private Char[] array;

    public static CharArray create(String text)
    {
        Char[] chars = new Char[text.length()];

        for (int i = 0; i < chars.length; i++)
        {
            chars[i] = Char.create(text.charAt(i));
        }

        return create(chars);
    }

    /**
     * Creates CharArray from given string. Default colors are white text and black background. To change color write
     * <i>@(TEXT_COLOR|BACKGROUND_COLOR)</i> with one of the colors from Color class or RGB triple in format
     * <i>(R,G,B)</i>.
     * <p>
     * Example:
     * "DEFAULT COLORS @(RED|BLACK) RED TEXT WITH BLACK BACKGROUND @(|0,0,255) RED ON GREEN BACKGROUND @(BLUE|)..."
     * <p>
     * Default color is white text on black background.
     *
     * @see Color
     */
    public static CharArray fromFormattedString(String string)
    {
        List<Char> chars = Lists.newArrayList();

        Color currentColor = DEFAULT_COLOR;
        Color currentBackground = DEFAULT_BACKGROUND_COLOR;

        Matcher matcher = FORMATTED_PATTERN.matcher(string);
        String[] split = FORMATTED_PATTERN.split(string, -1);

        List<Char> charList = Lists.newArrayList();
        for (int i = 0; i < split.length; i++)
        {
            String text = split[i];

            for (int j = 0; j < text.length(); j++)
            {
                char textChar = text.charAt(j);
                Char ch = Char.create(textChar, currentColor, currentBackground);

                charList.add(ch);
            }

            if (matcher.find())
            {
                String group = matcher.group();
                group = group.replaceAll("@\\(", "");
                group = group.replaceAll("\\)", "");

                String[] colors = group.split("\\|", -1);

                String colorText = colors[0].trim();
                String backgroundText = colors[1].trim();

                Color newColor = !colorText.isEmpty() ? Color.create(colorText) : currentColor;
                Color newBackground = !backgroundText.isEmpty() ? Color.create(backgroundText) : currentBackground;

                currentColor = newColor != null ? newColor : currentColor;
                currentBackground = newBackground != null ? newBackground : currentBackground;
            }
        }

        return create(charList.toArray(new Char[0]));
    }

    public int length()
    {
        return array.length;
    }

    public CharArray[] split(int maxLength)
    {
        int index = 0;
        CharArray[] result = new CharArray[array.length / maxLength + (array.length % maxLength != 0 ? 1 : 0)];

        while (index < array.length)
        {
            Char[] chars = Arrays.copyOfRange(array, index, Math.min(index + maxLength, array.length));
            result[index / maxLength] = create(chars);

            index += maxLength;
        }

        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Char ch : array)
        {
            sb.append(ch.getCharacter());
        }

        return sb.toString();
    }
}
