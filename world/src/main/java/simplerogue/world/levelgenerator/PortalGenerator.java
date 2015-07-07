package simplerogue.world.levelgenerator;

import simplerogue.engine.level.Portal;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.levelgenerator.util.BSP;
import simplerogue.engine.levelgenerator.util.BSPUtil;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.world.WorldConsts;

/**
 * @author Adam Wyłuda
 */
public class PortalGenerator extends AbstractLevelProcessor<DefaultLevelRequest>
{
    public static final String NAME = "portal_generator";

    @Override
    public void process(DefaultLevelRequest request)
    {
        if (request.getFrom() != null)
        {
            placePreviousPortal(request);
        }

        placeNextPortal(request);
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    private void placePreviousPortal(DefaultLevelRequest request)
    {
        Portal fromPortal = request.getFrom();
        Portal previousPortal = Managers.get(ObjectManager.class).createObject(WorldConsts.PORTAL_UP);

        fromPortal.bind(previousPortal);
        request.setEntryPortal(previousPortal);

        BSP.Node mostLeftNode = BSPUtil.mostLeftNode(request.getBsp());
        mostLeftNode.getRoom().putField(previousPortal);
    }

    private void placeNextPortal(DefaultLevelRequest request)
    {
        int nextMonsterCount;

        if (request.getFrom() == null)
        {
            nextMonsterCount = 10;
        }
        else
        {
            Portal<DefaultLevelRequest> portal = request.getFrom();
            nextMonsterCount = portal.getLevelRequest().getMonsterCount() + 5;
        }

        LevelRequest nextLevelRequest = createRequest(request.getLevelNumber() + 1, nextMonsterCount);
        Portal nextPortal = Portal.create(WorldConsts.PORTAL_DOWN, nextLevelRequest);

        BSP.Node mostRightNode = BSPUtil.mostRightNode(request.getBsp());
        mostRightNode.getRoom().putField(nextPortal);
    }

    private DefaultLevelRequest createRequest(int levelNumber, int monsterCount)
    {
        DefaultLevelRequest request =
                Managers.get(LevelGeneratorManager.class).createRequest(DefaultLevelGenerator.NAME);

        request.setMonsterCount(monsterCount);
        request.setLevelNumber(levelNumber);

        return request;
    }
}
