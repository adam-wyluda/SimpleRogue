package simplerogue.engine.test.world.levelgenerator;

import simplerogue.engine.level.Level;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.world.level.item.Sword;

/**
 * @author Adam Wyłuda
 */
public class TestItemProcessor extends AbstractLevelProcessor
{
    @Override
    public void process(LevelRequest request)
    {
        Level level = request.getLevel();
        Sword sword = Managers.get(ObjectManager.class).createObject(TestConsts.SWORD_NAME);

        sword.setAttack(100);

        level.putElement(sword, 1, 1);
    }

    @Override
    public String getName()
    {
        return "test_item_processor";
    }
}
