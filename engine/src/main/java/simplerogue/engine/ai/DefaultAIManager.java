package simplerogue.engine.ai;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import simplerogue.engine.level.Actor;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultAIManager extends AbstractManager implements AIManager, WorldListener
{
    // Action handlers
    private Map<String, AI> ais;
    private Map<String, EffectHandler> effectHandlers;
    private Map<String, FieldHandler> fieldHandlers;
    private Map<String, ItemHandler> itemHandlers;

    @Override
    public <T extends AI> T getAI(String name)
    {
        return (T) ais.get(name);
    }

    @Override
    public <T extends EffectHandler> T getEffectHandler(String name)
    {
        return (T) effectHandlers.get(name);
    }

    @Override
    public <T extends FieldHandler> T getFieldHandler(String name)
    {
        return (T) fieldHandlers.get(name);
    }

    @Override
    public <T extends ItemHandler> T getItemHandler(String name)
    {
        return (T) itemHandlers.get(name);
    }

    @Override
    public void worldChanged(World world)
    {
        ais = Maps.newHashMap();
        ais.putAll(AITypes.getAiMapping());
        for (AI<?> ai : world.getAis())
        {
            ais.put(ai.getName(), ai);
        }

        effectHandlers = Maps.newHashMap();
        effectHandlers.putAll(AITypes.getEffectHandlerMapping());
        for (EffectHandler<?> handler : world.getEffectHandlers())
        {
            effectHandlers.put(handler.getName(), handler);
        }

        fieldHandlers = Maps.newHashMap();
        fieldHandlers.putAll(AITypes.getFieldHandlerMapping());
        for (FieldHandler<?> handler : world.getFieldHandlers())
        {
            fieldHandlers.put(handler.getName(), handler);
        }

        itemHandlers = Maps.newHashMap();
        itemHandlers.putAll(AITypes.getItemHandlerMapping());
        for (ItemHandler<?> handler : world.getItemHandlers())
        {
            itemHandlers.put(handler.getName(), handler);
        }
    }
}
