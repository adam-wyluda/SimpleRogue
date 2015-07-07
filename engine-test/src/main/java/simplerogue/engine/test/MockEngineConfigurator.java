package simplerogue.engine.test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.application.ApplicationManager;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.resource.ResourceManager;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.view.ViewManager;
import simplerogue.engine.game.GameUtil;
import simplerogue.engine.world.World;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static simplerogue.engine.manager.Managers.get;
import static simplerogue.engine.manager.Managers.register;

/**
 * @author Adam Wyłuda
 */
public class MockEngineConfigurator
{
    public static void registerMockManagers()
    {
        register(ActionManager.class, mock(ActionManager.class));
        register(AIManager.class, mock(AIManager.class));
        register(ApplicationManager.class, mock(ApplicationManager.class));
        register(GameManager.class, mock(GameManager.class));
        register(LevelManager.class, mock(LevelManager.class));
        register(LevelGeneratorManager.class, mock(LevelGeneratorManager.class));
        register(ObjectManager.class, mock(ObjectManager.class));
        register(ResourceManager.class, mock(ResourceManager.class));
        register(SaveManager.class, mock(SaveManager.class));
        register(UIManager.class, mock(UIManager.class));
        register(ViewManager.class, mock(ViewManager.class));

        GameManager gameManager = get(GameManager.class);
        doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                World world = (World) invocation.getArguments()[0];
                GameUtil.notifyAboutWorldChange(world);

                return null;
            }
        }).when(gameManager).loadWorld(any(World.class));
    }
}
