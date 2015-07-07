package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.application.ApplicationManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.test.IntegratedTest;

/**
 * @author Adam Wyłuda
 */
public class ApplicationIntegratedTest extends IntegratedTest
{
    private ApplicationManager applicationManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        applicationManager = Managers.get(ApplicationManager.class);
    }

    @Test
    // Gradle process resources must be called to make it work inside IDE
    public void testVersionLoading()
    {
        // Should not throw any exception
        applicationManager.getEngineVersion();
    }
}
