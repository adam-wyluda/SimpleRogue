package simplerogue.world.ui;

import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.AbstractLayout;
import simplerogue.engine.ui.Layer;
import simplerogue.engine.ui.Layout;
import simplerogue.engine.ui.SideLayout;
import simplerogue.engine.ui.game.GameLayer;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.CharArray;
import simplerogue.engine.view.Screen;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class PlayerStatusLayer extends Layer
{
    private static final String NAME = "PLAYER_STATUS_LAYER";

    public PlayerStatusLayer()
    {
        setName(NAME);
        setPriority(GAME_UI_PRIORITY);
        setLayout(SideLayout.builder()
                .proportionalToBottom(0.0)
                .proportionalToLeft(0.5)
                .xCentered()
                .fixedHeight(1)
                .delegateWidth(createLayout())
                .build());
    }

    @Override
    public void render(CharArea area)
    {
        area.draw(getCharArray(), 0, 0);
    }

    @Override
    public boolean isVisible()
    {
        return GameLayer.getInstance().isVisible();
    }

    private CharArray getCharArray()
    {
        Player player = Managers.get(GameManager.class).getPlayer();

        int hp = player.getHp();
        int maxHp = player.getMaxHp();

        return CharArray.fromFormattedString(
                String.format("@(GREEN|)HP: %s%d@(GREEN|) / %d", hpColor(hp, maxHp), hp, maxHp));
    }

    private String hpColor(int hp, int maxHp)
    {
        double ratio = hp / (double) maxHp;
        String result;

        if (ratio < 0.3)
        {
            result = "@(RED|)";
        }
        else if (ratio < 0.7)
        {
            result = "@(YELLOW|)";
        }
        else
        {
            result = "@(GREEN|)";
        }

        return result;
    }

    private Layout createLayout()
    {
        return new AbstractLayout()
        {
            @Override
            public int getWidth(Screen screen)
            {
                return getCharArray().length();
            }
        };
    }
}
