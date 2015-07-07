package simplerogue.engine.ui.game.property;

import simplerogue.engine.game.EnumGameProperty;
import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.view.Key;

/**
 * @author Adam Wyłuda
 */
public class EnumMenuOption<T extends Enum<T>, G extends GameConfiguration>
        extends PropertyMenuOption<T>
{
    private static final String TEXT_FORMAT = "%s: %s < >";

    private final EnumGameProperty<T, G> property;
    private final G config;

    public EnumMenuOption(EnumGameProperty<T, G> property, G config)
    {
        this.property = property;
        this.config = config;

        setHandler(createHandler());
        refreshText();
    }

    private void refreshText()
    {
        String name = property.getName();
        T value = property.getValue(config);

        setText(String.format(TEXT_FORMAT, name, value));
    }

    private OptionHandler<EnumMenuOption> createHandler()
    {
        return new OptionHandler<EnumMenuOption>()
        {
            @Override
            public void perform(EnumMenuOption option, Key key)
            {
                handle(key);
            }
        };
    }

    private void handle(Key key)
    {
        switch (key)
        {
            case DIRECTION_W:
                previousValue();
                break;
            case DIRECTION_E:
                nextValue();
                break;
        }

        refreshText();
    }

    private void previousValue()
    {
        T value = property.getValue(config);
        int index = property.getChoices().indexOf(value);
        int nextIndex = (index - 1 + property.getChoices().size()) % property.getChoices().size();
        T nextValue = property.getChoices().get(nextIndex);

        property.setValue(config, nextValue);
    }

    private void nextValue()
    {
        T value = property.getValue(config);
        int index = property.getChoices().indexOf(value);
        int nextIndex = (index + 1) % property.getChoices().size();
        T nextValue = property.getChoices().get(nextIndex);

        property.setValue(config, nextValue);
    }
}
