package simplerogue.engine.view;

import simplerogue.engine.manager.Manager;
import simplerogue.engine.platform.View;

/**
 * Manages views and passes actions to UI.
 *
 * @author Adam Wyłuda
 */
public interface ViewManager extends Manager
{
    /**
     * Pushes current UI render to view.
     */
    void refreshView();

    /**
     * @return Platform view object.
     */
    <V extends View> V getView();

    /**
     * Passes key pressed action to UI.
     */
    void handleKey(Key key);

    /**
     * @return Screen data.
     */
    Screen getScreen();
}
