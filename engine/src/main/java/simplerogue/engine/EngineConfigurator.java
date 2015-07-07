package simplerogue.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.action.DefaultActionManager;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.DefaultAIManager;
import simplerogue.engine.application.ApplicationManager;
import simplerogue.engine.application.DefaultApplicationManager;
import simplerogue.engine.game.DefaultGameManager;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.DefaultLevelManager;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.levelgenerator.DefaultLevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.object.DefaultObjectManager;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.resource.DefaultResourceManager;
import simplerogue.engine.resource.ResourceManager;
import simplerogue.engine.save.DefaultSaveManager;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.ui.DefaultUIManager;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.DefaultViewManager;
import simplerogue.engine.view.ViewManager;

import static simplerogue.engine.manager.Managers.register;

/**
 * @author Adam Wyłuda
 */
public class EngineConfigurator
{
    private static final Logger LOG = LoggerFactory.getLogger(EngineConfigurator.class);

    public static void registerDefaultManagers()
    {
        LOG.info("Registering default managers");

        register(ActionManager.class, new DefaultActionManager());
        register(AIManager.class, new DefaultAIManager());
        register(ApplicationManager.class, new DefaultApplicationManager());
        register(GameManager.class, new DefaultGameManager());
        register(LevelManager.class, new DefaultLevelManager());
        register(LevelGeneratorManager.class, new DefaultLevelGeneratorManager());
        register(ObjectManager.class, new DefaultObjectManager());
        register(ResourceManager.class, new DefaultResourceManager());
        register(SaveManager.class, new DefaultSaveManager());
        register(UIManager.class, new DefaultUIManager());
        register(ViewManager.class, new DefaultViewManager());
    }
}
