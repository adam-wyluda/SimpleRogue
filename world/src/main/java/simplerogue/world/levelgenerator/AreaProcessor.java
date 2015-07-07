package simplerogue.world.levelgenerator;

import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Level;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.levelgenerator.util.BSP;
import simplerogue.engine.levelgenerator.util.BSPUtil;
import simplerogue.engine.levelgenerator.util.Room;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.Prototype;
import simplerogue.world.WorldConsts;

/**
 * @author Adam Wyłuda
 */
public class AreaProcessor extends AbstractLevelProcessor<DefaultLevelRequest>
{
    @Override
    public void process(DefaultLevelRequest request)
    {
        ObjectManager objectManager = Managers.get(ObjectManager.class);
        final Prototype roomWallPrototype = objectManager.getPrototype(WorldConsts.ROOM_WALL_NAME);
        final Prototype entryWallPrototype = objectManager.getPrototype(WorldConsts.ENTRY_WALL_NAME);
        Prototype floorPrototype = objectManager.getPrototype(WorldConsts.FLOOR_NAME);
        Prototype corridorWallPrototype = objectManager.getPrototype(WorldConsts.CORRIDOR_WALL_NAME);

        Level level = request.getLevel();

        BSP.Config config = BSP.Config.create();
        config.setDepth(5);
        config.setSkipChance(0.6);
        config.setSeparatorVariation(30);

        final BSP bsp = BSP.create(level, config);
        bsp.setCorridorPrototype(BSP.CorridorPrototype.create(floorPrototype, corridorWallPrototype));

        final BSP.RectangleRoomProvider defaultRoomProvider = BSP.RectangleRoomProvider.create(floorPrototype, roomWallPrototype);
        final BSP.RectangleRoomProvider endRoomProvider = BSP.RectangleRoomProvider.create(floorPrototype, entryWallPrototype);
        bsp.setRoomProvider(new BSP.RoomProvider()
        {
            @Override
            public Room provide(Level level, BSP.Node node)
            {
                if (BSPUtil.mostRightNode(bsp) == node || BSPUtil.mostLeftNode(bsp) == node)
                {
                    return endRoomProvider.provide(level, node);
                }
                else
                {
                    return defaultRoomProvider.provide(level, node);
                }
            }
        });

        bsp.makeLevel(level);
        request.setBsp(bsp);

        if (request.isInitial())
        {
            Actor player = objectManager.createObject(WorldConsts.PLAYER_NAME);
            request.setPlayer(player);

            BSP.Node mostLeftNode = BSPUtil.mostLeftNode(bsp);
            mostLeftNode.getRoom().putElement(player);
        }
    }

    @Override
    public String getName()
    {
        return "default_level_processor";
    }
}
