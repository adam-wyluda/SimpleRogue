package simplerogue.engine.resource;

/**
 * Resource constants.
 *
 * @author Adam Wyłuda
 */
public class Resources
{
    private Resources()
    {
    }

    // Extensions
    public static final String JSON_EXT = ".json";
    public static final String JSON_COMPRESSED_EXT = ".gz";
    public static final String MAP_EXT = ".map";
    public static final String MAP_COMPRESSED_EXT = ".png";

    // Paths
    public static final String PROTOTYPES = "/prototypes/";
    public static final String SAVES = "/saves/";
    public static final String LEVELS = "levels/";

    // Prototypes
    public static final String ACTOR_PROTOTYPES = "actors.json";
    public static final String EFFECT_PROTOTYPES = "effects.json";
    public static final String FIELD_PROTOTYPES = "fields.json";
    public static final String ITEM_PROTOTYPES = "items.json";

    // Save file names
    public static final String SAVE_PROPERTIES = "save.json";
    public static final String LEVEL_MAP = "level.map";
    public static final String LEVEL_PROPERTIES = "level.json";
    public static final String LEVEL_OBJECTS = "objects.json";
}
