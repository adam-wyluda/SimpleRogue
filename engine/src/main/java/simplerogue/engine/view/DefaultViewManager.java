package simplerogue.engine.view;

import simplerogue.engine.level.Direction;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.PlatformManager;
import simplerogue.engine.platform.View;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.ui.game.GameLayer;

/**
 * @author Adam Wyłuda
 */
public class DefaultViewManager extends AbstractManager implements ViewManager
{
    private UIManager uiManager;

    private View view;

    @Override
    public void init()
    {
        uiManager = Managers.get(UIManager.class);

        PlatformManager platformManager = Managers.get(PlatformManager.class);
        view = platformManager.createView();
    }

    @Override
    public void refreshView()
    {
        Screen screen = getScreen();
        CharArea area = uiManager.renderLayers(screen);

        Render render = new Render();
        render.setCharArea(area);

        if (uiManager.isRegistered(GameLayer.NAME))
        {
            GameLayer gameLayer = uiManager.getLayer(GameLayer.NAME);
            Direction shiftDirection = gameLayer.getDirection();
            render.setShiftDirection(shiftDirection);
        }

        view.setRender(render);
    }

    @Override
    public <V extends View> V getView()
    {
        return (V) view;
    }

    @Override
    public void handleKey(Key key)
    {
        uiManager.handleKeyPressed(key);
    }

    @Override
    public Screen getScreen()
    {
        int height = view.getHeight();
        int width = view.getWidth();

        Screen screen = new Screen(height, width);

        return screen;
    }
}
