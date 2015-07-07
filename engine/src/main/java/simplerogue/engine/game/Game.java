package simplerogue.engine.game;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import simplerogue.engine.level.Level;
import simplerogue.engine.object.Named;

import java.util.List;
import java.util.Map;

/**
 * A game state.
 *
 * @author Adam Wyłuda
 */
@Data
public class Game implements Named
{
    /**
     * A generator of current game save.
     */
    private String name;

    /**
     * Game properties shared across all levels and objects.
     */
    private GameConfiguration config;

    /**
     * Maps level names to their instances.
     */
    private Map<String, Level> levelMap = Maps.newHashMap();

    /**
     * A list of game messages.
     */
    private List<String> messages = Lists.newArrayList();

    public boolean isSaved()
    {
        return !Strings.isNullOrEmpty(getName());
    }
}
