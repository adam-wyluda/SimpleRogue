package simplerogue.engine.resource;

import simplerogue.engine.object.Named;
import simplerogue.engine.object.Reifiable;

/**
 * @author Adam Wyłuda
 */
public interface Resource extends Reifiable, Named
{
    /**
     * Absolute path of this resource in file system defined by {@link simplerogue.engine.platform.Storage}.
     */
    String getPath();

    void remove();
}
