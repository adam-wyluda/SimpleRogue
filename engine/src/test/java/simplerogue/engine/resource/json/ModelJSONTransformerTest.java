package simplerogue.engine.resource.json;

import com.google.common.collect.Lists;
import org.junit.Test;
import simplerogue.engine.object.Model;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Adam Wyłuda
 */
public class ModelJSONTransformerTest
{
    private static final String SOURCE_SINGLE = "" +
            "{\n" +
            "    \"property\" : \"value\",\n" +
            "    \"number\" : 150,\n" +
            "    \"boolean\" : true,\n" +
            "    \"object\" : {\n" +
            "        \"a\" : \"b\"\n" +
            "    },\n" +
            "    \"string_array\" : [\n" +
            "        \"a\", \"b\"\n" +
            "    ],\n" +
            "    \"int_array\" : [\n" +
            "        1, 2, 3\n" +
            "    ],\n" +
            "    \"object_array\" : [\n" +
            "        {\n" +
            "            \"prop\" : \"val\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"prop\" : \"xyz\"\n" +
            "        }\n" +
            "    ]\n" +
            "}\n";

    private static final String SOURCE_MULTI = "" +
            "[\n" +
            "    {\n" +
            "        \"x\" : \"y\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"x\" : \"z\"\n" +
            "    }\n" +
            "]\n";

    @Test
    public void testToModelSingle()
    {
        Model result = ModelJSONTransformer.toModel(SOURCE_SINGLE);

        assertEquals("value", result.getString("property"));
        assertEquals(150, result.getInt("number"));
        assertEquals(true, result.getBoolean("boolean"));

        Model object = result.getModel("object");
        assertEquals("b", object.getString("a"));

        List<String> stringArray = result.getStrings("string_array");
        assertEquals(2, stringArray.size());
        assertEquals("a", stringArray.get(0));
        assertEquals("b", stringArray.get(1));

        List<Integer> intArray = result.getInts("int_array");
        assertEquals(3, intArray.size());
        assertEquals((Integer) 1, intArray.get(0));
        assertEquals((Integer) 2, intArray.get(1));
        assertEquals((Integer) 3, intArray.get(2));

        List<Model> objectArray = result.get("object_array");
        assertEquals(2, objectArray.size());
        assertEquals("val", objectArray.get(0).getString("prop"));
        assertEquals("xyz", objectArray.get(1).getString("prop"));
    }

    @Test
    public void testToModelMulti()
    {
        List<Model> result = ModelJSONTransformer.toModelList(SOURCE_MULTI);
        assertEquals(2, result.size());

        Model model1 = result.get(0);
        assertEquals("y", model1.getString("x"));
        assertEquals(1, model1.getProperties().size());

        Model model2 = result.get(1);
        assertEquals("z", model2.getString("x"));
        assertEquals(1, model2.getProperties().size());
    }

    @Test
    public void testToJsonSingle()
    {
        Model root = new Model();
        root.put("property", "value");
        root.put("number", 150);
        root.put("boolean", true);

        Model object = new Model();
        object.put("a", "b");
        root.put("object", object);

        List<String> strings = Lists.newArrayList("a", "b");
        root.put("string_array", strings);

        List<Integer> ints = Lists.newArrayList(1, 2, 3);
        root.put("int_array", ints);

        Model model1 = new Model();
        model1.put("prop", "val");
        Model model2 = new Model();
        model2.put("prop", "xyz");
        List<Model> models = Lists.newArrayList(model1, model2);
        root.put("object_array", models);

        String result = ModelJSONTransformer.toJson(root);
        assertEquals(removeWhiteSpace(SOURCE_SINGLE.trim()), removeWhiteSpace(result.trim()));
    }

    @Test
    public void testToJsonMultiple()
    {
        Model model1 = new Model();
        model1.put("x", "y");

        Model model2 = new Model();
        model2.put("x", "z");

        List<Model> models = Lists.newArrayList(model1, model2);

        String result = ModelJSONTransformer.toJson(models);
        assertEquals(removeWhiteSpace(SOURCE_MULTI), removeWhiteSpace(result.trim()));
    }

    private static String removeWhiteSpace(String string)
    {
        return string.replaceAll("\\s+","");
    }
}
