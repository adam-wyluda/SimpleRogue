package simplerogue.engine.ui.game;

import com.google.common.base.Optional;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.Layer;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.Key;

/**
 * @author Adam Wyłuda
 */
public class PromptMenuLayer extends MenuLayer
{
    public static final String NAME = "PROMPT_MENU";

    public static enum Options
    {
        YES_NO, YES_NO_CANCEL, OK;
    }

    public static enum Answer
    {
        YES, NO, CANCEL, OK;
    }

    public static interface Callback
    {
        void handle(Answer answer);
    }

    public static final Callback NULL_CALLBACK = new Callback()
    {
        @Override
        public void handle(Answer answer)
        {
        }
    };

    private final Optional<Layer> previousLayer;
    private final Callback callback;

    public static void showPrompt(String title, Options options, Callback callback)
    {
        UIManager uiManager = Managers.get(UIManager.class);
        Optional<Layer> activeLayer = uiManager.getActiveLayer();

        PromptMenuLayer promptLayer = new PromptMenuLayer(title, activeLayer, options, callback);

        uiManager.registerLayer(promptLayer);
        uiManager.setActiveLayer(promptLayer);
    }

    public static void showYesNoPrompt(String title, Callback callback)
    {
        showPrompt(title, Options.YES_NO, callback);
    }

    public static void showYesNoCancelPrompt(String title, Callback callback)
    {
        showPrompt(title, Options.YES_NO_CANCEL, callback);
    }

    public static void showOkPrompt(String title)
    {
        showOkPrompt(title, NULL_CALLBACK);
    }

    public static void showOkPrompt(String title, Callback callback)
    {
        showPrompt(title, Options.OK, callback);
    }

    public PromptMenuLayer(String title, Optional<Layer> previousLayer, Options options, Callback callback)
    {
        super(NAME);

        this.previousLayer = previousLayer;
        this.callback = callback;

        setTitle(title);
        setPriority(NOTIFICATION_PRIORITY);

        createMenuOptions(options);
    }

    private void createMenuOptions(Options options)
    {
        switch (options)
        {
            default:
            case YES_NO:
                createYesNoOptions();
                break;
            case YES_NO_CANCEL:
                createYesNoCancelOptions();
                break;
            case OK:
                createOkOptions();
                break;
        }

        setSelectedOption(getOptions().get(getOptions().size() - 1));
    }

    private void createYesNoCancelOptions()
    {
        createYesNoOptions();
        createAnswerOption("Cancel", Answer.CANCEL);
    }

    private void createYesNoOptions()
    {
        createAnswerOption("Yes", Answer.YES);
        createAnswerOption("No", Answer.NO);
    }

    private void createOkOptions()
    {
        createAnswerOption("OK", Answer.OK);
    }

    private void createAnswerOption(String text, final Answer optionAnswer)
    {
        getOptions().add(MenuOption.create(text, new MenuOption.OptionHandler<MenuOption>()
        {
            @Override
            public void perform(MenuOption option, Key key)
            {
                answer(optionAnswer);
            }
        }));
    }

    private void answer(Answer answer)
    {
        navigateBack();
        callback.handle(answer);
    }

    private void navigateBack()
    {
        UIManager uiManager = Managers.get(UIManager.class);

        uiManager.removeLayer(this);

        if (previousLayer.isPresent())
        {
            uiManager.setActiveLayer(previousLayer.get());
        }
    }
}
