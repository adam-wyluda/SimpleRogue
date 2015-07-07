package simplerogue.engine.ui.game;

import com.google.common.collect.Lists;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.view.Key;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class LoadGameMenuLayer extends MenuLayer
{
    public static final String NAME = "LOAD_GAME_MENU";

    private static final String TITLE = "@(YELLOW|)Load game";

    private MenuOption backOption;

    public LoadGameMenuLayer()
    {
        super(NAME);

        setTitle(TITLE);

        refreshOptions();
    }

    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);

        if (visible)
        {
            refreshOptions();
        }
    }

    private void refreshOptions()
    {
        setOptions(Lists.<MenuOption>newArrayList());

        List<MenuOption> saveOptions = getSaveOptions();
        getOptions().addAll(saveOptions);

        backOption = MenuOption.create("Back", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                back();
            }
        });

        getOptions().add(backOption);
        setSelectedOption(backOption);
    }

    private void back()
    {
        switchTo(MainMenuLayer.NAME);
    }

    private List<MenuOption> getSaveOptions()
    {
        List<MenuOption> result = Lists.newArrayList();

        SaveManager saveManager = Managers.get(SaveManager.class);

        for (String save : saveManager.listSaves())
        {
            MenuOption option = createOptionForSave(save);
            result.add(option);
        }

        return result;
    }

    private MenuOption createOptionForSave(final String saveName)
    {
        MenuOption option = MenuOption.create("- " + saveName, new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                GameManager gameManager = Managers.get(GameManager.class);
                gameManager.loadGame(saveName);

                switchTo(GameLayer.NAME);
            }
        });

        return option;
    }
}
