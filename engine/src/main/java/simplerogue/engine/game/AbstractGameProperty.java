package simplerogue.engine.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.AbstractReifiable;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractGameProperty<T, G extends GameConfiguration>
        extends AbstractReifiable
        implements GameProperty<T, G>
{
    private final String name;

    protected AbstractGameProperty(String name)
    {
        this.name = name;
    }
}
