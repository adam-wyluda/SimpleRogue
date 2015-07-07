package simplerogue.engine.resource;

import simplerogue.engine.platform.Storage;

/**
 * @author Adam Wyłuda
 */
public class DefaultFileResource extends DefaultResource implements FileResource
{
    public DefaultFileResource(String path, Storage storage)
    {
        super(path, storage);
    }

    @Override
    public int[] read()
    {
        return storage.readFileToInts(getPath());
    }

    @Override
    public void save(int[] data)
    {
        storage.writeIntsToFile(getPath(), data);
    }
}
