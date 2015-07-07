package simplerogue.engine.ui.game;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.ui.FramedLayer;
import simplerogue.engine.ui.Layout;
import simplerogue.engine.ui.SideLayout;
import simplerogue.engine.view.Char;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.CharArray;
import simplerogue.engine.view.Key;
import simplerogue.engine.view.Screen;

import java.util.List;

/**
 * Layer showing menu options.
 *
 * @author Adam Wyłuda
 * @see MenuOption
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuLayer extends FramedLayer
{
    private static final String OPTION_FORMAT = " %s ";
    private static final String SELECTED_OPTION_FORMAT = "@(|GRAY) %s ";

    private String title;
    private Char frameChar = Char.create('#');
    private Char contentChar = Char.DEFAULT_CHAR;
    private List<MenuOption> options = Lists.newArrayList();

    private MenuOption selectedOption;

    private final Layout layout = SideLayout.createCenteredLayout(createLayout());

    protected MenuLayer(String name)
    {
        setName(name);
    }

    protected MenuLayer(String name, String title, Char frameChar, Char contentChar, List<MenuOption> options)
    {
        setName(name);

        this.title = title;
        this.frameChar = frameChar;
        this.contentChar = contentChar;
        this.options = options;
    }

    /**
     * Creates new menu layer instance.
     *
     * @param name Unique identifier of this menu.
     * @param title Formatted string (see {@link simplerogue.engine.view.CharArray#fromFormattedString(String)})
     *              which will be menu title. Will be omitted during rendering if empty.
     */
    public static MenuLayer create(String name,
                                   String title,
                                   Char frameChar, Char contentChar,
                                   List<MenuOption> options)
    {
        return new MenuLayer(name, title, frameChar, contentChar, options);
    }

    @Override
    protected void renderContent(CharArea area)
    {
        if (selectedOption == null)
        {
            setSelectedIndex(0);
        }

        int width = area.getWidth();
        int startY = 0;

        if (hasTitle())
        {
            startY = 2;

            CharArray formattedTitle = getFormattedTitle();
            int titleLength = formattedTitle.toString().length();
            int x = (width - titleLength) / 2;

            area.draw(formattedTitle, 0, x);
        }

        int maxWidth = calculateWidth();

        for (int i = 0; i < options.size(); i++)
        {
            MenuOption option = options.get(i);
            String text = optionToStringFixedWithSpaces(option, maxWidth);
            CharArray array = CharArray.fromFormattedString(text);

            int y = startY + i;

            area.draw(array, y, 0);
        }
    }

    @Override
    public void handleKeyPressed(Key key)
    {
        if (key == Key.DIRECTION_N)
        {
            moveSelectionDown();
        }
        else if (key == Key.DIRECTION_S)
        {
            moveSelectionUp();
        }
        else
        {
            optionSelected(key);
        }
    }

    private void moveSelectionUp()
    {
        int index = getSelectedIndex();
        index++;

        setSelectedIndex(index);
    }

    private void moveSelectionDown()
    {
        int index = getSelectedIndex();
        index--;

        setSelectedIndex(index);
    }

    private void optionSelected(Key key)
    {
        MenuOption.OptionHandler handler = selectedOption.getHandler();

        handler.perform(selectedOption, key);
    }

    private void setSelectedIndex(int index)
    {
        index = index < 0 ? index + options.size() : index;
        index = index % options.size();

        selectedOption = options.get(index);
    }

    private int getSelectedIndex()
    {
        int index;

        if (selectedOption == null || !options.contains(selectedOption))
        {
            index = 0;
        }
        else
        {
            index = options.indexOf(selectedOption);
        }

        return index;
    }

    private boolean hasTitle()
    {
        return !Strings.isNullOrEmpty(title);
    }

    private CharArray getFormattedTitle()
    {
        return CharArray.fromFormattedString(title);
    }

    private int calculateHeight()
    {
        int titleSize = hasTitle() ? 2 : 0;
        int optionCount = options.size();

        return titleSize + optionCount;
    }

    private int calculateWidth()
    {
        int width = title != null ? getFormattedTitle().toString().length() : 0;

        for (MenuOption option : options)
        {
            int textLength = CharArray.fromFormattedString(optionToString(option)).length();

            width = Math.max(width, textLength);
        }

        return width;
    }

    private String optionToString(MenuOption option)
    {
        String formatString;

        if (option == selectedOption)
        {
            formatString = SELECTED_OPTION_FORMAT;
        }
        else
        {
            formatString = OPTION_FORMAT;
        }

        String optionString = option.getText().toString();

        return String.format(formatString, optionString);
    }

    private String optionToStringFixedWithSpaces(MenuOption option, int maxWidth)
    {
        String formatString = optionToString(option);
        int textLength = CharArray.fromFormattedString(optionToString(option)).length();
        int spacesLeft = maxWidth - textLength;

        StringBuilder result = new StringBuilder(formatString);

        while (spacesLeft-- > 0)
        {
            result.append(" ");
        }

        return result.toString();
    }

    private Layout createLayout()
    {
        return new Layout()
        {
            @Override
            public int getY(Screen screen)
            {
                return screen.getHeight() / 2 - CONTENT_GAP;
            }

            @Override
            public int getX(Screen screen)
            {
                return screen.getWidth() / 2 - CONTENT_GAP;
            }

            @Override
            public int getHeight(Screen screen)
            {
                return calculateHeight() + 2 * CONTENT_GAP;
            }

            @Override
            public int getWidth(Screen screen)
            {
                return calculateWidth() + 2 * CONTENT_GAP;
            }
        };
    }
}
