package simplerogue.engine.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import simplerogue.engine.ai.AI;
import simplerogue.engine.ai.EffectHandler;
import simplerogue.engine.ai.FieldHandler;
import simplerogue.engine.ai.ItemHandler;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelElement;
import simplerogue.engine.levelgenerator.LevelGenerator;
import simplerogue.engine.object.Prototype;

import java.util.List;
import java.util.Map;

/**
 * Describes all possible game elements.
 *
 * @author Adam Wyłuda
 */
@Data
public class World
{
    private String name;
    private List<Prototype> prototypes = Lists.newArrayList();
    private List<LevelGenerator> levelGenerators = Lists.newArrayList();
    private List<AI> ais = Lists.newArrayList();
    private List<EffectHandler> effectHandlers = Lists.newArrayList();
    private List<FieldHandler> fieldHandlers = Lists.newArrayList();
    private List<ItemHandler> itemHandlers = Lists.newArrayList();
    private List<GameConfigurator> configurators = Lists.newArrayList();
    private Map<String, Class<? extends LevelElement>> objectTypes = Maps.newHashMap();
    private Map<String, Class<? extends Level>> levelTypes = Maps.newHashMap();

    public void configureUI()
    {
    }

    public void configureGame()
    {
    }

    protected void addObjectTypes(String[] names, Class<? extends LevelElement> type)
    {
        for (String name : names)
        {
            objectTypes.put(name, type);
        }
    }
}
