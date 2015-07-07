package simplerogue.engine.ui;

import com.google.common.base.Optional;
import lombok.Data;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Named;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.Key;

/**
 * Element of game UI.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.ui.UIManager
 */
@Data
public abstract class Layer implements Named
{
    public static final int BACK_PRIORITY = 0;
    public static final int GAME_UI_PRIORITY = 50;
    public static final int MENU_PRIORITY = 100;
    public static final int NOTIFICATION_PRIORITY = 200;

    private Layout layout = FullLayout.create();

    /**
     * Defines order in which layers are rendered. Higher = closer to front.
     */
    private int priority = MENU_PRIORITY;

    private String name;
    private boolean visible;

    public boolean isActive()
    {
        Optional<Layer> activeLayer = Managers.get(UIManager.class).getActiveLayer();
        return activeLayer.isPresent() && activeLayer.get() == this;
    }

    /**
     * @param area Buffer on which this layer will be drawn. Its size is equal to size defined by {@link #getLayout()}.
     */
    public abstract void render(CharArea area);

    public void handleKeyPressed(Key key)
    {
    }

    /**
     * Helper method to switch between views.
     */
    protected void switchTo(String nextLayerName)
    {
        setVisible(false);

        UIManager uiManager = Managers.get(UIManager.class);

        Layer nextLayer = uiManager.getLayer(nextLayerName);
        uiManager.setActiveLayer(nextLayer);
    }
}
