package simplerogue.engine.game;

import simplerogue.engine.level.Actor;
import simplerogue.engine.manager.Manager;
import simplerogue.engine.world.World;

import java.util.List;

/**
 * Manages game state.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.game.Game
 * @see simplerogue.engine.game.GameConfiguration
 * @see simplerogue.engine.game.GameConfigurator
 */
public interface GameManager extends Manager
{
    /**
     * Selected world.
     */
    World getWorld();

    /**
     * Sets current world.
     */
    void loadWorld(World world);

    /**
     * Returns list of available game configurators.
     */
    List<GameConfigurator> getConfigurators();

    /**
     * Returns configurator for current game.
     *
     * @see #getGame()
     */
    GameConfigurator getConfigurator();

    /**
     * Returns game configurator with given generator.
     */
    GameConfigurator getConfigurator(String name);

    /**
     * Returns current Game.
     */
    Game getGame();

    /**
     * Returns Actor instance of player.
     */
    <T extends Actor> T getPlayer();

    /**
     * Initiates game with given GameConfiguration.
     */
    Game newGame(GameConfiguration config);

    /**
     * Loads saved gave with given generator.
     */
    Game loadGame(String saveName);

    /**
     * Stores current game state.
     */
    void saveGame();

    /**
     * Deletes current game save (if it was ever saved).
     */
    void gameOver();

    /**
     * Returns true after starting or loading a game.
     */
    boolean isGameLoaded();
}
