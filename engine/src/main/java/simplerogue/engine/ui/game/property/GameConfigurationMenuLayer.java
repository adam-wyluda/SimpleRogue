package simplerogue.engine.ui.game.property;

import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.game.GameProperty;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.ui.game.GameLayer;
import simplerogue.engine.ui.game.MenuLayer;
import simplerogue.engine.ui.game.MenuOption;
import simplerogue.engine.view.Key;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class GameConfigurationMenuLayer extends MenuLayer
{
    public static final String NAME = "GAME_CONFIGURATION_MENU";

    private static final String TITLE = "@(YELLOW|)Game Configuration";

    public static GameConfigurationMenuLayer getInstance()
    {
        return Managers.get(UIManager.class).getLayer(NAME);
    }

    public GameConfigurationMenuLayer()
    {
        super(NAME);
        setTitle(TITLE);
    }

    public void reloadOptions(GameConfigurator configurator, final GameConfiguration config)
    {
        getOptions().clear();

        List<GameProperty> properties = configurator.getProperties();
        for (GameProperty property : properties)
        {
            PropertyMenuOption menuOption = property.createMenuOption(config);
            getOptions().add(menuOption);
        }

        getOptions().add(MenuOption.create("Start game", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                GameManager gameManager = Managers.get(GameManager.class);
                gameManager.newGame(config);

                switchTo(GameLayer.NAME);
            }
        }));
    }
}
