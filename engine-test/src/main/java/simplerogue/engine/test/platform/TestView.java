package simplerogue.engine.test.platform;

import lombok.Data;
import simplerogue.engine.platform.View;
import simplerogue.engine.view.Render;

/**
 * @author Adam Wyłuda
 */
@Data
public class TestView implements View
{
    public static final int HEIGHT = 15;
    public static final int WIDTH = 25;

    private Render render;

    @Override
    public String promptText(String title, String initialValue)
    {
        return initialValue;
    }

    @Override
    public int getHeight()
    {
        return HEIGHT;
    }

    @Override
    public int getWidth()
    {
        return WIDTH;
    }
}
