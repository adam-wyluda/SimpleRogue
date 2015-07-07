package simplerogue.engine.resource;

import simplerogue.engine.platform.Storage;

/**
 * @author Adam Wyłuda
 */
public class DefaultResource extends AbstractResource implements Resource
{
    protected final Storage storage;

    public DefaultResource(String path, Storage storage)
    {
        super(path, ResourceUtil.getNameFromPath(path));

        this.storage = storage;
    }

    @Override
    public void remove()
    {
        storage.deleteFile(getPath());
    }
}
