package simplerogue.engine.ui.game;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.game.Game;
import simplerogue.engine.game.GameUtil;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.FramedLayer;
import simplerogue.engine.ui.FullLayout;
import simplerogue.engine.ui.Layout;
import simplerogue.engine.ui.SideLayout;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.CharArray;
import simplerogue.engine.view.Key;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NotificationsLayer extends FramedLayer
{
    private static final String NAME = "NOTIFICATIONS_LAYER";

    private Layout layout;

    public NotificationsLayer()
    {
        this(NAME);
    }

    public NotificationsLayer(String name)
    {
        setName(name);
        setVisible(true);

        setAsGameUI();
    }

    public static NotificationsLayer getInstance()
    {
        return Managers.get(UIManager.class).getLayer(NAME);
    }

    @Override
    public boolean isVisible()
    {
        return super.isVisible() && GameLayer.getInstance().isVisible();
    }

    public void setActive()
    {
        setPriority(MENU_PRIORITY);
        layout = FullLayout.create();
    }

    public void setAsGameUI()
    {
        setPriority(GAME_UI_PRIORITY);
        layout = SideLayout.builder()
                .fixedToTop(0)
                .fixedToRight(0)
                .proportionalHeight(0.2)
                .proportionalWidth(0.3)
                .build();
    }

    @Override
    protected void renderContent(CharArea area)
    {
        Game game = GameUtil.getGame();
        List<String> messages = game.getMessages();
        final int lastMessageIndex = messages.size() - 1;
        int messageIndex = lastMessageIndex;

        int freeSpace = area.getHeight();

        LinkedList<CharArray> messageArraySplit = Lists.newLinkedList();

        while (freeSpace > 0)
        {
            if (messageArraySplit.isEmpty())
            {
                if (messageIndex < 0)
                {
                    break;
                }

                String message = messages.get(messageIndex);
                CharArray array = CharArray.fromFormattedString(message);
                CharArray[] split = array.split(area.getWidth());
                Collections.addAll(messageArraySplit, split);

                messageIndex--;
            }

            CharArray messageArray = messageArraySplit.removeLast();
            int y = freeSpace - 1;
            area.draw(messageArray, y, 0);

            freeSpace--;
        }
    }

    @Override
    public void handleKeyPressed(Key key)
    {
        switch (key)
        {
            case DIRECTION_N:
                break;

            case DIRECTION_S:
                break;

            case SHOW_MENU:
                setAsGameUI();
                Managers.get(UIManager.class).setActiveLayer(GameLayer.getInstance());
                break;
        }
    }
}
