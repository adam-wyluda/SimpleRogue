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

        final Player player = Managers.get(GameManager.class).getPlayer();

        final boolean canIncrease = player.getSkillPoints() > 0;
        String postfix = canIncrease ? " +" : "";

        getOptions().add(MenuOption.create("Strength: " + player.getStrength() + postfix,
                new MenuOption.OptionHandler<MenuOption>()
                {
                    @Override
                    public void perform(MenuOption option, Key key)
                    {
                        if (canIncrease)
                        {
                            player.increaseStrength();
                            reloadOptions();
                        }
                    }
                }));

        getOptions().add(MenuOption.create("Perception: " + player.getPerception() + postfix,
                new MenuOption.OptionHandler<MenuOption>()
                {
                    @Override
                    public void perform(MenuOption option, Key key)
                    {
                        if (canIncrease)
                        {
                            player.increasePerception();
                            reloadOptions();
                        }
                    }
                }));

        getOptions().add(MenuOption.create("Stamina: " + player.getStamina() + postfix,
                new MenuOption.OptionHandler<MenuOption>()
                {
                    @Override
                    public void perform(MenuOption option, Key key)
                    {
                        if (canIncrease)
                        {
                            player.increaseStamina();
                            reloadOptions();
                        }
                    }
                }));

        getOptions().add(MenuOption.create("Skill points: " + player.getSkillPoints()));

        getOptions().add(MenuOption.create("Back", new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                switchTo(GameMenuLayer.NAME);
            }
        }));
    }

    @Override
    public void setVisible(boolean visible)
    {
        reloadOptions();

        super.setVisible(visible);
    }
}
