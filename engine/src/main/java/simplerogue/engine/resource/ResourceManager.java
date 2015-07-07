package simplerogue.engine.resource;

import simplerogue.engine.manager.Manager;

import java.util.List;

/**
 * Manages application resources.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.platform.Storage
 */
public interface ResourceManager extends Manager
{
    Resource createResource(String path);

    /**
     * @return List of resources at given path.
     */
    List<Resource> listResources(String path);

    /**
     * @return Resource at given path.
     */
    Resource getResource(String path);
}
