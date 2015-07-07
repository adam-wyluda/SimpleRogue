package simplerogue.engine.game;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public abstract class SelectGameProperty<T, G extends GameConfiguration> extends AbstractGameProperty<T, G>
{
    public SelectGameProperty(String name)
    {
        super(name);
    }

    public abstract List<T> getChoices();
}
