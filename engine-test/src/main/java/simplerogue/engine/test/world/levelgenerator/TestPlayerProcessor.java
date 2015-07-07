package simplerogue.engine.test.world.levelgenerator;

import simplerogue.engine.level.Actor;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestPlayerProcessor extends AbstractLevelProcessor
{
    @Override
    public void process(LevelRequest request)
    {
        Actor player = Managers.get(ObjectManager.class).createObject(TestConsts.PLAYER_NAME);

        request.getLevel().putElement(player, 5, 5);

        request.setPlayer(player);
    }

    @Override
    public String getName()
    {
        return "test_player_processor";
    }
}
