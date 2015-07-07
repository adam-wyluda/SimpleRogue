package simplerogue.engine.test.world;

import com.google.common.collect.Lists;
import lombok.Getter;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.test.TestConsts;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldManager;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class TestWorldManager extends AbstractManager implements WorldManager
{
    @Getter
    private List<World> worlds = Lists.newArrayList();

    public TestWorldManager()
    {
        World world = new World();

        world.setName(TestConsts.WORLD_NAME);
        world.setLevelGenerators(TestConsts.LEVEL_GENERATORS);
        world.setAis(TestConsts.AIS);
        world.setEffectHandlers(TestConsts.EFFECT_HANDLERS);
        world.setFieldHandlers(TestConsts.FIELD_HANDLERS);
        world.setItemHandlers(TestConsts.ITEM_HANDLERS);
        world.setConfigurators(TestConsts.GAME_CONFIGURATORS);
        world.setPrototypes(TestConsts.PROTOTYPES);
        world.setObjectTypes(TestConsts.OBJECT_TYPES);
        world.setLevelTypes(TestConsts.LEVEL_TYPES);

        worlds.add(world);
    }
}
