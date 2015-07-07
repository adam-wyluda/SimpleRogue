package simplerogue.engine.ui;

import simplerogue.engine.view.Screen;

/**
 * @author Adam Wyłuda
 */
public class AbstractLayout implements Layout
{
    @Override
    public int getY(Screen screen)
    {
        return 0;
    }

    @Override
    public int getX(Screen screen)
    {
        return 0;
    }

    @Override
    public int getHeight(Screen screen)
    {
        return 0;
    }

    @Override
    public int getWidth(Screen screen)
    {
        return 0;
    }
}
