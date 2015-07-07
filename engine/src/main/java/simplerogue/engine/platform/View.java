package simplerogue.engine.platform;

import simplerogue.engine.view.Render;

/**
 * Interface for UI display.
 *
 * @author Adam Wyłuda
 */
public interface View
{
    /**
     * Refreshes view with given CharArea.
     * @param render
     */
    void setRender(Render render);

    /**
     * Ask user for text input.
     * @param initialValue Initial text.
     */
    String promptText(String title, String initialValue);

    /**
     * How many characters fit vertically.
     */
    int getHeight();

    /**
     * How many characters fit horizontally.
     */
    int getWidth();
}
