package simplerogue.engine.object;

/**
 * Object that can load its properties from {@link simplerogue.engine.object.Model}.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.object.Model
 */
public interface Loadable
{
    /**
     * Loads properties from given model.
     */
    void load(Model model);

    /**
     * Stores properties to model.
     */
    Model save();
}
