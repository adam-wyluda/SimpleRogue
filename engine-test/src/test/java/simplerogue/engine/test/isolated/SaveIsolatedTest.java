package simplerogue.engine.test.isolated;

import com.google.common.collect.Lists;
import org.junit.Test;
import simplerogue.engine.save.DefaultSaveManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.resource.Directory;
import simplerogue.engine.resource.Resource;
import simplerogue.engine.resource.ResourceManager;
import simplerogue.engine.resource.Resources;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.test.IsolatedTest;
import simplerogue.engine.test.Mocks;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Adam Wyłuda
 */
public class SaveIsolatedTest extends IsolatedTest
{
    private SaveManager saveManager;

    @Override
    protected void beforeInit()
    {
        super.beforeInit();

        saveManager = new DefaultSaveManager();
        Managers.register(SaveManager.class, saveManager);

        List<Resource> resources = Lists.newArrayList();
        resources.add(Mocks.resource(Directory.class, Resources.SAVES + "abc/"));
        resources.add(Mocks.resource(Directory.class, Resources.SAVES + "def/"));
        resources.add(Mocks.resource(Directory.class, Resources.SAVES + "ghi/"));

        ResourceManager resourceManager = Managers.get(ResourceManager.class);
        when(resourceManager.listResources(Resources.SAVES)).thenReturn(resources);
    }

    @Test
    public void testListSaves()
    {
        List<String> saves = saveManager.listSaves();

        assertEquals(3, saves.size());
        assertThat(saves, hasItems("abc", "def", "ghi"));
    }
}
