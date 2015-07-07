package simplerogue.engine.level;

import simplerogue.engine.util.RandomUtil;
import simplerogue.engine.view.Key;

/**
 * @author Adam Wyłuda
 */
public enum Direction
{
    // TODO Perhaps it shouldn't be an enum to allow custom transform functions?
    N(-1, 0),
    NE(-1, 1),
    E(0, 1),
    SE(1, 1),
    S(1, 0),
    SW(1, -1),
    W(0, -1),
    NW(-1, -1),
    C(0, 0);

    // TODO Make NON_IDEMPOTENT a Set<Direction> and reimplement isDirection as contains on the set
    public static Direction[] NON_IDEMPOTENT =
            {
                    N, NE, E, SE,
                    S, SW, W, NW
            };

    private final int transformY;
    private final int transformX;

    private Direction(int transformY, int transformX)
    {
        this.transformY = transformY;
        this.transformX = transformX;
    }

    public int transformY(int y)
    {
        return y + transformY;
    }

    public int transformX(int x)
    {
        return x + transformX;
    }

    public Field transform(Field field)
    {
        int newY = transformY(field.getPosY());
        int newX = transformX(field.getPosX());

        Level level = field.getLevel();
        FieldArea area = level.getArea();
        Field newField = area.getFieldAt(newY, newX);

        return newField;
    }

    public Point transform(Point point)
    {
        int newY = transformY(point.getY());
        int newX = transformX(point.getX());

        return Point.create(newY, newX);
    }

    public static boolean isDirection(Key key)
    {
        switch (key)
        {
            case DIRECTION_N:
            case DIRECTION_NE:
            case DIRECTION_E:
            case DIRECTION_SE:
            case DIRECTION_S:
            case DIRECTION_SW:
            case DIRECTION_W:
            case DIRECTION_NW:
                return true;
            default:
                return false;
        }
    }

    public static Direction fromKey(Key key)
    {
        switch (key)
        {
            case DIRECTION_N:
                return Direction.N;
            case DIRECTION_NE:
                return Direction.NE;
            case DIRECTION_E:
                return Direction.E;
            case DIRECTION_SE:
                return Direction.SE;
            case DIRECTION_S:
                return Direction.S;
            case DIRECTION_SW:
                return Direction.SW;
            case DIRECTION_W:
                return Direction.W;
            case DIRECTION_NW:
                return Direction.NW;
            default:
            case DIRECTION_C:
                return Direction.C;
        }
    }

    public static Direction random()
    {
        Direction[] values = Direction.values();
        Direction randomDirection = values[RandomUtil.randomInt(values.length)];

        return randomDirection;
    }
}
