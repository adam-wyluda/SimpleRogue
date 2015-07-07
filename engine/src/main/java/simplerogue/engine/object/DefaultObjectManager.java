package simplerogue.engine.object;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.level.ActiveField;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.LevelElement;
import simplerogue.engine.level.LevelElementTypes;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.save.SaveManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultObjectManager extends AbstractManager implements ObjectManager, WorldListener
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultObjectManager.class);

    private AIManager aiManager;
    private SaveManager saveManager;

    // Registries
    private int maxId;
    private Map<Integer, ObjectInstance> objects;
    private Map<Integer, Field> cachedFields;

    // World properties
    private Map<String, Class<? extends LevelElement>> objectTypes;
    private Map<String, Prototype> prototypes;
    private Map<Integer, Prototype> fieldPrototypes;

    @Override
    public void init()
    {
        aiManager = Managers.get(AIManager.class);
        saveManager = Managers.get(SaveManager.class);
    }

    @Override
    public void cleanup()
    {
        LOG.debug("Cleaning object registry");

        maxId = 0;
        objects = Maps.newHashMap();
        cachedFields = Maps.newHashMap();
    }

    @Override
    public void worldChanged(World world)
    {
        cleanup();

        prototypes = Maps.newHashMap();
        fieldPrototypes = Maps.newHashMap();

        objectTypes = Maps.newHashMap();
        objectTypes.putAll(LevelElementTypes.getMapping());
        objectTypes.putAll(world.getObjectTypes());

        List<Prototype> defaultPrototypes = generateDefaultPrototypes();
        List<Prototype> worldPrototypes = world.getPrototypes();
        List<Prototype> loadedPrototypes = saveManager.loadPrototypes(world.getName());

        LOG.debug("Loading default prototypes");
        for (Prototype prototype : defaultPrototypes)
        {
            registerPrototype(prototype);
        }

        LOG.debug("Loading world prototypes");
        for (Prototype prototype : worldPrototypes)
        {
            registerPrototype(prototype);
        }

        LOG.debug("Loading loaded prototypes");
        for (Prototype prototype : loadedPrototypes)
        {
            registerPrototype(prototype);
        }
    }

    private List<Prototype> generateDefaultPrototypes()
    {
        List<Prototype> result = Lists.newArrayList();

        for (Map.Entry<String, ?> entry : LevelElementTypes.getMapping().entrySet())
        {
            String name = entry.getKey();
            Prototype prototype = Prototype.create(new Model());

            prototype.setName(name);

            if (LevelElementTypes.getFieldMapping().containsKey(name))
            {
                int fieldId = LevelElementTypes.getFieldMapping().get(name);

                prototype.setFieldId(fieldId);
            }

            if (LevelElementTypes.getCached().contains(name))
            {
                prototype.setCached(true);
            }

            result.add(prototype);
        }

        return result;
    }

    @Override
    public int generateId()
    {
        return ++maxId;
    }

    @Override
    public void registerPrototype(Prototype prototype)
    {
        LOG.debug("Registering prototype {}", prototype.getName());

        prototypes.put(prototype.getName(), prototype);

        if (prototype.isField())
        {
            int fieldId = prototype.getFieldId();

            fieldPrototypes.put(fieldId, prototype);

            if (prototype.isCached())
            {
                Field cachedField = createField(fieldId);
                cachedFields.put(fieldId, cachedField);
            }
        }
    }

    @Override
    public Prototype getPrototype(String name)
    {
        Prototype result = prototypes.get(name);

        if (result == null)
        {
            throw ObjectException.prototypeNotFound(name);
        }
        return result;
    }

    @Override
    public Prototype getFieldPrototype(int fieldId)
    {
        Prototype result = fieldPrototypes.get(fieldId);

        if (result == null)
        {
            throw ObjectException.fieldPrototypeNotFound(fieldId);
        }

        return result;
    }

    @Override
    public void registerObject(ObjectInstance object)
    {
        LOG.debug("Registering object of type {} with id {}",
                object.getClass().getSimpleName(),
                object.getId());

        objects.put(object.getId(), object);

        if (object.getId() > maxId)
        {
            maxId = object.getId() + 1;
        }
    }

    @Override
    public boolean isRegistered(ObjectInstance object)
    {
        return objects.containsValue(object);
    }

    @Override
    public void removeObject(ObjectInstance object)
    {
        int id = object.getId();

        objects.remove(id);
    }

    @Override
    public <T extends ObjectInstance> T getObject(int id)
    {
        ObjectInstance result = objects.get(id);

        if (result == null)
        {
            throw ObjectException.objectNotFound(id);
        }

        return (T) result;
    }

    @Override
    public List<ObjectInstance> getAllObjects()
    {
        return Lists.newArrayList(objects.values());
    }

    @Override
    public <T extends ObjectInstance> List<T> getObjects(List<Integer> ids)
    {
        List<T> objects = Lists.newLinkedList();

        for (Integer id : ids)
        {
            T object = (T) getObject(id);
            objects.add(object);
        }

        return objects;
    }

    @Override
    public <T extends ObjectInstance> List<T> lookupObjects(Class<T> type)
    {
        List<T> result = Lists.newArrayList();

        for (ObjectInstance instance : objects.values())
        {
            if (instance.is(type))
            {
                result.add(instance.reify(type));
            }
        }

        return result;
    }

    @Override
    public <T extends ObjectInstance> T createObject(String prototypeName)
    {
        return createObject(prototypeName, true);
    }

    @Override
    public <T extends ObjectInstance> T createObject(String prototypeName, boolean register)
    {
        Prototype prototype = getPrototype(prototypeName);

        T object = (T) createAndLoad(prototype);
        int id = generateId();
        object.setId(id);

        if (register)
        {
            registerObject(object);
        }

        if (prototype.isField())
        {
            Field field = object.reify(Field.class);
            int fieldId = prototype.getFieldId();

            field.setFieldId(fieldId);
        }

        return object;
    }

    @Override
    public <T extends Field> T createField(int fieldId)
    {
        Field result;

        result = cachedFields.get(fieldId);

        if (result == null)
        {
            Prototype prototype = getFieldPrototype(fieldId);

            result = (T) createAndLoad(prototype);

            if (result.is(ActiveField.class))
            {
                ActiveField activeField = result.reify(ActiveField.class);

                int id = generateId();
                activeField.setId(id);
            }
        }

        return (T) result;
    }

    private <T extends LevelElement> T createAndLoad(Prototype prototype)
    {
        Class<? extends LevelElement> clazz = objectTypes.get(prototype.getType());

        T object = ObjectUtil.create(clazz);
        object.load(new Model(prototype.getModel()));

        return object;
    }
}
