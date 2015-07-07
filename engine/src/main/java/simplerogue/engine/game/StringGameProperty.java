package simplerogue.engine.game;

import simplerogue.engine.ui.game.property.PropertyMenuOption;
import simplerogue.engine.ui.game.property.StringMenuOption;

/**
 * @author Adam Wyłuda
 */
public abstract class StringGameProperty <G extends GameConfiguration> extends AbstractGameProperty<String, G>
{
    protected StringGameProperty(String name)
    {
        super(name);
    }

    @Override
    public PropertyMenuOption<String> createMenuOption(G config)
    {
        return new StringMenuOption<>(this, config);
    }
}
