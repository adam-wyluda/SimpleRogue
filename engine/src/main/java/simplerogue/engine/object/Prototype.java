package simplerogue.engine.object;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import simplerogue.engine.manager.Managers;

/**
 * Object prototype.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.object.ObjectInstance
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Prototype implements HasModel, Named
{
    /**
     * Unique generator of this prototype.
     */
    static final String NAME = "prototype_id";

    /**
     * {@link simplerogue.engine.object.ObjectInstance} type of this prototype.
     */
    private static final String TYPE = "type";

    /**
     * Unique id if prototype is a Field.
     */
    public static final String FIELD_ID = "field_id";

    /**
     * Tells if the field is cacheable (to use only one instance in levels).
     */
    static final String CACHED = "cached";

    protected Model model;

    public static Prototype create(Model model)
    {
        return new Prototype(model);
    }

    public String getName()
    {
        return model.getString(NAME);
    }

    public void setName(String name)
    {
        model.put(NAME, name);
    }

    /**
     * @return Object type of this prototype. If not defined then returns same as {@link #getName()}.
     */
    public String getType()
    {
        return model.has(TYPE) ? model.getString(TYPE) : getName();
    }

    public void setType(String type)
    {
        model.put(TYPE, type);
    }

    public int getFieldId()
    {
        return model.getInt(FIELD_ID);
    }

    public void setFieldId(int id)
    {
        model.put(FIELD_ID, id);
    }

    public boolean isField()
    {
        return model.has(FIELD_ID);
    }

    public boolean isCached()
    {
        return ObjectUtil.isCached(model);
    }

    public void setCached(boolean cached)
    {
        ObjectUtil.setCached(model, cached);
    }

    public <T> T create()
    {
        T result;

        if (isField())
        {
            result = (T) Managers.get(ObjectManager.class).createField(getFieldId());
        }
        else
        {
            result = (T) Managers.get(ObjectManager.class).createObject(getName());
        }

        return result;
    }
}
