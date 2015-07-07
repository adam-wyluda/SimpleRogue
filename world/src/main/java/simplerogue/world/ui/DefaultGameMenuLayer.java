package simplerogue.world.ui;

import com.google.common.collect.Lists;
import simplerogue.engine.ui.game.GameMenuLayer;
import simplerogue.engine.ui.game.MenuOption;
import simplerogue.engine.view.Key;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class DefaultGameMenuLayer extends GameMenuLayer
{
    @Override
    protected List<MenuOption> getCustomOptions()
    {
        List<MenuOption> customOptions = Lists.newArrayList();

        customOptions.add(MenuOption.create(PlayerStatsMenuLayer.TITLE,
                new MenuOption.OptionHandler<MenuOption>()
                {
                    @Override
                    public void perform(MenuOption option, Key key)
                    {
                        switchTo(PlayerStatsMenuLayer.NAME);
                    }
                }));

        customOptions.add(MenuOption.create(EquipmentMenuLayer.TITLE,
                new MenuOption.OptionHandler<MenuOption>()
                {
                    @Override
                    public void perform(MenuOption option, Key key)
                    {
                        switchTo(EquipmentMenuLayer.NAME);
                    }
                }));

        return customOptions;
    }
}
