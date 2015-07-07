package simplerogue.engine.test.ai;

import simplerogue.engine.ai.AbstractFieldHandler;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestFieldHandler extends AbstractFieldHandler<Field>
{
    @Override
    public void handle(Field field, FieldElement element)
    {

    }

    @Override
    public String getName()
    {
        return TestConsts.TEST_FIELD_HANDLER_NAME;
    }
}
