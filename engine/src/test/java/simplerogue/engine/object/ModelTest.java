package simplerogue.engine.object;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Adam Wyłuda
 */
public class ModelTest
{
    // TODO Test Model.getModels()

    @Test
    public void testCasting()
    {
        Model model = new Model();

        model.put("my_string", "value");
        model.put("my_int", 23);
        model.put("my_int_str", "45");
        model.put("my_enum", TestEnum.DEF);
        model.put("my_enum_str", "GHI");

        assertEquals("value", model.getString("my_string"));
        assertEquals("23", model.getString("my_int"));

        assertEquals(23, model.getInt("my_int"));
        assertEquals(45, model.getInt("my_int_str"));

        assertEquals("DEF", model.getString("my_enum"));
        assertEquals(TestEnum.DEF, model.getEnum(TestEnum.class, "my_enum"));

        assertEquals(TestEnum.GHI, model.getEnum(TestEnum.class, "my_enum_str"));
    }

    @Test
    public void testClone()
    {
        Model origin = new Model();
        Model modelInOrigin = new Model();

        origin.put("my_model", modelInOrigin);
        modelInOrigin.put("my_val", 500);

        Model clone = new Model(origin);
        Model modelInClone = clone.getModel("my_model");

        assertEquals(500, modelInClone.getInt("my_val"));
        assertFalse(modelInClone == modelInOrigin);

        modelInClone.put("my_val", 1000);

        assertEquals(500, modelInOrigin.getInt("my_val"));
    }

    @Test
    public void testOrder()
    {
        Model model = new Model();

        for (int i = 0; i < 50; i++)
        {
            model.put("a" + i, i);
        }

        model.put("a20", 1000);

        // Values should be copied in the same order
        Model copy = new Model(model);

        List<Pair<String, Object>> pairs = copy.getProperties();

        assertEquals(50, pairs.size());

        assertEquals(1000, pairs.get(20).getValue());
        for (int i = 1; i < 50; i++)
        {
            if (i == 20)
            {
                continue;
            }

            int value = model.getInt("a" + i);
            assertEquals(i, value);
        }
    }

    @Test
    public void testRemove()
    {
        Model model = new Model();

        model.put("1", 1);
        model.put("2", 2);

        model.remove("1");

        assertEquals(1, model.getProperties().size());
        assertNull(model.get("1"));
    }

    @Test
    public void testMerge()
    {
        Model a = new Model();
        Model a_a = new Model();
        Model a_b = new Model();
        Model a_b_a = new Model();

        a.put("immutable", 25);
        a.put("my_val", "hello");
        a.put("a", a_a);
        a.put("b", a_b);

        a_a.put("sth", "1500");

        a_b.put("sth", 3000);
        a_b.put("a", a_b_a);

        a_b_a.put("deep", "val");

        Model m_a = new Model();
        Model m_a_b = new Model();
        Model m_a_b_a = new Model();

        m_a.put("my_val", "goodbye");
        m_a.put("b", m_a_b);

        m_a_b.put("a", m_a_b_a);

        m_a_b_a.put("deep", "depp");
        m_a_b_a.put("new", "property");

        Model r_a = a.merge(m_a);
        Model r_a_a = r_a.getModel("a");
        Model r_a_b = r_a.getModel("b");
        Model r_a_b_a = r_a_b.getModel("a");

        // Test merged model
        assertEquals(25, r_a.getInt("immutable"));
        assertEquals("goodbye", r_a.getString("my_val"));
        assertEquals(1500, r_a_a.getInt("sth"));
        assertEquals(3000, r_a_b.getInt("sth"));
        assertEquals("depp", r_a_b_a.getString("deep"));
        assertEquals("property", r_a_b_a.getString("new"));

        m_a.put("immutable", 1000);
        r_a.put("immutable", 2000);

        // Check if old model didn't change
        assertEquals(25, a.getInt("immutable"));
        assertEquals("hello", a.getString("my_val"));
        assertEquals(1500, a_a.getInt("sth"));
        assertEquals(3000, a_b.getInt("sth"));
        assertEquals("val", a_b_a.getString("deep"));
    }

    @Test
    public void testDiff()
    {
        Model a = new Model();
        Model a_a = new Model();
        Model a_b = new Model();
        Model a_b_a = new Model();

        a.put("immutable", 25);
        a.put("my_val", "hello");
        a.put("a", a_a);
        a.put("b", a_b);

        a_a.put("sth", "1500");

        a_b.put("sth", 3000);
        a_b.put("a", a_b_a);

        a_b_a.put("deep", "val");

        Model m_a = new Model();
        Model m_a_a = new Model();
        Model m_a_b = new Model();
        Model m_a_b_a = new Model();

        m_a.put("immutable", 25);
        m_a.put("my_val", "xyz");
        m_a.put("a", m_a_a);
        m_a.put("b", m_a_b);

        m_a_a.put("sth", "1500");

        m_a_b.put("sth", 3000);
        m_a_b.put("a", m_a_b_a);

        m_a_b_a.put("deep", "other_val");

        Model r_a = a.diff(m_a);
        Model r_a_b = r_a.getModel("b");
        Model r_a_b_a = r_a_b.getModel("a");

        // Test diffed model
        assertFalse(r_a.has("immutable"));
        assertEquals("hello", r_a.getString("my_val"));
        assertFalse(r_a.has("a"));
        assertFalse(r_a_b.has("sth"));
        assertEquals("val", r_a_b_a.getString("deep"));

        m_a.put("immutable", 1000);
        r_a.put("immutable", 2000);

        // Check if old model didn't change
        assertEquals(25, a.getInt("immutable"));
        assertEquals("hello", a.getString("my_val"));
        assertEquals(1500, a_a.getInt("sth"));
        assertEquals(3000, a_b.getInt("sth"));
        assertEquals("val", a_b_a.getString("deep"));
    }
}
