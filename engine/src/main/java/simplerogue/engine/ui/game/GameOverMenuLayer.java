package simplerogue.engine.ui.game;

import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.Key;

/**
 * @author Adam Wyłuda
 */
public class GameOverMenuLayer extends MenuLayer
{
    public static final String NAME = "GAME_OVER_MENU";

    protected GameOverMenuLayer()
    {
        super(NAME);

        setTitle("Game Over");

        getOptions().add(MenuOption.create("OK", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                switchTo(MainMenuLayer.NAME);
                Managers.get(UIManager.class).getLayer(GameLayer.NAME).setVisible(false);
            }
        }));
    }

    public static GameOverMenuLayer create()
    {
        return new GameOverMenuLayer();
    }
}
