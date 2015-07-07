package simplerogue.engine.test.isolated;

import org.junit.Test;
import simplerogue.engine.resource.DefaultResourceManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.resource.Directory;
import simplerogue.engine.resource.FileResource;
import simplerogue.engine.resource.Resource;
import simplerogue.engine.resource.ResourceManager;
import simplerogue.engine.resource.TreeResource;
import simplerogue.engine.test.IsolatedTest;
import simplerogue.engine.test.TestConsts;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Wyłuda
 */
public class ResourceIsolatedTest extends IsolatedTest
{
    private ResourceManager resourceManager;

    @Override
    protected void beforeInit()
    {
        super.afterInit();

        resourceManager = new DefaultResourceManager();
        Managers.register(ResourceManager.class, resourceManager);
    }

    @Test
    public void testListResources()
    {
        List<Resource> resources = resourceManager.listResources(TestConsts.SAVE_PATH);
        assertEquals(2, resources.size());

        boolean hasFile, hasDirectory;
        hasFile = hasDirectory = false;

        for (Resource resource : resources)
        {
            hasFile = hasFile || resource.is(FileResource.class);
            hasDirectory = hasDirectory || resource.is(Directory.class);
        }

        assertTrue(hasFile && hasDirectory);
    }

    @Test
    public void testGetResource()
    {
        Resource resource = resourceManager.getResource(TestConsts.FIELDS_PATH);
        assertTrue(resource.is(TreeResource.class));

        TreeResource treeResource = resource.reify(TreeResource.class);
        List<Model> models = treeResource.readModelArray();
        assertEquals(3, models.size());

        Model wallModel = models.get(0);
        assertEquals(50, wallModel.getInt(TestConsts.WALL_TRANSPARENCY));

        Model floorModel = models.get(1);
        List<String> strings = floorModel.getStrings("test_array");
        assertEquals(2, strings.size());
        assertEquals("def", strings.get(0));
        assertEquals("rty", strings.get(1));
    }
}
