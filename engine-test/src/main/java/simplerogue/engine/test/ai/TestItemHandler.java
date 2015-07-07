package simplerogue.engine.test.ai;

import simplerogue.engine.ai.AbstractItemHandler;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.ObjectInstance;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestItemHandler extends AbstractItemHandler<Item>
{
    @Override
    public void useItem(Item item, Actor user, ObjectInstance target)
    {

    }

    @Override
    public String getName()
    {
        return TestConsts.TEST_ITEM_HANDLER_NAME;
    }
}
