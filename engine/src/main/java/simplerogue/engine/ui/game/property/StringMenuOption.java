package simplerogue.engine.ui.game.property;

import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.game.StringGameProperty;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.View;
import simplerogue.engine.view.Key;
import simplerogue.engine.view.ViewManager;

/**
 * @author Adam Wyłuda
 */
public class StringMenuOption<G extends GameConfiguration> extends PropertyMenuOption<String>
{
    private static final String TEXT_FORMAT = "%s: %s";

    private final StringGameProperty<G> property;
    private final G config;


    public StringMenuOption(StringGameProperty<G> property, G config)
    {
        this.property = property;
        this.config = config;

        setHandler(createHandler());
        refreshText();
    }

    private void refreshText()
    {
        String name = property.getName();
        String value = property.getValue(config);

        setText(String.format(TEXT_FORMAT, name, value));
    }

    private OptionHandler<StringMenuOption> createHandler()
    {
        return new OptionHandler<StringMenuOption>()
        {
            @Override
            public void perform(StringMenuOption option, Key key)
            {
                handle(key);
            }
        };
    }

    private void handle(Key key)
    {
        View view = Managers.get(ViewManager.class).getView();

        String name = property.getName();
        String value = property.getValue(config);

        String newValue = view.promptText(name, value);
        property.setValue(config, newValue);

        refreshText();
    }
}
