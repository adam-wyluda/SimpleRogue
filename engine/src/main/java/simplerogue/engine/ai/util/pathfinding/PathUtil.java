package simplerogue.engine.ai.util.pathfinding;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.xguzm.pathfinding.PathFinderOptions;
import org.xguzm.pathfinding.finders.AStarFinder;
import simplerogue.engine.action.MoveAction;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.FieldElement;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class PathUtil
{
    public static Optional<MoveAction> moveTo(Actor actor, FieldElement target)
    {
        return moveTo(actor, target.getField());
    }

    public static Optional<MoveAction> moveTo(Actor actor, Field target)
    {
        Preconditions.checkArgument(actor.getLevel() == target.getLevel(), "Elements must be on the same level");

        return moveTo(actor, target.getPosY(), target.getPosX());
    }

    public static Optional<MoveAction> moveTo(Actor actor, int y, int x)
    {
        FieldArea area = actor.getLevel().getArea();

        FieldAreaGraph graph = FieldAreaGraph.create(area);
        PathFinderOptions options = new PathFinderOptions()
        {
        };
        options.heuristic = new FieldHeuristic();

        FieldNode startNode = graph.getFieldNode(actor.getPosY(), actor.getPosX(), true);
        FieldNode endNode = graph.getFieldNode(y, x, true);

        Optional<MoveAction> result = Optional.absent();

        AStarFinder aStarFinder = new AStarFinder(FieldNode.class, options);
        List<FieldNode> path = aStarFinder.findPath(startNode, endNode, graph);

        if (path != null && !path.isEmpty())
        {
            FieldNode nextStep = path.get(0);
            MoveAction action = MoveAction.create(actor, nextStep.getY(), nextStep.getX());
            result = Optional.of(action);
        }

        return result;
    }
}
