package simplerogue.world;

import com.google.common.collect.Lists;
import lombok.Getter;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldManager;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class DefaultWorldManager extends AbstractManager implements WorldManager
{
    @Getter
    private List<World> worlds;

    public DefaultWorldManager()
    {
        worlds = Lists.<World> newArrayList(new DefaultWorld());
    }
}
