package simplerogue.engine.object;

import simplerogue.engine.level.Field;
import simplerogue.engine.manager.Manager;

import java.util.List;

/**
 * Registry of all objects.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.object.ObjectInstance
 * @see simplerogue.engine.object.Prototype
 */
public interface ObjectManager extends Manager
{
    /**
     * Cleans all registries.
     */
    void cleanup();

    /**
     * Returns unique id for object.
     */
    int generateId();

    /**
     * Registers given prototype.
     */
    void registerPrototype(Prototype prototype);

    /**
     * Returns prototype registered with given generator.
     *
     * @throws simplerogue.engine.object.ObjectException If prototype with given generator was not found.
     */
    Prototype getPrototype(String name);

    /**
     * Returns field prototype registered with given field.
     *
     * @throws simplerogue.engine.object.ObjectException If prototype with given id was not found.
     */
    Prototype getFieldPrototype(int fieldId);

    /**
     * Registers given {@link simplerogue.engine.object.ObjectInstance}.
     */
    void registerObject(ObjectInstance object);

    /**
     * Returns true if given object is registered.
     *
     * @see #registerObject(ObjectInstance)
     */
    boolean isRegistered(ObjectInstance object);

    /**
     * Removes given object.
     */
    void removeObject(ObjectInstance object);

    /**
     * Returns object registered with given id.
     *
     * @throws simplerogue.engine.object.ObjectException If object with given id was not found.
     */
    <T extends ObjectInstance> T getObject(int id);

    /**
     * Returns all registered objects.
     */
    List<ObjectInstance> getAllObjects();

    /**
     * Returns objects registered with given ids.
     *
     * @throws simplerogue.engine.object.ObjectException If object with given id was not found.
     */
    <T extends ObjectInstance> List<T> getObjects(List<Integer> ids);

    <T extends ObjectInstance> List<T> lookupObjects(Class<T> type);

    /**
     * Creates ObjectInstance of given prototype. Registers created object.
     */
    <T extends ObjectInstance> T createObject(String prototype);

    /**
     * Creates ObjectInstance of given prototype.
     *
     * @param register If new object should be registered.
     */
    <T extends ObjectInstance> T createObject(String prototype, boolean register);

    /**
     * Creates {@link Field} from given fieldId. Returns cached instance if field is cacheable.
     * Doesn't register new field.
     *
     * @see simplerogue.engine.object.Prototype#isCached()
     * @see simplerogue.engine.level.Field
     */
    <T extends Field> T createField(int fieldId);
}
