package simplerogue.engine.test.world.levelgenerator;

import com.google.common.base.Optional;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.Level;
import simplerogue.engine.levelgenerator.util.GeneratorUtil;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
public class TestFieldAreaProcessor extends AbstractLevelProcessor
{
    @Override
    public void process(LevelRequest request)
    {
        ObjectManager objectManager = Managers.get(ObjectManager.class);

        Level level = request.getLevel();
        FieldArea area = level.getArea();
        Prototype wall = objectManager.getPrototype(TestConsts.WALL_NAME);
        Prototype floor = objectManager.getPrototype(TestConsts.FLOOR_NAME);

        GeneratorUtil.drawRectangle(level, wall, Optional.of(floor),
                0, 0, area.getHeight(), area.getWidth());
    }

    @Override
    public String getName()
    {
        return "test_field_area_processor";
    }
}
