package simplerogue.engine.levelgenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractLevelGenerator<T extends LevelRequest>
        extends AbstractReifiable
        implements LevelGenerator<T>
{
    private final String name;

    @Override
    public T createRequest()
    {
        return (T) LevelRequest.create(getName());
    }
}
