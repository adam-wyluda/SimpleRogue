package simplerogue.engine.ui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.view.Char;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.CharUtil;
import simplerogue.engine.view.Color;

/**
 * Layer decorated with a frame.
 *
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class FramedLayer extends Layer
{
    public static final int CONTENT_GAP = 2;

    private Char frameChar;
    private Char contentChar;

    protected FramedLayer()
    {
        frameChar = Char.create('#', Color.GREEN);
        contentChar = Char.DEFAULT_CHAR;
    }

    @Override
    public void render(CharArea area)
    {
        CharUtil.drawRectangle(area, frameChar, contentChar);

        // Where content starts
        int contentY = CONTENT_GAP;
        int contentX = CONTENT_GAP;
        int height = area.getHeight() - 2 * CONTENT_GAP;
        int width = area.getWidth() - 2 * CONTENT_GAP;

        CharArea content = area.getPart(contentY, contentX, height, width);
        renderContent(content);

        area.draw(content, contentY, contentX);
    }

    protected abstract void renderContent(CharArea area);
}
