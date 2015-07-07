package simplerogue.engine.test;

import simplerogue.engine.manager.Managers;
import simplerogue.engine.test.platform.TestView;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.ViewManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldManager;

/**
 * @author Adam Wyłuda
 */
public class TestHelper
{
    public static World getWorld()
    {
        return Managers.get(WorldManager.class).getWorlds().get(0);
    }

    public static CharArea getRender()
    {
        ViewManager viewManager = Managers.get(ViewManager.class);
        viewManager.refreshView();

        TestView view = viewManager.getView();
        CharArea area = view.getRender().getCharArea();

        return area;
    }
}
