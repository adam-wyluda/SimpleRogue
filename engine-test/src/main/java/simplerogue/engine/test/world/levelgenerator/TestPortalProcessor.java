package simplerogue.engine.test.world.levelgenerator;

import simplerogue.engine.level.Portal;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;

/**
 * @author Adam Wyłuda
 */
public class TestPortalProcessor extends AbstractLevelProcessor
{
    @Override
    public void process(LevelRequest request)
    {
        Portal portal = Managers.get(ObjectManager.class).createObject(Portal.NAME);

        request.getLevel().putField(portal, 2, 2);
        request.setEntryPortal(portal);
    }

    @Override
    public String getName()
    {
        return "test_portal_processor";
    }
}
