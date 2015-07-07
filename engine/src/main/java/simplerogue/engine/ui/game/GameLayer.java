package simplerogue.engine.ui.game;

import com.google.common.base.Optional;
import lombok.Data;
import simplerogue.engine.action.Action;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.action.MoveAction;
import simplerogue.engine.ai.PlayerAI;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.Point;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.FullLayout;
import simplerogue.engine.ui.Layer;
import simplerogue.engine.ui.Layout;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.Char;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.CharUtil;
import simplerogue.engine.view.Color;
import simplerogue.engine.view.Key;

/**
 * Renders game. Default instance can be accessed with {@link #getInstance()}
 *
 * @author Adam Wyłuda
 */
@Data
public class GameLayer extends Layer
{
    /**
     * Provides player actions.
     *
     * @param <P> Player type.
     */
    public interface ActionProvider<P extends Actor>
    {
        Optional<Action> interpret(P player, Key key);
    }

    public interface TargetHandler
    {
        void handleTargetSelected(int targetY, int targetX);
    }

    // TODO Implement Target mode
    public static enum GameMode
    {
        ACTION, TARGET;
    }

    public static final String NAME = "GAME_LAYER";

    private static final Color DEFAULT_TARGET_COLOR = Color.LIGHT_GRAY;

    private GameManager gameManager = Managers.get(GameManager.class);

    private GameMenuLayer gameMenu;

    private GameMode mode = GameMode.ACTION;

    private final ActionProvider<Actor> defaultActionProvider = new DefaultActionProvider<>();
    private boolean callDefaultActionProvider = true;
    private ActionProvider actionProvider;
    private Direction direction = Direction.C;

    private TargetHandler targetHandler = new DefaultTargetHandler();
    private Color targetColor = DEFAULT_TARGET_COLOR;
    private int targetY;
    private int targetX;

    private GameLayer()
    {
        // Game layer should be always on back
        setPriority(BACK_PRIORITY);

        setGameMenu(new GameMenuLayer());
    }

    public static GameLayer create()
    {
        return create(FullLayout.create());
    }

    public static GameLayer create(Layout layout)
    {
        GameLayer gameLayer = new GameLayer();
        gameLayer.setLayout(layout);

        return gameLayer;
    }

    public static GameLayer getInstance()
    {
        return Managers.get(UIManager.class).getLayer(NAME);
    }

    public static void register()
    {
        Managers.get(UIManager.class).registerLayer(new GameLayer());
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    public void setGameMenu(GameMenuLayer gameMenu)
    {
        UIManager uiManager = Managers.get(UIManager.class);

        if (this.gameMenu != null)
        {
            this.gameMenu.setVisible(false);
        }
        this.gameMenu = gameMenu;

        uiManager.registerLayer(gameMenu);
    }

    public Direction getDirection()
    {
        return isActive() ? direction : Direction.C;
    }

    @Override
    public void render(CharArea area)
    {
        if (!gameManager.isGameLoaded())
        {
            return;
        }

        Level level = gameManager.getPlayer().getLevel();

        int height = area.getHeight();
        int width = area.getWidth();

        Point cameraPoint = getCameraPoint();
        int playerY = cameraPoint.getY();
        int playerX = cameraPoint.getX();

        int startY = playerY - height / 2;
        int startX = playerX - width / 2;

        FieldArea part = level.getArea().getPart(startY, startX, height, width);
        CharArea transform = CharUtil.transform(part);

        // Change targeted character background
        if (mode == GameMode.TARGET)
        {
            Char targetChar = transform.getChars()[height / 2][width / 2];
            targetChar = Char.create(targetChar.getCharacter(), targetChar.getColor(), targetColor);
            transform.getChars()[height / 2][width / 2] = targetChar;
        }

        area.draw(transform, 0, 0);
    }

    @Override
    public void handleKeyPressed(Key key)
    {
        // Reset direction
        direction = Direction.C;

        switch (key)
        {
            case CHANGE_PLAYER_MODE:
                changePlayerMode();
                break;

            case SHOW_MENU:
                gameMenu.setVisible(true);
                Managers.get(UIManager.class).setActiveLayer(gameMenu);
                break;

            default:
                interpretKey(key);
        }
    }

    private void changePlayerMode()
    {
        resetTargeting();
        mode = GameMode.values()[(mode.ordinal() + 1) % GameMode.values().length];
    }

    private void resetTargeting()
    {
        Actor player = gameManager.getPlayer();

        targetY = player.getPosY();
        targetX = player.getPosX();
    }

    private void interpretKey(Key key)
    {
        switch (mode)
        {
            case ACTION:
                interpretPlayerAction(key);
                break;
            case TARGET:
                interpretTargetAction(key);
                break;
        }
    }

    private void interpretPlayerAction(Key key)
    {
        Actor player = gameManager.getPlayer();
        PlayerAI ai = player.getAi().reify(PlayerAI.class);

        Optional<Action> action = Optional.absent();

        if (callDefaultActionProvider)
        {
            action = defaultActionProvider.interpret(player, key);
        }

        if (!action.isPresent())
        {
            action = actionProvider.interpret(player, key);
        }

        if (action.isPresent())
        {
            ai.setNextAction(action.get());

            ActionManager actionManager = Managers.get(ActionManager.class);
            actionManager.makeTurn();
        }
    }

    private void interpretTargetAction(Key key)
    {
        if (key == Key.DIRECTION_C)
        {
            targetHandler.handleTargetSelected(targetY, targetX);
        }
        else
        {
            direction = Direction.fromKey(key);

            targetY = direction.transformY(targetY);
            targetX = direction.transformX(targetX);
        }
    }

    private Point getCameraPoint()
    {
        switch (mode)
        {
            default:
            case ACTION:
                Actor player = gameManager.getPlayer();
                return Point.create(player.getPosY(), player.getPosX());
            case TARGET:
                return Point.create(targetY, targetX);
        }
    }

    public class DefaultActionProvider<P extends Actor> implements ActionProvider<P>
    {
        /**
         * Returns move action, if key points to non-blocking field.
         */
        @Override
        public Optional<Action> interpret(P player, Key key)
        {
            Optional<Action> action = Optional.absent();

            if (Direction.isDirection(key))
            {
                Direction direction = Direction.fromKey(key);
                Field targetField = direction.transform(player.getField());

                if (!targetField.isBlocking())
                {
                    action = Optional.<Action>of(MoveAction.create(player, direction));
                    GameLayer.this.direction = direction;
                }
            }

            return action;
        }
    }

    private class DefaultTargetHandler implements TargetHandler
    {
        @Override
        public void handleTargetSelected(int targetY, int targetX)
        {
        }
    }
}
