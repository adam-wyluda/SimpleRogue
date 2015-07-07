package simplerogue.engine.resource;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.PlatformManager;
import simplerogue.engine.platform.Storage;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class DefaultResourceManager extends AbstractManager implements ResourceManager
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultResourceManager.class);

    private Storage storage;

    @Override
    public void init()
    {
        PlatformManager platformManager = Managers.get(PlatformManager.class);
        this.storage = platformManager.createStorage();
    }

    @Override
    public Resource createResource(String path)
    {
        LOG.debug("Creating resource at path {}", path);

        storage.createFile(path);
        Resource resource = getResource(path);

        return resource;
    }

    @Override
    public List<Resource> listResources(String path)
    {
        List<String> names = storage.listFiles(path);
        List<Resource> result = Lists.newArrayListWithCapacity(names.size());

        for (String name : names)
        {
            Resource resource = getResource(path + name);
            result.add(resource);
        }

        return result;
    }

    @Override
    public Resource getResource(String path)
    {
        Resource resource = ResourceFactory.createResource(storage, path);

        return resource;
    }
}
