package simplerogue.engine.ui;

import simplerogue.engine.view.Screen;

/**
 * Defines layer's layout bounds.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.ui.Layer
 */
public interface Layout
{
    int getY(Screen screen);

    int getX(Screen screen);

    int getHeight(Screen screen);

    int getWidth(Screen screen);
}
