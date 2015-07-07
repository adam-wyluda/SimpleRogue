package simplerogue.world.levelgenerator;

import simplerogue.engine.level.FieldElement;
import simplerogue.engine.levelgenerator.util.BSP;
import simplerogue.engine.levelgenerator.util.BSPUtil;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.util.RandomUtil;

/**
 * @author Adam Wyłuda
 */
public class GeneratorHelper
{
    /**
     * Generates random element from given prototype names.
     *
     * @param names       List of prototype names.
     * @param level       How many times probability test is repeated.
     * @param probability Enhances chance of getting higher level elements.
     */
    public static <T extends FieldElement> T generateRandomElementWithDecreasingChance(String[] names, int level, double probability)
    {
        int index = 0;
        String name = null;

        while (index < names.length)
        {
            name = names[index++];

            int repeats = 0;
            while (level > 0 && repeats < level && RandomUtil.randomBoolean(probability))
            {
                repeats++;
            }

            if (repeats < level)
            {
                break;
            }
        }

        T object = Managers.get(ObjectManager.class).createObject(name);
        return object;
    }

    public static void addObjectsWithDecreasingChance(DefaultLevelRequest request, String[] names, int count, double probability)
    {
        int levelNumber = request.getLevelNumber();

        while (count-- > 0)
        {
            FieldElement element = generateRandomElementWithDecreasingChance(names, levelNumber, probability);

            BSP.Node node = BSPUtil.randomNode(request.getBsp());
            node.getRoom().putElement(element);
        }
    }

    public static void addObjectsWithEvenDistribution(DefaultLevelRequest request, String[] names, int count)
    {
        while (count-- > 0)
        {
            String name = RandomUtil.randomElement(names);
            addObjectToRandomRoom(request, name);
        }
    }

    public static void addObjectToRandomRoom(DefaultLevelRequest request, String name)
    {
        FieldElement object = Managers.get(ObjectManager.class).createObject(name);

        BSP.Node node = BSPUtil.randomNode(request.getBsp());
        node.getRoom().putElement(object);
    }
}
