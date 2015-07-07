package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectUtil;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.save.GameSave;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.test.IntegratedTest;
import simplerogue.engine.test.TestConsts;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests game and save modules.
 *
 * @author Adam Wyłuda
 */
public class SaveIntegratedTest extends IntegratedTest
{
    private SaveManager saveManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        saveManager = Managers.get(SaveManager.class);
    }

    @Test
    public void testListSaves()
    {
        List<String> saves = saveManager.listSaves();

        assertEquals(1, saves.size());
        assertEquals(TestConsts.SAVE_NAME, saves.get(0));
    }

    @Test
    public void testLoadGame()
    {
        GameSave save = saveManager.loadGame(TestConsts.SAVE_NAME);

        String value = save.getProperties().getString(TestConsts.SECRET_PROPERTY);
        assertEquals(TestConsts.SECRET_VALUE, value);

        assertEquals(1, save.getLevelSaves().size());
        GameSave.LevelSave levelSave = save.getLevelSaves().get(TestConsts.LEVEL_NAME);
        assertNotNull(levelSave);

        int floor = levelSave.getMap()[TestConsts.WALL_POS_Y - 1][0];
        int wall = levelSave.getMap()[TestConsts.WALL_POS_Y][0];
        assertEquals(TestConsts.FLOOR_ID, floor);
        assertEquals(TestConsts.WALL_ID, wall);

        Model properties = levelSave.getProperties();
        String levelProperty = properties.getString(TestConsts.LEVEL_PROPERTY);
        assertEquals(TestConsts.LEVEL_PROPERTY_VALUE, levelProperty);

        Map<Integer, Model> objects = levelSave.getObjectProperties();
        assertFalse(objects.isEmpty());
        assertNotNull(objects.get(TestConsts.PLAYER_ID));
    }

    @Test
    public void testSaveGame()
    {
        GameSave save = saveManager.loadGame(TestConsts.SAVE_NAME);
        save.getProperties().put(TestConsts.SECRET_PROPERTY, "my_value");

        GameSave.LevelSave levelSave = save.getLevelSaves().get(TestConsts.LEVEL_NAME);
        levelSave.getProperties().put("my_prop", "xyz");

        assertEquals(TestConsts.FLOOR_ID, levelSave.getMap()[1][1]);
        levelSave.getMap()[1][1] = TestConsts.WALL_ID;

        assertFalse(levelSave.getObjectProperties().isEmpty());
        levelSave.getObjectProperties().clear();

        Model model = new Model();
        ObjectUtil.setId(model, 555);
        levelSave.getObjectProperties().put(555, model);

        saveManager.saveGame(save);

        GameSave newSave = saveManager.loadGame(TestConsts.SAVE_NAME);
        assertEquals("my_value", save.getProperties().getString(TestConsts.SECRET_PROPERTY));

        GameSave.LevelSave newLevel = newSave.getLevelSaves().get(TestConsts.LEVEL_NAME);
        assertEquals("xyz", newLevel.getProperties().getString("my_prop"));
        assertEquals(TestConsts.WALL_ID, levelSave.getMap()[1][1]);

        assertEquals(1, levelSave.getObjectProperties().size());
        Model newModel = levelSave.getObjectProperties().values().iterator().next();
        assertEquals(555, ObjectUtil.getId(newModel));
    }

    @Test
    public void testLoadPrototypes()
    {
        List<Prototype> prototypes = saveManager.loadPrototypes(TestConsts.WORLD_NAME);

        boolean hasPlayer = false;
        boolean hasFloor = false;
        boolean hasBow = false;

        for (Prototype prototype : prototypes)
        {
            String name = prototype.getName();

            hasPlayer = hasPlayer || name.equals(TestConsts.PLAYER_NAME);
            hasFloor = hasFloor || name.equals(TestConsts.FLOOR_NAME);
            hasBow = hasBow || name.equals(TestConsts.BOW_NAME);
        }

        assertTrue(hasPlayer);
        assertTrue(hasFloor);
        assertTrue(hasBow);
    }
}
