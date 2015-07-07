package simplerogue.engine.ui;

import com.google.common.base.Optional;
import simplerogue.engine.manager.Manager;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.Key;
import simplerogue.engine.view.Screen;

/**
 * Stores a registry of {@link simplerogue.engine.ui.Layer}, renders full view.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.ui.Layer
 */
public interface UIManager extends Manager
{
    void cleanup();

    /**
     * Registers layer under its unique generator. Registered layer is hidden by default.
     */
    void registerLayer(Layer layer);

    void removeLayer(Layer layer);

    boolean isRegistered(String name);

    /**
     * Returns layer registered with given generator.
     */
    <T extends Layer> T getLayer(String name);

    /**
     * Currently active layer that receives UI events from user.
     *
     * @return {@link com.google.common.base.Optional#absent()} if there is no active layer set.
     */
    Optional<Layer> getActiveLayer();

    /**
     * Makes provided layer active.
     *
     * @see #getActiveLayer()
     */
    void setActiveLayer(Layer layer);

    /**
     * Makes layer with provided generator active.
     *
     * @see #getActiveLayer()
     */
    void setActiveLayer(String name);

    /**
     * Renders all layers.
     */
    CharArea renderLayers(Screen screen);

    /**
     * Passes action to active layer.
     *
     * @see #getActiveLayer()
     */
    void handleKeyPressed(Key key);
}
