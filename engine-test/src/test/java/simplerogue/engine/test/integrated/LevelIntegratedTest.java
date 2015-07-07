package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.level.Portal;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.test.GameTest;
import simplerogue.engine.test.TestConsts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Adam Wyłuda
 */
public class LevelIntegratedTest extends GameTest
{
    private GameManager gameManager;
    private LevelManager levelManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        gameManager = Managers.get(GameManager.class);
        levelManager = Managers.get(LevelManager.class);
    }

    @Test
    public void testGenerateLevel()
    {
        LevelRequest request = Managers.get(LevelGeneratorManager.class).createRequest(TestConsts.TEST_GENERATOR_NAME);

        Portal entry = levelManager.generateLevel(request);

        assertEquals(2, entry.getPosY());
        assertEquals(2, entry.getPosX());
    }

    @Test
    public void testMoveElement()
    {
        LevelRequest request = Managers.get(LevelGeneratorManager.class).createRequest(TestConsts.TEST_GENERATOR_NAME);
        Level beginningLevel = levelManager.generateLevel(request).getLevel();
        Portal nextLevelEntry = levelManager.generateLevel(request);
        Level nextLevel = nextLevelEntry.getLevel();

        Actor actor = Managers.get(ObjectManager.class).createObject(TestConsts.MONSTER_NAME);
        beginningLevel.putElement(actor, 10, 10);

        levelManager.moveElement(actor, nextLevelEntry);

        assertEquals(nextLevel, actor.getLevel());

        // TODO Fix this when actor landing field will be more determinable
//        assertEquals(nextLevelEntry, actor.getField());
//        assertEquals(2, actor.getPosY());
//        assertEquals(2, actor.getPosX());
    }

    @Test
    public void testGetLevel()
    {
        gameManager.loadGame(TestConsts.SAVE_NAME);
        Level level = levelManager.getLevel(TestConsts.LEVEL_NAME);

        assertNotNull(level);
        assertFalse(level.getObjects().isEmpty());
    }
}
