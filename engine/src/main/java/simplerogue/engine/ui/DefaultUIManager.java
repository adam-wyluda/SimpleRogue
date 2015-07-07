package simplerogue.engine.ui;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.game.GameLayer;
import simplerogue.engine.ui.game.GameOverMenuLayer;
import simplerogue.engine.ui.game.LoadGameMenuLayer;
import simplerogue.engine.ui.game.MainMenuLayer;
import simplerogue.engine.ui.game.NotificationsLayer;
import simplerogue.engine.ui.game.property.GameConfigurationMenuLayer;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.Key;
import simplerogue.engine.view.Screen;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultUIManager extends AbstractManager implements UIManager, WorldListener
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultUIManager.class);

    private Map<String, Layer> layers = Maps.newHashMap();
    private Optional<Layer> activeLayer;

    @Override
    public void cleanup()
    {
        layers.clear();
    }

    @Override
    public void registerLayer(Layer layer)
    {
        String name = layer.getName();

        LOG.debug("Registering layer {}", name);
        layers.put(name, layer);
    }

    @Override
    public void removeLayer(Layer layer)
    {
        String name = layer.getName();

        LOG.debug("Removing layer {}", name);
        if (layer.isActive())
        {
            activeLayer = Optional.absent();
        }
        layers.remove(name);
    }

    @Override
    public boolean isRegistered(String name)
    {
        return layers.containsKey(name);
    }

    @Override
    public <T extends Layer> T getLayer(String name)
    {
        return (T) layers.get(name);
    }

    @Override
    public Optional<Layer> getActiveLayer()
    {
        return activeLayer;
    }

    @Override
    public void setActiveLayer(Layer layer)
    {
        layer.setVisible(true);
        this.activeLayer = Optional.of(layer);
    }

    @Override
    public void setActiveLayer(String name)
    {
        Layer layer = getLayer(name);
        setActiveLayer(layer);
    }

    @Override
    public CharArea renderLayers(Screen screen)
    {
        LOG.debug("Rendering layers to a screen with width {} and height {}", screen.getWidth(), screen.getHeight());

        CharArea result = CharArea.create(screen.getHeight(), screen.getWidth());

        for (Layer layer : getVisibleLayers())
        {
            Layout layout = layer.getLayout();
            int posY = layout.getY(screen);
            int posX = layout.getX(screen);
            int height = layout.getHeight(screen);
            int width = layout.getWidth(screen);

            CharArea area = CharArea.create(height, width);
            layer.render(area);

            result.draw(area, posY, posX);
        }

        return result;
    }

    @Override
    public void handleKeyPressed(Key key)
    {
        LOG.debug("Handling key {}", key);

        Optional<Layer> layerOptional = getActiveLayer();

        if (layerOptional.isPresent())
        {
            Layer layer = layerOptional.get();

            layer.handleKeyPressed(key);
        }
    }

    private List<Layer> getVisibleLayers()
    {
        List<Layer> result = Lists.newArrayList();

        for (Layer layer : layers.values())
        {
            if (layer.isVisible())
            {
                result.add(layer);
            }
        }

        sortLayers(result);

        return result;
    }

    private void sortLayers(List<Layer> layerList)
    {
        Collections.sort(layerList, new Comparator<Layer>()
        {
            @Override
            public int compare(Layer o1, Layer o2)
            {
                return Integer.compare(o1.getPriority(), o2.getPriority());
            }
        });
    }

    @Override
    public void worldChanged(World world)
    {
        configureDefaultUI();
        world.configureUI();
    }

    private void configureDefaultUI()
    {
        // TODO World select layer

        UIManager uiManager = Managers.get(UIManager.class);

        uiManager.registerLayer(new MainMenuLayer());
        uiManager.registerLayer(new LoadGameMenuLayer());
        uiManager.registerLayer(new GameConfigurationMenuLayer());
        uiManager.registerLayer(GameLayer.create());
        uiManager.registerLayer(GameOverMenuLayer.create());
        uiManager.registerLayer(new NotificationsLayer());

        uiManager.setActiveLayer(MainMenuLayer.NAME);
    }
}
