package simplerogue.engine.level;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.Data;
import simplerogue.engine.object.AbstractReifiable;
import simplerogue.engine.object.Loadable;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.Named;
import simplerogue.engine.object.ObjectInstance;

import java.util.List;

/**
 * Level is composed of a map of fields, and list of object instances placed on it.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.level.Field
 * @see simplerogue.engine.level.FieldArea
 * @see simplerogue.engine.object.ObjectInstance
 */
@Data
public class Level extends AbstractReifiable implements Loadable, Named
{
    public static final String NAME = "level";

    public static final String LEVEL_TYPE = "level_type";

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";

    /**
     * Level generator, not persisted in properties (but is stored as directory generator). Never changes.
     */
    private String name;

    private String type;
    private FieldArea area;
    private List<ObjectInstance> objects = Lists.newArrayList();

    @Override
    public void load(Model model)
    {
    }

    @Override
    public Model save()
    {
        Model model = new Model();

        model.put(HEIGHT, area.getHeight());
        model.put(WIDTH, area.getWidth());

        return model;
    }

    public void putElement(FieldElement element, int y, int x)
    {
        element.setLevel(this);
        element.setPosY(y);
        element.setPosX(x);

        Field field = area.getFieldAt(y, x);
        field.getElements().add(element);
        objects.add(element);
    }

    public void putField(Field field, int y, int x)
    {
        area.setFieldAt(field, y, x);

        if (!field.isCached())
        {
            field.setLevel(this);
            field.setPosY(y);
            field.setPosX(x);
        }

        if (field.is(ActiveField.class))
        {
            objects.add(field.reify(ActiveField.class));
        }
    }

    public void removeElement(FieldElement element)
    {
        element.getField().getElements().remove(element);
        objects.remove(element);
    }

    public <T extends ObjectInstance> Optional<T> lookupElement(Class<T> type)
    {
        Optional<T> result = Optional.absent();

        for (ObjectInstance object : objects)
        {
            if (object.is(type))
            {
                result = Optional.of((T) object);
                break;
            }
        }

        return result;
    }

    public <T extends ObjectInstance> List<T> lookupElements(Class<T> type)
    {
        List<T> result = Lists.newArrayList();

        for (ObjectInstance object : objects)
        {
            if (object.is(type))
            {
                result.add((T) object);
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
}
