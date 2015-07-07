package simplerogue.engine.test;

/**
 * Test in mocked environment.
 *
 * @author Adam Wyłuda
 */
public class IsolatedTest extends EngineTest
{
    @Override
    protected void registerManagers()
    {
        MockEngineConfigurator.registerMockManagers();
    }

    @Override
    protected void afterInit()
    {
        super.afterInit();
    }
}
