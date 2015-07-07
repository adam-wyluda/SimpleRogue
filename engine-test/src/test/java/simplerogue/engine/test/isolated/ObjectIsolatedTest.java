package simplerogue.engine.test.isolated;

import com.google.common.collect.Sets;
import org.junit.Test;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.DefaultObjectManager;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectException;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.test.IsolatedTest;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Adam Wyłuda
 */
public class ObjectIsolatedTest extends IsolatedTest
{
    private ObjectManager objectManager;

    @Override
    protected void beforeInit()
    {
        super.beforeInit();

        objectManager = new DefaultObjectManager();
        Managers.register(ObjectManager.class, objectManager);
    }

    @Test
    public void testGenerateIdIsUnique()
    {
        Set<Integer> ids = Sets.newTreeSet();

        while (ids.size() < 1000)
        {
            int newId = objectManager.generateId();

            assertThat("newId is not unique", ids, not(hasItem(newId)));

            ids.add(newId);
        }
    }

    @Test
    public void testRegisterPrototype()
    {
        Prototype prototype = Prototype.create(new Model());
        prototype.setName("abc");

        objectManager.registerPrototype(prototype);

        Prototype samePrototype = objectManager.getPrototype("abc");
        assertEquals(prototype, samePrototype);
    }

    @Test(expected = ObjectException.class)
    public void testNonExistentPrototype()
    {
        // Should throw ObjectException
        objectManager.getPrototype("abc");
    }

    @Test(expected = ObjectException.class)
    public void testNonExistentObjectInstance()
    {
        // Should throw ObjectException
        objectManager.getObject(objectManager.generateId());
    }
}
