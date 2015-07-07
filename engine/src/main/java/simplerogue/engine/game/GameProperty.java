package simplerogue.engine.game;

import simplerogue.engine.object.Named;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.ui.game.property.PropertyMenuOption;

/**
 * Property used by {@link simplerogue.engine.game.GameConfigurator}
 *
 * @author Adam Wyłuda
 */
public interface GameProperty<T, G extends GameConfiguration> extends Named, Reifiable
{
    T getValue(G config);

    void setValue(G config, T value);

    PropertyMenuOption<T> createMenuOption(G config);
}
