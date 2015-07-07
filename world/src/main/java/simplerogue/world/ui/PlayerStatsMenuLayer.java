package simplerogue.world.ui;

import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.game.GameMenuLayer;
import simplerogue.engine.ui.game.MenuLayer;
import simplerogue.engine.ui.game.MenuOption;
import simplerogue.engine.view.Key;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class PlayerStatsMenuLayer extends MenuLayer
{
    public static final String TITLE = "Player stats";
    public static final String NAME = "PLAYER_STATS_MENU";

    public PlayerStatsMenuLayer()
    {
        super(NAME);

        setTitle(TITLE);
    }

    private void reloadOptions()
    {
        getOptions().clear();

        Player player = Managers.get(GameManager.class).getPlayer();

        getOptions().add(MenuOption.create("Strength: " + player.getStrength()));
        getOptions().add(MenuOption.create("Perception: " + player.getPerception()));

        getOptions().add(MenuOption.create("Back", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                switchTo(GameMenuLayer.NAME);
            }
        }));

        setSelectedOption(getOptions().get(getOptions().size() - 1));
    }

    @Override
    public void setVisible(boolean visible)
    {
        reloadOptions();

        super.setVisible(visible);
    }
}
