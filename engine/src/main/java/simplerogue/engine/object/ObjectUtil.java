package simplerogue.engine.object;

import simplerogue.engine.level.LevelElement;
import simplerogue.engine.manager.Managers;

/**
 * Object/prototype utilities.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.object.ObjectInstance
 * @see simplerogue.engine.object.Prototype
 */
public class ObjectUtil
{
    /**
     * Shortcut for ObjectManager.getPrototype(getPrototypeName(model)).
     *
     * @see simplerogue.engine.object.ObjectManager#getPrototype(String)
     */
    public static Prototype getPrototype(Model model)
    {
        return Managers.get(ObjectManager.class).getPrototype(getPrototypeName(model));
    }

    /**
     * Reads prototype generator from model.
     */
    public static String getPrototypeName(Model model)
    {
        return model.getString(Prototype.NAME);
    }

    public static void setPrototypeName(Model finalModel, String name)
    {
        finalModel.put(Prototype.NAME, name);
    }

    /**
     * Reads object instance id from model.
     *
     * @see simplerogue.engine.object.ObjectInstance
     */
    public static int getId(Model model)
    {
        return model.getInt(ObjectInstance.ID);
    }

    /**
     * Sets object instance id to model.
     *
     * @see simplerogue.engine.object.ObjectInstance
     */
    public static void setId(Model model, int id)
    {
        model.put(ObjectInstance.ID, id);
    }

    public static int getFieldId(Model model)
    {
        return model.has(Prototype.FIELD_ID) ? model.getInt(Prototype.FIELD_ID) : -1;
    }

    public static void setFieldId(Model model, int fieldId)
    {
        model.put(Prototype.FIELD_ID, fieldId);
    }

    public static boolean isCached(Model model)
    {
        return model.has(Prototype.CACHED) ? model.getBoolean(Prototype.CACHED) : false;
    }

    public static void setCached(Model model, boolean cached)
    {
        model.put(Prototype.CACHED, cached);
    }

    public static <T extends LevelElement> T create(Class<? extends LevelElement> clazz)
    {
        T object = (T) instantiate(clazz);

        if (object == null)
        {
            throw ObjectException.instantiationError(clazz.getCanonicalName());
        }

        return object;
    }

    private static <T extends LevelElement> T instantiate(Class<? extends LevelElement> clazz)
    {
        T object = null;

        try
        {
            object = (T) clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return object;
    }
}
