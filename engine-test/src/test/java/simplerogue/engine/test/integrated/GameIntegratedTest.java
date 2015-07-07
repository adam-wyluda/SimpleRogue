package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.game.EnumGameProperty;
import simplerogue.engine.game.Game;
import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.game.GameProperty;
import simplerogue.engine.level.TestLevelRenderer;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Item;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.test.GameTest;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.game.Difficulty;
import simplerogue.engine.test.game.TestGameConfiguration;
import simplerogue.engine.test.world.level.TestLevel;
import simplerogue.engine.test.world.level.item.Bow;
import simplerogue.engine.test.world.level.item.Staff;
import simplerogue.engine.test.world.level.item.Sword;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Wyłuda
 */
public class GameIntegratedTest extends GameTest
{
    private GameManager gameManager;
    private ObjectManager objectManager;
    private LevelManager levelManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        gameManager = Managers.get(GameManager.class);
        objectManager = Managers.get(ObjectManager.class);
        levelManager = Managers.get(LevelManager.class);
    }

    /**
     * Look for objects defined in test/resources/saves/...
     *
     * @see simplerogue.engine.test.util.LevelMapGenerator
     */
    @Test
    public void testLoadGame()
    {
        Game game = gameManager.loadGame(TestConsts.SAVE_NAME);
        assertEquals(TestConsts.SAVE_NAME, game.getName());

        GameConfiguration config = game.getConfig();
        assertEquals(TestConsts.TEST_GAME_CONFIGURATOR_NAME, config.getName());

        Level level = levelManager.getLevel(TestConsts.LEVEL_NAME);
        assertFalse(level.getObjects().isEmpty());

        Actor player = config.getPlayer();
        assertEquals(level, player.getLevel());
        assertEquals(TestConsts.PLAYER_ID, player.getId());

        List<Item> items = player.getItems();
        assertEquals(2, items.size());

        Sword playerSword = items.get(0).reify(Sword.class);
        assertEquals(TestConsts.PLAYER_SWORD_ID, playerSword.getId());
        assertEquals(TestConsts.PLAYER_SWORD_ATTACK, playerSword.getAttack());

        Bow playerBow = items.get(1).reify(Bow.class);
        assertEquals(TestConsts.PLAYER_BOW_ID, playerBow.getId());

        String expected = "" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "XXXXXXXXXXXXXXXXXXXX\n" + // TestConsts.WALL_POS_Y == 5
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "          @         \n" + // player at (10, 10)
                "                    \n" +
                "                    \n" +
                "             M      \n" + // monster at (13, 13)
                "                    \n";
        String render = TestLevelRenderer.renderToString(level.getArea().getPart(0, 0, 15, 20));
        assertEquals(expected, render);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testSaveGame()
    {
        GameConfigurator configurator = gameManager.getConfigurator(TestConsts.TEST_GAME_CONFIGURATOR_NAME);
        TestGameConfiguration config = configurator.createInitialConfiguration().reify(TestGameConfiguration.class);

        GameProperty<Difficulty, ?> property = (GameProperty<Difficulty, ?>) configurator.getProperties().get(0);
        EnumGameProperty<Difficulty, TestGameConfiguration> difficultyProperty = property.reify(EnumGameProperty.class);
        difficultyProperty.setValue(config, Difficulty.HARD);

        Game newGame = gameManager.newGame(config);
        assertEquals(Difficulty.HARD, difficultyProperty.getValue(config));

        Staff staff = objectManager.createObject(TestConsts.STAFF_NAME);
        staff.setLength(150);

        Actor player = gameManager.getPlayer();
        assertNotNull(player);
        player.getItems().add(staff);
        assertEquals(1, player.getItems().size());

        Level level = player.getLevel();
        assertTrue(level.is(TestLevel.class));
        TestLevel testLevel = level.reify(TestLevel.class);
        testLevel.setRoomCount(5);

        gameManager.saveGame();

        // Clear game
        gameManager.loadGame(TestConsts.SAVE_NAME);
        player = gameManager.getPlayer();
        assertNotEquals(1, player.getItems().size());

        // Load again
        Game loadedGame = gameManager.loadGame(newGame.getName());
        player = loadedGame.getConfig().getPlayer();
        assertEquals(1, player.getItems().size());
        assertEquals(150, player.getItems().get(0).reify(Staff.class).getLength());

        level = player.getLevel();
        assertTrue(level.is(TestLevel.class));
        testLevel = level.reify(TestLevel.class);
        assertEquals(5, testLevel.getRoomCount());
    }
}
