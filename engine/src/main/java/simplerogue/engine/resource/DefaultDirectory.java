package simplerogue.engine.resource;

import simplerogue.engine.manager.Managers;
import simplerogue.engine.platform.Storage;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class DefaultDirectory extends DefaultResource implements Directory
{
    public DefaultDirectory(String path, Storage storage)
    {
        super(path, storage);
    }

    @Override
    public List<Resource> getChildren()
    {
        return Managers.get(ResourceManager.class).listResources(getPath());
    }

    @Override
    public Resource getChild(String name)
    {
        return Managers.get(ResourceManager.class).getResource(getPath() + name);
    }
}
