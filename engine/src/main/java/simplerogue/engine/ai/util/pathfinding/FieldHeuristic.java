package simplerogue.engine.ai.util.pathfinding;

import org.xguzm.pathfinding.Heuristic;
import org.xguzm.pathfinding.NavigationNode;
import simplerogue.engine.level.LevelUtil;

/**
 * @author Adam Wyłuda
 */
public class FieldHeuristic implements Heuristic
{
    @Override
    public float calculate(NavigationNode from, NavigationNode to)
    {
        int y1 = ((FieldNode) from).getY();
        int x1 = ((FieldNode) from).getX();
        int y2 = ((FieldNode) to).getY();
        int x2 = ((FieldNode) to).getX();

        double cost = LevelUtil.euclideanDistance(y1, x1, y2, x2);

        return (float) cost;
    }
}
