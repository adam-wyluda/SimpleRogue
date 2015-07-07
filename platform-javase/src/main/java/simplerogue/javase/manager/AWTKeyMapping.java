package simplerogue.javase.manager;

import com.google.common.collect.Maps;
import simplerogue.engine.view.Key;

import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class AWTKeyMapping
{
    /**
     * AWT Key code -> Key
     */
    public static final Map<Integer, Key> MAPPING = Maps.newHashMap();

    static
    {
        MAPPING.put(KeyEvent.VK_NUMPAD2, Key.DIRECTION_S);
        MAPPING.put(KeyEvent.VK_DOWN, Key.DIRECTION_S);
        MAPPING.put(KeyEvent.VK_X, Key.DIRECTION_S);

        MAPPING.put(KeyEvent.VK_NUMPAD4, Key.DIRECTION_W);
        MAPPING.put(KeyEvent.VK_LEFT, Key.DIRECTION_W);
        MAPPING.put(KeyEvent.VK_A, Key.DIRECTION_W);

        MAPPING.put(KeyEvent.VK_NUMPAD6, Key.DIRECTION_E);
        MAPPING.put(KeyEvent.VK_RIGHT, Key.DIRECTION_E);
        MAPPING.put(KeyEvent.VK_D, Key.DIRECTION_E);

        MAPPING.put(KeyEvent.VK_NUMPAD8, Key.DIRECTION_N);
        MAPPING.put(KeyEvent.VK_UP, Key.DIRECTION_N);
        MAPPING.put(KeyEvent.VK_W, Key.DIRECTION_N);

        MAPPING.put(KeyEvent.VK_NUMPAD1, Key.DIRECTION_SW);
        MAPPING.put(KeyEvent.VK_Z, Key.DIRECTION_SW);

        MAPPING.put(KeyEvent.VK_NUMPAD3, Key.DIRECTION_SE);
        MAPPING.put(KeyEvent.VK_C, Key.DIRECTION_SE);

        MAPPING.put(KeyEvent.VK_NUMPAD9, Key.DIRECTION_NE);
        MAPPING.put(KeyEvent.VK_E, Key.DIRECTION_NE);

        MAPPING.put(KeyEvent.VK_NUMPAD7, Key.DIRECTION_NW);
        MAPPING.put(KeyEvent.VK_Q, Key.DIRECTION_NW);

        MAPPING.put(KeyEvent.VK_NUMPAD5, Key.DIRECTION_C);
        MAPPING.put(KeyEvent.VK_S, Key.DIRECTION_C);

        MAPPING.put(KeyEvent.VK_NUMPAD0, Key.CHANGE_PLAYER_MODE);
        MAPPING.put(KeyEvent.VK_SHIFT, Key.CHANGE_PLAYER_MODE);

        MAPPING.put(KeyEvent.VK_ENTER, Key.SHOW_MENU);
        MAPPING.put(KeyEvent.VK_SPACE, Key.SHOW_MENU);

        MAPPING.put(KeyEvent.VK_1, Key.ACTION_1);
        MAPPING.put(KeyEvent.VK_2, Key.ACTION_2);
        MAPPING.put(KeyEvent.VK_3, Key.ACTION_3);
        MAPPING.put(KeyEvent.VK_4, Key.ACTION_4);
    }
}
