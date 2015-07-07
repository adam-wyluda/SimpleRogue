package simplerogue.engine.ui.game;

import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.Char;
import simplerogue.engine.view.Color;
import simplerogue.engine.view.Key;

import java.util.Collections;
import java.util.List;

/**
 * Show options like saving game. Used in {@link GameLayer}. Can be extended to offer custom menu options
 * by overriding {@link #getCustomOptions()}.
 *
 * @author Adam Wyłuda
 */
public class GameMenuLayer extends MenuLayer
{
    public static final String NAME = "GAME_MENU";

    protected GameMenuLayer()
    {
        super(NAME);

        setFrameChar(Char.create('%', Color.CYAN));

        getOptions().add(MenuOption.create("Resume", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                GameMenuLayer.this.setVisible(false);
                Managers.get(UIManager.class).setActiveLayer(GameLayer.NAME);
            }
        }));

        getOptions().addAll(getCustomOptions());

        getOptions().add(MenuOption.create("Save game", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                Managers.get(GameManager.class).saveGame();
            }
        }));

        getOptions().add(MenuOption.create("Back to main menu", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                PromptMenuLayer.showYesNoCancelPrompt("@(YELLOW|)Would you like to save game?",
                        new PromptMenuLayer.Callback()
                        {
                            @Override
                            public void handle(PromptMenuLayer.Answer answer)
                            {
                                if (answer == PromptMenuLayer.Answer.YES)
                                {
                                    Managers.get(GameManager.class).saveGame();
                                }

                                if (answer == PromptMenuLayer.Answer.YES || answer == PromptMenuLayer.Answer.NO)
                                {
                                    GameLayer.getInstance().setVisible(false);
                                    switchTo(MainMenuLayer.NAME);
                                }
                            }
                        });
            }
        }));
    }

    protected List<MenuOption> getCustomOptions()
    {
        return Collections.emptyList();
    }

    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);

        setSelectedOption(getOptions().get(0));
    }
}
