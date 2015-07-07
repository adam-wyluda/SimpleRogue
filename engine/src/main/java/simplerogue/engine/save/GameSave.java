package simplerogue.engine.save;

import lombok.Data;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.Named;

import java.util.Map;

/**
 * Properties from which {@link simplerogue.engine.game.Game} will be loaded.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.game.Game
 */
@Data
public class GameSave implements Named
{
    private String name;
    private Model properties;
    private Map<String, LevelSave> levelSaves;

    @Data
    public static class LevelSave
    {
        private int[][] map;
        private Model properties;
        private Map<Integer, Model> objectProperties;
    }
}
