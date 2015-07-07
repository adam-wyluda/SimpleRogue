package simplerogue.engine.object;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import simplerogue.engine.manager.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents tree data structure.
 *
 * @author Adam Wyłuda
 */
public class Model
{
    // Must be a list to retain property order
    @Getter
    private final List<Pair<String, Object>> properties;
    // Necessary for efficient lookup
    private final Map<String, Pair<String, Object>> index;

    /**
     * Creates a new clean model object.
     */
    public Model()
    {
        this.properties = Lists.newArrayList();
        this.index = Maps.newHashMap();
    }

    /**
     * Creates a new model with deep copy of given model properties.
     */
    public Model(Model origin)
    {
        this(origin.properties);
    }

    /**
     * Creates a new model with given properties.
     */
    public Model(List<Pair<String, Object>> properties)
    {
        this.properties = Lists.newArrayListWithCapacity(properties.size());
        this.index = Maps.newHashMapWithExpectedSize(properties.size());

        // Copy all Model elements
        for (Pair<String, Object> entry : properties)
        {
            Pair<String, Object> newProperty;

            if (entry.getValue() instanceof Model)
            {
                Model model = (Model) entry.getValue();
                newProperty = new MutablePair<String, Object>(entry.getKey(), new Model(model.properties));
            }
            else
            {
                newProperty = new MutablePair<>(entry.getKey(), entry.getValue());
            }

            this.properties.add(newProperty);
            this.index.put(newProperty.getKey(), newProperty);
        }
    }

    public <T> T get(String key)
    {
        Pair<?, ?> pair = index.get(key);

        return pair != null ? (T) pair.getValue() : null;
    }

    public int getInt(String key)
    {
        Object value = get(key);

        int result;

        if (value instanceof Number)
        {
            result = ((Number) value).intValue();
        }
        else
        {
            result = Integer.parseInt(value.toString());
        }

        return result;
    }

    public double getDouble(String key)
    {
        Object value = get(key);

        double result;

        if (value instanceof Number)
        {
            result = ((Number) value).doubleValue();
        }
        else
        {
            result = Double.parseDouble(value.toString());
        }

        return result;
    }

    public String getString(String key)
    {
        Object value = get(key);

        String result;

        if (value instanceof String)
        {
            result = (String) value;
        }
        else
        {
            result = value.toString();
        }

        return result;
    }

    public boolean getBoolean(String key)
    {
        Object value = get(key);

        boolean result;

        if (value instanceof Boolean)
        {
            result = (Boolean) value;
        }
        else
        {
            result = Boolean.parseBoolean(value.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getEnum(Class<T> type, String key)
    {
        Object value = get(key);

        T result;

        if (type.isInstance(value))
        {
            result = (T) value;
        }
        else
        {
            result = Enum.valueOf(type, value.toString());
        }

        return result;
    }

    public <T extends ObjectInstance> T getInstance(String key)
    {
        return Managers.get(ObjectManager.class).getObject(getInt(key));
    }

    public <T extends ObjectInstance> Optional<T> getInstanceOptional(String key)
    {
        Optional<T> result;

        if (has(key))
        {
            result = Optional.of((T) getInstance(key));
        }
        else
        {
            result = Optional.absent();
        }

        return result;
    }

    public Prototype getPrototype(String key)
    {
        String name = getString(key);
        return Managers.get(ObjectManager.class).getPrototype(name);
    }

    public List<String> getStrings(String key)
    {
        Object value = get(key);

        List<String> result;

        if (value instanceof List)
        {
            result = (List<String>) value;
        }
        else if (value == null)
        {
            result = new ArrayList<>();
            put(key, result);
        }
        else
        {
            throw new IllegalArgumentException("Value under key: " + key + " is not a list");
        }

        return result;
    }

    /**
     * Returns a list of integer values. If this was not defined then new list is created and added to model.
     */
    public List<Integer> getInts(String key)
    {
        Object value = get(key);

        List<Integer> result;

        if (value instanceof List)
        {
            List list = (List) value;

            if (list.isEmpty() || list.get(0) instanceof Integer)
            {
                result = (List<Integer>) value;
            }
            else if (list.get(0) instanceof Double)
            {
                result = new ArrayList<>();
                List<Double> numbers = (List<Double>) list;

                for (Double number : numbers)
                {
                    result.add((int) Math.round(number));
                }
            }
            else if (list.get(0) instanceof String)
            {
                result = new ArrayList<>();
                List<String> strings = (List<String>) list;

                for (String string : strings)
                {
                    result.add(Integer.parseInt(string));
                }
            }
            else
            {
                throw new IllegalArgumentException("Values under key: " + key + " are not strings");
            }
        }
        else if (value == null)
        {
            result = new ArrayList<>();
            put(key, result);
        }
        else
        {
            throw new IllegalArgumentException("Value under key: " + key + " is not a list");
        }

        return result;
    }

    public <T extends ObjectInstance> List<T> getInstances(String key)
    {
        return Managers.get(ObjectManager.class).getObjects(getInts(key));
    }

    public Model getModel(String key)
    {
        return (Model) get(key);
    }

    public void put(String key, Object object)
    {
        if (object != null)
        {
            if (index.containsKey(key))
            {
                index.get(key).setValue(object);
            }
            else
            {
                Pair<String, Object> newPair = new MutablePair<>(key, object);
                properties.add(newPair);
                index.put(key, newPair);
            }
        }
    }

    public void putInstance(String key, ObjectInstance object)
    {
        if (object != null)
        {
            put(key, object.getId());
        }
    }

    public void putInstances(String key, List<? extends ObjectInstance> objects)
    {
        if (objects != null)
        {
            List<Integer> ids = Lists.newArrayListWithExpectedSize(objects.size());

            for (ObjectInstance object : objects)
            {
                int id = object.getId();

                ids.add(id);
            }

            put(key, ids);
        }
    }

    public void putLoadable(String key, Loadable loadable)
    {
        if (loadable != null)
        {
            put(key, loadable.save());
        }
    }

    public void putLoadables(String key, List<? extends Loadable> loadables)
    {
        if (loadables != null)
        {
            List<Model> models = Lists.newArrayList();

            for (Loadable loadable : loadables)
            {
                Model model = loadable.save();
                models.add(model);
            }

            put(key, models);
        }
    }

    public void putPrototype(String key, Prototype prototype)
    {
        if (prototype != null)
        {
            put(key, prototype.getName());
        }
    }

    public boolean has(String key)
    {
        return index.containsKey(key);
    }

    public void remove(String key)
    {
        properties.remove(index.get(key));
        index.remove(key);
    }

    public Model merge(Model toMerge)
    {
        Model merge = new Model(this);

        // Update existing properties
        for (Pair<String, Object> entry : merge.getProperties())
        {
            Object otherValue = toMerge.get(entry.getKey());

            if (otherValue != null)
            {
                if (entry.getValue() instanceof Model && otherValue instanceof Model)
                {
                    Model thisModel = (Model) entry.getValue();
                    Model otherModel = (Model) otherValue;

                    entry.setValue(thisModel.merge(otherModel));
                }
                else if (otherValue instanceof Model)
                {
                    entry.setValue(new Model((Model) otherValue));
                }
                else if (otherValue instanceof Iterable)
                {
                    entry.setValue(Lists.newArrayList((Iterable) otherValue));
                }
                else
                {
                    entry.setValue(otherValue);
                }
            }
        }

        // Add new properties
        for (Pair<String, Object> entry : toMerge.getProperties())
        {
            if (merge.get(entry.getKey()) == null)
            {
                Object otherValue = entry.getValue();

                if (otherValue instanceof Model)
                {
                    merge.put(entry.getKey(), new Model((Model) otherValue));
                }
                else if (otherValue instanceof Iterable)
                {
                    merge.put(entry.getKey(), Lists.newArrayList((Iterable) otherValue));
                }
                else
                {
                    merge.put(entry.getKey(), otherValue);
                }
            }
        }

        return merge;
    }

    /**
     * Removes all properties from this that are duplicated in toDiff.
     */
    public Model diff(Model toDiff)
    {
        Model diff = new Model(this);

        List<String> toBeRemoved = Lists.newLinkedList();

        for (Pair<String, Object> entry : diff.getProperties())
        {
            Object otherValue = toDiff.get(entry.getKey());

            if (otherValue != null)
            {
                if (entry.getValue() instanceof Model && otherValue instanceof Model)
                {
                    Model thisModel = (Model) entry.getValue();
                    Model otherModel = (Model) otherValue;

                    Model otherDiff = thisModel.diff(otherModel);

                    if (otherDiff.properties.isEmpty())
                    {
                        toBeRemoved.add(entry.getKey());
                    }
                    else
                    {
                        entry.setValue(otherDiff);
                    }
                }
                else if (!(entry.getValue() instanceof Model || otherValue instanceof Model))
                {
                    if (entry.getValue().equals(otherValue))
                    {
                        toBeRemoved.add(entry.getKey());
                    }
                }
            }
        }

        for (String toRemove : toBeRemoved)
        {
            diff.remove(toRemove);
        }

        return diff;
    }
}
