package simplerogue.engine.test.platform;

import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.platform.PlatformManager;
import simplerogue.engine.platform.Storage;
import simplerogue.engine.platform.View;

/**
 * @author Adam Wyłuda
 */
public class TestPlatformManager extends AbstractManager implements PlatformManager
{
    @Override
    public Storage createStorage()
    {
        return new TestStorage();
    }

    @Override
    public View createView()
    {
        return new TestView();
    }
}
