package simplerogue.javase.manager;

import simplerogue.engine.application.ApplicationManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.View;
import simplerogue.engine.view.Char;
import simplerogue.engine.view.CharArea;
import simplerogue.engine.view.Key;
import simplerogue.engine.view.Render;
import simplerogue.engine.view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Swing view implementation.
 *
 * @author Adam Wyłuda
 */
public class SwingView implements View
{
    private final static Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private final static int CHAR_HEIGHT = 10;
    private final static int CHAR_WIDTH = 7;

    private JFrame frame;

    private CharArea charArea;

    public SwingView()
    {
        frame = new JFrame();

        frame.setSize(600, 500);
        // Move to center
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel()
        {

            @Override
            public void paintComponent(Graphics graphics)
            {
                draw(graphics);
            }
        };
        frame.setContentPane(panel);

        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                SwingView.this.keyPressed(e);
            }
        });
        frame.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                ViewManager viewManager = Managers.get(ViewManager.class);

                viewManager.refreshView();
            }

            @Override
            public void componentShown(ComponentEvent e)
            {
                String version = Managers.get(ApplicationManager.class).getEngineVersion().getFullString();
                frame.setTitle("SimpleRogue " + version);
            }
        });

        frame.setVisible(true);
    }

    @Override
    public void setRender(Render render)
    {
        this.charArea = render.getCharArea();

        frame.repaint();
    }

    @Override
    public String promptText(String title, String initialValue)
    {
        return JOptionPane.showInputDialog(title, initialValue);
    }

    @Override
    public int getWidth()
    {
        return frame.getWidth() / CHAR_WIDTH - 2;
    }

    @Override
    public int getHeight()
    {
        return frame.getHeight() / CHAR_HEIGHT - 4;
    }

    private void draw(Graphics graphics)
    {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(FONT);

        for (int y = 0; y < charArea.getHeight(); y++)
        {
            for (int x = 0; x < charArea.getWidth(); x++)
            {
                drawCharacter(graphics, charArea.getChars()[y][x], y, x);
            }
        }
    }

    private void drawCharacter(Graphics graphics, Char character, int y, int x)
    {
        graphics.setColor(toAWTColor(character.getBackgroundColor()));
        graphics.fillRect(x * CHAR_WIDTH, y * CHAR_HEIGHT, CHAR_WIDTH, CHAR_HEIGHT + 1);

        graphics.setColor(toAWTColor(character.getColor()));
        graphics.drawString(Character.toString(character.getCharacter()), x * CHAR_WIDTH, (y + 1) * CHAR_HEIGHT);
    }

    private Color toAWTColor(simplerogue.engine.view.Color color)
    {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    private void keyPressed(KeyEvent keyEvent)
    {
        ViewManager viewManager = Managers.get(ViewManager.class);
        Key key = AWTKeyMapping.MAPPING.get(keyEvent.getKeyCode());

        if (key != null)
        {
            viewManager.handleKey(key);
        }

        viewManager.refreshView();
    }
}
