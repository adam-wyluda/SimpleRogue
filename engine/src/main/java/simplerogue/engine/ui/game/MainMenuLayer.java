package simplerogue.engine.ui.game;

import com.google.common.collect.Lists;
import simplerogue.engine.application.ApplicationManager;
import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.game.property.GameConfigurationMenuLayer;
import simplerogue.engine.view.Char;
import simplerogue.engine.view.Color;
import simplerogue.engine.view.Key;

/**
 * @author Adam Wyłuda
 */
public class MainMenuLayer extends MenuLayer
{
    public static final String NAME = "MAIN_MENU";

    private static final String TITLE = "@(CYAN|)Main menu";
    private static final Char FRAME_CHAR = Char.create('O', Color.BLUE);

    private MenuOption newGameOption;
    private MenuOption loadGameOption;
    private MenuOption exitOption;

    public MainMenuLayer()
    {
        super(NAME);

        setTitle(TITLE);
        setFrameChar(FRAME_CHAR);

        newGameOption = MenuOption.create("New game", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                newGame();
            }
        });

        loadGameOption = MenuOption.create("Load game", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                loadGame();
            }
        });

        exitOption = MenuOption.create("Exit", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                exitGame();
            }
        });

        setOptions(Lists.newArrayList(newGameOption, loadGameOption, exitOption));
    }

    private void newGame()
    {
        GameManager gameManager = Managers.get(GameManager.class);
        GameConfigurator configurator = gameManager.getConfigurators().get(0);
        GameConfiguration config = configurator.createInitialConfiguration();

        GameConfigurationMenuLayer.getInstance().reloadOptions(configurator, config);
        switchTo(GameConfigurationMenuLayer.NAME);
    }

    private void loadGame()
    {
        switchTo(LoadGameMenuLayer.NAME);
    }

    private void exitGame()
    {
        ApplicationManager applicationManager = Managers.get(ApplicationManager.class);
        applicationManager.exit();
    }
}
