package simplerogue.engine.ai;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class AITypes
{
    private static final Map<String, AI> AI_MAPPING;
    private static final Map<String, EffectHandler> EFFECT_HANDLER_MAPPING;
    private static final Map<String, FieldHandler> FIELD_HANDLER_MAPPING;
    private static final Map<String, ItemHandler> ITEM_HANDLER_MAPPING;

    static
    {
        AI_MAPPING = Maps.newHashMap();
        AI_MAPPING.put(PlayerAI.NAME, new PlayerAI());

        EFFECT_HANDLER_MAPPING = Maps.newHashMap();

        FIELD_HANDLER_MAPPING = Maps.newHashMap();
        FIELD_HANDLER_MAPPING.put(PortalHandler.NAME, new PortalHandler());

        ITEM_HANDLER_MAPPING = Maps.newHashMap();
        ITEM_HANDLER_MAPPING.put(WasteItemHandler.NAME, new WasteItemHandler());
    }

    public static Map<String, AI> getAiMapping()
    {
        return AI_MAPPING;
    }

    public static Map<String, EffectHandler> getEffectHandlerMapping()
    {
        return EFFECT_HANDLER_MAPPING;
    }

    public static Map<String, FieldHandler> getFieldHandlerMapping()
    {
        return FIELD_HANDLER_MAPPING;
    }

    public static Map<String, ItemHandler> getItemHandlerMapping()
    {
        return ITEM_HANDLER_MAPPING;
    }
}
