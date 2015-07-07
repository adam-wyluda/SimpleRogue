package simplerogue.engine.levelgenerator.util;

import simplerogue.engine.util.RandomUtil;

/**
 * @author Adam Wyłuda
 */
public class BSPUtil
{
    public static BSP.Node mostLeftNode(BSP bsp)
    {
        BSP.Node node = bsp.getRoot();

        while (!node.isLeaf())
        {
            node = node.getLeft().get();
        }

        return node;
    }

    public static BSP.Node mostRightNode(BSP bsp)
    {
        BSP.Node node = bsp.getRoot();

        while (!node.isLeaf())
        {
            node = node.getRight().get();
        }

        return node;
    }

    public static BSP.Node randomNode(BSP bsp)
    {
        BSP.Node node = bsp.getRoot();

        while (!node.isLeaf())
        {
            if (RandomUtil.randomBoolean())
            {
                node = node.getLeft().get();
            }
            else
            {
                node = node.getRight().get();
            }
        }

        return node;
    }
}
