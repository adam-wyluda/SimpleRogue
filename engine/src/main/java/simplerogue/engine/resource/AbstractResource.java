package simplerogue.engine.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractResource extends AbstractReifiable implements Resource
{
    private final String path;
    private final String name;
}
