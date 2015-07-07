package simplerogue.engine.level;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

/**
 * @author Adam Wyłuda
 */
public class LevelElementTypes
{
    // TODO Perhaps this could be replaced by prototypes
    private static final Map<String, Class<? extends LevelElement>> MAPPING;
    private static final Map<String, Integer> FIELD_MAPPING;
    private static final Set<String> CACHED;

    private LevelElementTypes()
    {
    }

    static
    {
        MAPPING = Maps.newHashMap();

        MAPPING.put(ActiveField.NAME, ActiveField.class);
        MAPPING.put(Actor.NAME, Actor.class);
        MAPPING.put(Effect.NAME, Effect.class);
        MAPPING.put(Field.NAME, Field.class);
        MAPPING.put(FieldElement.NAME, FieldElement.class);
        MAPPING.put(Item.NAME, Item.class);
        MAPPING.put(LevelElement.NAME, LevelElement.class);
        MAPPING.put(NullField.NAME, NullField.class);
        MAPPING.put(Portal.NAME, Portal.class);

        FIELD_MAPPING = Maps.newHashMap();
        FIELD_MAPPING.put(NullField.NAME, NullField.ID);

        CACHED = Sets.newHashSet();
        CACHED.add(NullField.NAME);
    }

    public static Map<String, Class<? extends LevelElement>> getMapping()
    {
        return MAPPING;
    }

    public static Map<String, Integer> getFieldMapping()
    {
        return FIELD_MAPPING;
    }

    public static Set<String> getCached()
    {
        return CACHED;
    }
}
