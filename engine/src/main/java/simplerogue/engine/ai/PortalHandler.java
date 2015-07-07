package simplerogue.engine.ai;

import simplerogue.engine.level.Actor;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.level.Portal;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;

/**
 * @author Adam Wyłuda
 */
public class PortalHandler extends AbstractFieldHandler<Portal>
{
    public static final String NAME = "portal";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void handle(Portal portal, FieldElement element)
    {
        LevelManager levelManager = Managers.get(LevelManager.class);

        if (!portal.isPointingToOtherPortal())
        {
            // Generate new level
            LevelRequest request = portal.getLevelRequest();
            request.setFrom(portal);

            Portal nextPortal = levelManager.generateLevel(request);
            portal.setNextPortal(nextPortal);
        }

        if (element.is(Actor.class))
        {
            // Move actor to the next level
            Portal nextPortal = portal.getNextPortal();
            levelManager.moveElement(element, nextPortal);
        }
    }
}
