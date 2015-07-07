package simplerogue.engine.ai.util.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.xguzm.pathfinding.grid.GridCell;
import simplerogue.engine.level.FieldArea;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor(staticName = "create")
public class FieldNode extends GridCell
{
    private final FieldArea area;
    private final int y;
    private final int x;
    private final boolean enforceWalkable;

    @Override
    public boolean isWalkable()
    {
        return enforceWalkable || !area.getFieldAt(y, x).isBlocking();
    }
}
