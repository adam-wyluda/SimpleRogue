package simplerogue.engine.save;

import simplerogue.engine.manager.Manager;
import simplerogue.engine.object.Prototype;

import java.util.List;

/**
 * Transforms resources to {@link simplerogue.engine.save.GameSave}.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.game.Game
 */
public interface SaveManager extends Manager
{
    /**
     * Generates unique generator for given configurator.
     */
    String generateSaveName(String configurator);

    /**
     * Returns a list of game save names.
     */
    List<String> listSaves();

    /**
     * Loads game with given generator.
     */
    GameSave loadGame(String name);

    /**
     * Stores game state. {@link simplerogue.engine.game.Game#getName()} can be null, in that case
     * this method will generate a new save generator and set it with
     * {@link simplerogue.engine.game.Game#setName(String)}.
     */
    void saveGame(GameSave game);

    void deleteSave(String name);

    /**
     * Loads game prototypes from given world.
     */
    List<Prototype> loadPrototypes(String worldName);
}
