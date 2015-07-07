package simplerogue.world.levelgenerator;

import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.levelgenerator.util.BSP;
import simplerogue.engine.levelgenerator.util.BSPUtil;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.Prototype;
import simplerogue.world.WorldConsts;
import simplerogue.world.level.item.Armor;
import simplerogue.world.level.item.Bow;
import simplerogue.world.level.item.Potion;
import simplerogue.world.level.item.Weapon;

/**
 * @author Adam Wyłuda
 */
public class ItemGenerator extends AbstractLevelProcessor<DefaultLevelRequest>
{
    private static final String NAME = "item_generator";

    @Override
    public void process(DefaultLevelRequest request)
    {
        ObjectManager objectManager = Managers.get(ObjectManager.class);

        if (request.isInitial())
        {
            createInitialItems(request);
        }

        int count = Math.max(1, request.getLevelNumber() - 1);
        GeneratorHelper.addObjectsWithDecreasingChance(request, WorldConsts.HEALTH_POTIONS, 3 + count, 0.3);
        GeneratorHelper.addObjectsWithDecreasingChance(request, WorldConsts.SWORDS, count, 0.3);
        GeneratorHelper.addObjectsWithDecreasingChance(request, WorldConsts.ARMORS, count, 0.3);

        Potion deathPotion = objectManager.createObject(WorldConsts.DEATH_POTION_NAME);
        BSP.Node node = BSPUtil.randomNode(request.getBsp());
        node.getRoom().putElement(deathPotion);
    }

    @Override
    public String getName()
    {
        return NAME;
    }

    private void createInitialItems(DefaultLevelRequest request)
    {
        ObjectManager objectManager = Managers.get(ObjectManager.class);

        Prototype swordPrototype = objectManager.getPrototype(WorldConsts.SWORDS[0]);
        Prototype armorPrototype = objectManager.getPrototype(WorldConsts.ARMORS[0]);
        Prototype bowPrototype = objectManager.getPrototype(WorldConsts.BOWS[0]);

        Weapon sword = swordPrototype.create();
        Armor armor = armorPrototype.create();
        Bow bow = bowPrototype.create();

        BSP.Node mostLeftNode = BSPUtil.mostLeftNode(request.getBsp());
        mostLeftNode.getRoom().putElement(sword);
        mostLeftNode.getRoom().putElement(armor);
        mostLeftNode.getRoom().putElement(bow);
    }

}
