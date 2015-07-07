package simplerogue.engine.level;

import com.google.common.base.Preconditions;
import simplerogue.engine.object.Model;

/**
 * @author Adam Wyłuda
 */
public class LevelUtil
{
    public static final String POS_Y = "pos_y";
    public static final String POS_X = "pos_x";

    public static String getLevelType(Model levelModel)
    {
        return levelModel.has(Level.LEVEL_TYPE) ? levelModel.getString(Level.LEVEL_TYPE) : Level.NAME;
    }

    public static void setLevelType(Model levelModel, String type)
    {
        levelModel.put(Level.LEVEL_TYPE, type);
    }

    public static int getHeight(int[][] map)
    {
        return map.length;
    }

    public static int getWidth(int[][] map)
    {
        return map.length != 0 ? map[0].length : 0;
    }

    public static int getPosY(Model model)
    {
        return model.has(POS_Y) ? model.getInt(POS_Y) : -1;
    }

    public static int getPosX(Model model)
    {
        return model.has(POS_X) ? model.getInt(POS_X) : -1;
    }

    public static double euclideanDistance(LevelElement one, LevelElement two)
    {
        Preconditions.checkArgument(one.getLevel() == two.getLevel(), "Two elements must be on the same level");

        int oneY = one.getPosY();
        int oneX = one.getPosX();

        int twoY = two.getPosY();
        int twoX = two.getPosX();

        return euclideanDistance(oneY, oneX, twoY, twoX);
    }

    public static double euclideanDistance(int oneY, int oneX, int twoY, int twoX)
    {
        int dy = oneY - twoY;
        int dx = oneX - twoX;

        double distance = Math.sqrt(dy * dy + dx * dx);

        return distance;
    }

    public static Direction directionFrom(LevelElement from, LevelElement to)
    {
        Preconditions.checkArgument(from.getLevel() == to.getLevel(), "Two elements must be on the same level");

        int fromY = from.getPosY();
        int fromX = from.getPosX();
        int toY = to.getPosY();
        int toX = to.getPosX();

        return directionFrom(fromY, fromX, toY, toX);
    }

    public static Direction directionFrom(int fromY, int fromX, int toY, int toX)
    {
        Direction result = Direction.C;
        double minDistance = Double.MAX_VALUE;

        for (Direction direction : Direction.values())
        {
            int transformY = direction.transformY(fromY);
            int transformX = direction.transformX(fromX);

            double distance = euclideanDistance(transformY, transformX, toY, toX);

            if (distance < minDistance)
            {
                result = direction;
                minDistance = distance;
            }
        }

        return result;
    }

    public static double enhanceChanceWithBlockingCount(Field field, double baseChance)
    {
        int count = surroundingBlockingCount(field);

        return 1.0 - Math.pow(1.0 - baseChance, count + 1);
    }

    public static int surroundingBlockingCount(Field field)
    {
        int count = 0;

        for (Direction direction : Direction.NON_IDEMPOTENT)
        {
            Field transformedField = direction.transform(field);

            if (transformedField.isBlocking())
            {
                count++;
            }
        }

        return count;
    }
}
