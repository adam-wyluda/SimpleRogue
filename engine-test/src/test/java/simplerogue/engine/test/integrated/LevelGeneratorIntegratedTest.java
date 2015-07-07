package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.ai.PlayerAI;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.Item;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.TestLevelRenderer;
import simplerogue.engine.levelgenerator.LevelGenerator;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.test.IntegratedTest;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.world.level.TestLevel;
import simplerogue.engine.test.world.level.field.Wall;
import simplerogue.engine.test.world.level.item.Sword;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Wyłuda
 */
public class LevelGeneratorIntegratedTest extends IntegratedTest
{
    private LevelGeneratorManager levelGeneratorManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        levelGeneratorManager = Managers.get(LevelGeneratorManager.class);
    }

    @Test
    public void testCreateLevel()
    {
        LevelRequest request = Managers.get(LevelGeneratorManager.class).createRequest(TestConsts.TEST_GENERATOR_NAME);
        LevelGenerator generator = levelGeneratorManager.getGenerator(request);

        Level level = generator.createLevel(request);
        assertTrue(level.is(TestLevel.class));

        FieldArea area = level.getArea();
        assertEquals(100, area.getWidth());

        Field wall = area.getFieldAt(0, 0);
        assertTrue(wall.is(Wall.class));

        Field floor = area.getFieldAt(1, 1);
        assertTrue(floor.is(Field.class));
        assertFalse(floor.isBlocking());
        assertEquals(1, floor.getElements().size());

        Item item = floor.getElements().get(0).reify(Item.class);
        assertTrue(item.is(Sword.class));

        Sword sword = item.reify(Sword.class);
        assertEquals(100, sword.getAttack());

        Actor player = area.getFieldAt(5, 5).getElements().get(0).reify(Actor.class);
        assertTrue(player.getAi() instanceof PlayerAI);

        String expected = "" +
                "XXXXXXXXXX\n" +
                "X!        \n" +
                "X O       \n" +
                "X         \n" +
                "X         \n" +
                "X    @    \n" +
                "X         \n" +
                "X         \n" +
                "X         \n" +
                "X         \n";
        String render = TestLevelRenderer.renderToString(area.getPart(0, 0, 10, 10));
        assertEquals(expected, render);
    }
}
