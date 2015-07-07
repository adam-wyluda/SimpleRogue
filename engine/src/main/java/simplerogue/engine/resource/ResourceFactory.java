package simplerogue.engine.resource;

import simplerogue.engine.platform.Storage;

/**
 * @author Adam Wyłuda
 */
public class ResourceFactory
{
    /**
     * Creates resource based on path.
     */
    public static Resource createResource(Storage storage, String path)
    {
        Resource result;

        if (ResourceUtil.isDirectory(path))
        {
            result = new DefaultDirectory(path, storage);
        }
        else if (path.endsWith(Resources.JSON_EXT))
        {
            result = new JSONTreeResource(path, storage);
        }
        else if (path.endsWith(Resources.MAP_EXT))
        {
            result = new DefaultMapResource(path, storage);
        }
        else
        {
            result = new DefaultFileResource(path, storage);
        }

        return result;
    }
}
