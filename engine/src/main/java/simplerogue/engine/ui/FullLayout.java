package simplerogue.engine.ui;

import lombok.AllArgsConstructor;
import simplerogue.engine.view.Screen;

/**
 * Layout that takes whole screen.
 *
 * @author Adam Wyłuda
 */
@AllArgsConstructor(staticName = "create")
public class FullLayout implements Layout
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
        return screen.getHeight();
    }

    @Override
    public int getWidth(Screen screen)
    {
        return screen.getWidth();
    }
}
