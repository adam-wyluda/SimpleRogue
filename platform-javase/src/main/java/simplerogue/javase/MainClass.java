package simplerogue.javase;

import simplerogue.engine.EngineConfigurator;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.PlatformManager;
import simplerogue.engine.view.ViewManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldManager;
import simplerogue.javase.manager.JavaSEPlatformManager;
import simplerogue.world.DefaultWorldManager;

import javax.swing.*;


/**
 * @author Adam Wyłuda
 */
public class MainClass
{
    public static void main(String... args)
    {
        configureSystem();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                WorldManager worldManager = new DefaultWorldManager();

                runGame(worldManager);
            }
        });
    }

    public static void configureSystem() {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
    }

    public static void runGame(WorldManager worldManager)
    {
        EngineConfigurator.registerDefaultManagers();
        Managers.register(PlatformManager.class, new JavaSEPlatformManager());
        Managers.register(WorldManager.class, worldManager);
        Managers.initAll();

        World world = Managers.get(WorldManager.class).getWorlds().get(0);
        Managers.get(GameManager.class).loadWorld(world);
        Managers.get(ViewManager.class).refreshView();
    }
}
