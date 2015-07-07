package simplerogue.engine.view;

import lombok.Data;
import simplerogue.engine.level.Direction;

/**
 * UI render data.
 *
 * @author Adam Wyłuda
 */
@Data
public class Render
{
    private CharArea charArea;
    private Direction shiftDirection;
}
