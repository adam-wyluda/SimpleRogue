package simplerogue.javase.manager;

import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.platform.FileSystemStorage;
import simplerogue.engine.platform.PlatformManager;
import simplerogue.engine.platform.Storage;
import simplerogue.engine.platform.View;

import java.io.File;

/**
 * @author Adam Wyłuda
 */
public class JavaSEPlatformManager extends AbstractManager implements PlatformManager
{
    private static final String GAME_HOME = ".simplerogue";

    @Override
    public Storage createStorage()
    {
        File gameHome = getGameHome();

        return new FileSystemStorage(gameHome);
    }

    @Override
    public View createView()
    {
        return new SwingView();
    }

    private File getGameHome()
    {
        File userHome = getUserHome();
        File gameHome = new File(userHome, GAME_HOME);

        if (!gameHome.exists())
        {
            gameHome.mkdir();
        }

        return gameHome;
    }

    private File getUserHome()
    {
        String path = System.getProperty("user.home");
        File userHome = new File(path);

        return userHome;
    }
}
