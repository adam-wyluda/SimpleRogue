package simplerogue.engine.ui.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import simplerogue.engine.view.Key;

/**
 * Part of {@link MenuLayer}.
 *
 * @author Adam Wyłuda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuOption
{
    private String text = "default";
    private OptionHandler<?> handler = NULL_HANDLER;

    public static MenuOption create(String text)
    {
        return new MenuOption(text, NULL_HANDLER);
    }

    public static MenuOption create(String text, OptionHandler<?> handler)
    {
        return new MenuOption(text, handler);
    }

    /**
     * Called when user performs action on menu option.
     *
     * @author Adam Wyłuda
     * @see MenuOption
     */
    public static interface OptionHandler<T extends MenuOption>
    {
        /**
         * Call menu action.
         *
         * @param option Menu option on which this action was called.
         * @param key Key which user has pressed.
         */
        void perform(T option, Key key);
    }

    public static final OptionHandler<?> NULL_HANDLER = new OptionHandler<MenuOption>()
    {
        @Override
        public void perform(MenuOption option, Key key)
        {
        }
    };
}
