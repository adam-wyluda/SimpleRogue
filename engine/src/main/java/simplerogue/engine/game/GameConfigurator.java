package simplerogue.engine.game;

import simplerogue.engine.object.Named;

import java.util.List;

/**
 * Provided by world module. Creates Game objects.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.game.Game
 * @see simplerogue.engine.game.GameProperty
 */
public interface GameConfigurator <T extends GameConfiguration> extends Named
{
    /**
     * Returns basic configuration. Called when new game is created or loaded.
     * In latter case, created configuration will be merged with saved properties by calling
     * {@link simplerogue.engine.game.GameConfiguration#load(simplerogue.engine.object.Model)}.
     *
     * @see simplerogue.engine.game.GameConfiguration
     */
    T createInitialConfiguration();

    /**
     * Configures a new game. Must create initial level with player object.
     */
    Game configureGame(T config);

    /**
     * Lists game properties.
     */
    List<GameProperty> getProperties();
}
