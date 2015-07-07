package simplerogue.world.ui;

import com.google.common.base.Optional;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.Item;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.game.GameMenuLayer;
import simplerogue.engine.ui.game.MenuLayer;
import simplerogue.engine.ui.game.MenuOption;
import simplerogue.engine.view.Key;
import simplerogue.world.level.creature.Player;
import simplerogue.world.level.item.Armor;
import simplerogue.world.level.item.Weapon;

/**
 * @author Adam Wyłuda
 */
public class EquipmentMenuLayer extends MenuLayer
{
    public static final String TITLE = "Equipment";
    public static final String NAME = "EQUIPMENT_MENU";

    public EquipmentMenuLayer()
    {
        super(NAME);

        setTitle(TITLE);
    }

    private void reloadOptions()
    {
        getOptions().clear();

        Player player = Managers.get(GameManager.class).getPlayer();

        for (Item item : player.getItems())
        {
            getOptions().add(new ItemMenuOption(item));
        }

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

    private class ItemMenuOption extends MenuOption
    {
        private static final String EQUIPPED_WEAPON = "@(RED|)";
        private static final String EQUIPPED_ARMOR = "@(CYAN|)";
        private static final String NOT_EQUIPPED = "";

        private final Item item;

        private ItemMenuOption(final Item item)
        {
            super("", MenuOption.NULL_HANDLER);

            this.item = item;

            setHandler(createOptionHandler());

            String prefix;
            prefix = isEquippedWeapon() ? EQUIPPED_WEAPON : NOT_EQUIPPED;
            prefix = isEquippedArmor() ? EQUIPPED_ARMOR : prefix;

            setText(prefix + item.getName());
        }

        private boolean isEquippedWeapon()
        {
            Player player = Managers.get(GameManager.class).getPlayer();

            return player.getEquippedWeapon().isPresent() && player.getEquippedWeapon().get() == item;
        }

        private boolean isEquippedArmor()
        {
            Player player = Managers.get(GameManager.class).getPlayer();

            return player.getEquippedArmor().isPresent() && player.getEquippedArmor().get() == item;
        }

        private MenuOption.OptionHandler<MenuOption> createOptionHandler()
        {
            return new MenuOption.OptionHandler<MenuOption>()
            {
                @Override
                public void perform(MenuOption option, Key key)
                {
                    Player player = Managers.get(GameManager.class).getPlayer();

                    if (isEquippedWeapon())
                    {
                        player.setEquippedWeapon(Optional.<Weapon>absent());
                    }
                    else if (isEquippedArmor())
                    {
                        player.setEquippedArmor(Optional.<Armor>absent());
                    }
                    else if (item.is(Weapon.class))
                    {
                        player.setEquippedWeapon(Optional.of(item.reify(Weapon.class)));
                    }
                    else if (item.is(Armor.class))
                    {
                        player.setEquippedArmor(Optional.of(item.reify(Armor.class)));
                    }
                    else
                    {
                        item.getHandler().useItem(item, player, player);
                    }

                    reloadOptions();
                }
            };
        }
    }
}
