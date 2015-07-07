package simplerogue.engine.ai.util.pathfinding;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.xguzm.pathfinding.NavigationGraph;
import org.xguzm.pathfinding.PathFinderOptions;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.Point;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
@AllArgsConstructor(staticName = "create")
public class FieldAreaGraph implements NavigationGraph<FieldNode>
{
    private final FieldArea fieldArea;
    private final Map<Point, FieldNode> nodes = Maps.newHashMap();

    @Override
    public List<FieldNode> getNeighbors(FieldNode node)
    {
        List<FieldNode> result = Lists.newArrayListWithExpectedSize(8);

        int y = node.getY();
        int x = node.getX();

        for (Direction direction : Direction.NON_IDEMPOTENT)
        {
            int nY = direction.transformY(y);
            int nX = direction.transformX(x);

            FieldNode neighbor = getFieldNode(nY, nX);
            result.add(neighbor);
        }

        return result;
    }

    public FieldNode getFieldNode(int y, int x)
    {
        return getFieldNode(y, x, false);
    }

    public FieldNode getFieldNode(int y, int x, boolean enforceWalkable)
    {
        Point point = Point.create(y, x);
        FieldNode node = nodes.get(point);

        if (node == null)
        {
            node = FieldNode.create(fieldArea, y, x, enforceWalkable);
            nodes.put(point, node);
        }

        return node;
    }

    @Override
    public List<FieldNode> getNeighbors(FieldNode node, PathFinderOptions opt)
    {
        return getNeighbors(node);
    }

    @Override
    public float getMovementCost(FieldNode node1, FieldNode node2, PathFinderOptions opt)
    {
        return 1.0f;
    }

    @Override
    public boolean isWalkable(FieldNode node)
    {
        return node.isWalkable();
    }
}
