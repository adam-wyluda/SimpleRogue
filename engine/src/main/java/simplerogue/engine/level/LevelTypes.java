package simplerogue.engine.level;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class LevelTypes
{
    private static final Map<String, Class<? extends Level>> MAPPING;

    private LevelTypes()
    {
    }

    static
    {
        MAPPING = Maps.newHashMap();

        MAPPING.put(Level.NAME, Level.class);
    }

    public static Map<String, Class<? extends Level>> getMapping()
    {
        return MAPPING;
    }
}
