package simplerogue.engine.resource;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public interface Directory extends Resource
{
    /**
     * All resources in this directory.
     */
    List<Resource> getChildren();

    /**
     * @return Child resource with given relative generator.
     */
    Resource getChild(String name);
}
