package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.test.GameTest;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.world.level.field.Wall;
import simplerogue.engine.test.world.level.item.Sword;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Wyłuda
 */
public class ObjectIntegratedTest extends GameTest
{
    private ObjectManager objectManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        objectManager = Managers.get(ObjectManager.class);
    }

    @Test
    public void testLoadPrototype()
    {
        Prototype wall = objectManager.getPrototype(TestConsts.WALL_NAME);

        assertEquals(TestConsts.WALL_NAME, wall.getName());
        assertTrue(wall.isField());
        assertEquals(5, wall.getFieldId());
        assertTrue(wall.isCached());

        Prototype floor = objectManager.getPrototype(TestConsts.FLOOR_NAME);

        assertEquals(TestConsts.FLOOR_NAME, floor.getName());
        assertTrue(floor.isField());
        assertEquals(10, floor.getFieldId());
        assertFalse(floor.isCached());

        Prototype sword = objectManager.getPrototype(TestConsts.SWORD_NAME);

        assertEquals(TestConsts.SWORD_NAME, sword.getName());
        assertFalse(sword.isField());
    }

    @Test
    public void testCreateObject()
    {
        Prototype prototype = objectManager.getPrototype(TestConsts.SWORD_NAME);

        Sword sword = objectManager.createObject(prototype.getName());
        assertEquals(20, sword.getAttack());

        Sword sword2 = objectManager.createObject(prototype.getName());
        assertTrue(sword.getId() != sword2.getId());
    }

    @Test
    public void testCreateField()
    {
        Wall wall = objectManager.createField(TestConsts.WALL_ID);

        assertEquals(50, wall.getTransparency());
    }

    @Test
    public void testGetObject()
    {
        Sword sword = objectManager.getObject(TestConsts.SWORD_ID);

        assertEquals(20, sword.getAttack());
        assertEquals(20, sword.getPosY());
        assertEquals(30, sword.getPosX());
    }

    @Test
    public void testCreateAndRegisterObject()
    {
        Managers.get(GameManager.class).loadGame(TestConsts.SAVE_NAME);

        Sword sword = objectManager.createObject(TestConsts.SWORD_NAME);

        Sword sameSword = objectManager.getObject(sword.getId());
        assertThat("Registered instance is not the same as original", sameSword, is(equalTo(sword)));
    }
}
