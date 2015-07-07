package simplerogue.engine.game;

import com.google.common.collect.Lists;
import lombok.Getter;
import simplerogue.engine.ui.game.property.EnumMenuOption;
import simplerogue.engine.ui.game.property.PropertyMenuOption;

import java.util.List;

/**
 * Game property that is based on enum value.
 *
 * @author Adam Wyłuda
 */
public abstract class EnumGameProperty<T extends Enum<T>, G extends GameConfiguration> extends SelectGameProperty<T, G>
{
    @Getter
    private final List<T> choices;
    private T value;

    protected EnumGameProperty(String name, List<T> choices)
    {
        super(name);

        this.choices = choices;
    }

    protected EnumGameProperty(String name, Class<T> type)
    {
        super(name);

        this.choices = Lists.newArrayList(type.getEnumConstants());
    }

    protected EnumGameProperty(Class<T> type)
    {
        this(type.getSimpleName(), type);
    }

    @Override
    public PropertyMenuOption<T> createMenuOption(G config)
    {
        return new EnumMenuOption<>(this, config);
    }
}
