package simplerogue.engine.manager;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global registry of managers. Provides unified access method to singleton services. <p/>
 *
 * <b>Not thread safe! Must be initiated once from a single thread.</b>
 *
 * @author Adam Wyłuda
 */
public class Managers
{
    private static final Map<Class<? extends Manager>, Manager> managerMap = new HashMap<>();
    private static final Logger LOG = LoggerFactory.getLogger(Managers.class);

    /**
     * Binds given type to concrete instance.
     */
    public static <T extends Manager> void register(Class<T> clazz, T manager)
    {
        LOG.debug("Binding {} to {}", clazz.getCanonicalName(), manager.getClass().getCanonicalName());
        managerMap.put(clazz, manager);
    }

    /**
     * @return Singleton instance of the given type.
     */
    public static <T extends Manager> T get(Class<T> clazz)
    {
        return (T) managerMap.get(clazz);
    }

    /**
     * @return All managers assignable from given type.
     */
    public static <T> Iterable<T> lookup(Class<T> clazz)
    {
        List<T> result = Lists.newLinkedList();

        for (Manager manager : managerMap.values())
        {
            if (clazz.isAssignableFrom(manager.getClass()))
            {
                result.add((T) manager);
            }
        }

        return result;
    }

    /**
     * @return True, if there is a manager registered to given class.
     */
    public static boolean has(Class<? extends Manager> clazz)
    {
        return managerMap.containsKey(clazz);
    }

    /**
     * Executes {@link Manager#init()} for every registered manager. It should be called only once and only after
     * all managers have been registered.
     */
    public static void initAll()
    {
        LOG.info("Initiating all managers");

        for (Map.Entry<?, Manager> entry : managerMap.entrySet())
        {
            Manager manager = entry.getValue();

            LOG.debug("Initiating {}", manager.getClass().getCanonicalName());
            manager.init();
        }
    }
}
