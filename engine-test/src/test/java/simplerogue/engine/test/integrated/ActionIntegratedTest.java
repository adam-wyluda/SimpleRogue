package simplerogue.engine.test.integrated;

import org.junit.Test;
import simplerogue.engine.action.Action;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.action.MoveAction;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.PlayerAI;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.TestLevelRenderer;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Level;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.test.GameTest;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.test.world.level.actor.Monster;

import static org.junit.Assert.assertEquals;

/**
 * @author Adam Wyłuda
 */
public class ActionIntegratedTest extends GameTest
{
    private ActionManager actionManager;
    private AIManager aiManager;
    private GameManager gameManager;
    private ObjectManager objectManager;

    @Override
    protected void afterInit()
    {
        super.afterInit();

        actionManager = Managers.get(ActionManager.class);
        aiManager = Managers.get(AIManager.class);
        gameManager = Managers.get(GameManager.class);
        objectManager = Managers.get(ObjectManager.class);
    }

    @Test
    public void testMakeTurn()
    {
        String expectedBefore = "" +
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
        String expectedAfter = "" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "XXXXXXXXXXXXXXXXXXXX\n" + // TestConsts.WALL_POS_Y == 5
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "           @        \n" + // player at (9, 11)
                "                    \n" +
                "                    \n" +
                "                    \n" +
                "             M      \n" + // monster at (13, 13)
                "                    \n";

        Actor player = gameManager.getPlayer();
        PlayerAI playerAI = aiManager.getAI(PlayerAI.NAME);

        // This will also test actionManager.submitAction()
        Action moveAction = MoveAction.create(player, Direction.NE);
        playerAI.setNextAction(moveAction);

        // Monster grow 5 size every turn
        Monster monster = objectManager.getObject(TestConsts.MONSTER_ID);
        assertEquals(10, monster.getSize());

        Level level = player.getLevel();

        String renderBefore = TestLevelRenderer.renderToString(level.getArea().getPart(0, 0, 15, 20));
        actionManager.makeTurn();
        String renderAfter = TestLevelRenderer.renderToString(level.getArea().getPart(0, 0, 15, 20));

        assertEquals(expectedBefore, renderBefore);
        assertEquals(expectedAfter, renderAfter);
        assertEquals(15, monster.getSize());
    }
}
