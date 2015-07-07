package simplerogue.engine.levelgenerator;

import com.google.common.collect.Maps;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultLevelGeneratorManager extends AbstractManager implements LevelGeneratorManager, WorldListener
{
    private Map<String, LevelGenerator> generators = Maps.newHashMap();

    @Override
    public void worldChanged(World world)
    {
        this.generators = Maps.newHashMap();

        for (LevelGenerator generator : world.getLevelGenerators())
        {
            this.generators.put(generator.getName(), generator);
        }
    }

    @Override
    public <T extends LevelRequest> T createRequest(String name)
    {
        LevelGenerator levelGenerator = getGenerator(name);
        T requets = (T) levelGenerator.createRequest();

        return requets;
    }

    @Override
    public LevelGenerator getGenerator(String name)
    {
        return generators.get(name);
    }

    @Override
    public LevelGenerator getGenerator(LevelRequest request)
    {
        LevelGenerator result;

        result = getGenerator(request.getGenerator());

        return result;
    }
}
