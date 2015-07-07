package simplerogue.engine.resource.json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import simplerogue.engine.object.Model;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class ModelJSONTransformer
{
    private ModelJSONTransformer()
    {
    }

    public static List<Model> toModelList(String jsonSource)
    {
        JsonArray jsonArray = JsonArray.readFrom(jsonSource);

        List<Model> result = toModelList(jsonArray);
        return result;
    }

    public static Model toModel(String jsonSource)
    {
        JsonObject jsonObject = JsonObject.readFrom(jsonSource);

        Model result = toModel(jsonObject);
        return result;
    }

    private static Model toModel(JsonObject jsonObject)
    {
        Model model = new Model();

        for (JsonObject.Member member : jsonObject)
        {
            Object transformed = transformJsonObject(member.getValue());

            model.put(member.getName(), transformed);
        }

        return model;
    }

    private static List<Model> toModelList(JsonArray jsonArray)
    {
        List<Model> models = Lists.newArrayListWithCapacity(jsonArray.size());

        for (JsonValue value : jsonArray)
        {
            if (value instanceof JsonObject)
            {
                JsonObject jsonObject = (JsonObject) value;
                Model model = toModel(jsonObject);

                models.add(model);
            }
            else
            {
                throw new IllegalArgumentException("Unsupported JSON construct");
            }
        }

        return models;
    }

    private static Object transformJsonObject(JsonValue jsonValue)
    {
        Object result;

        if (jsonValue.isNumber())
        {
            // No way to tell if it's integer
            result = jsonValue.asDouble();
        }
        else if (jsonValue.isBoolean())
        {
            result = jsonValue.asBoolean();
        }
        else if (jsonValue.isString())
        {
            result = jsonValue.asString();
        }
        else if (jsonValue.isObject())
        {
            JsonObject jsonObject = jsonValue.asObject();
            result = toModel(jsonObject);
        }
        else if (jsonValue.isArray())
        {
            JsonArray jsonArray = jsonValue.asArray();
            result = transformJsonArray(jsonArray);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported JSON object type");
        }

        return result;
    }

    private static List<?> transformJsonArray(JsonArray jsonArray)
    {
        List<Object> result = Lists.newArrayListWithCapacity(jsonArray.size());

        for (JsonValue jsonValue : jsonArray)
        {
            Object transformed = transformJsonObject(jsonValue);

            result.add(transformed);
        }

        return result;
    }

    public static String toJson(Model model)
    {
        JsonObject jsonObject = toJsonObject(model);
        return jsonObject.toString();
    }

    public static String toJson(List<Model> list)
    {
        JsonArray jsonArray = new JsonArray();

        for (Model model : list)
        {

            JsonObject jsonObject = toJsonObject(model);
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }

    private static JsonObject toJsonObject(Model model)
    {
        JsonObject result = new JsonObject();

        for (Pair<String, Object> pair : model.getProperties())
        {
            String name = pair.getKey();
            Object value = pair.getValue();

            addToObject(result, name, value);
        }

        return result;
    }

    private static void addToObject(JsonObject jsonObject, String name, Object value)
    {
        if (value instanceof Boolean)
        {
            jsonObject.add(name, (boolean) value);
        }
        else if (value instanceof Double || value instanceof Float)
        {
            jsonObject.add(name, (double) value);
        }
        else if (value instanceof Integer)
        {
            jsonObject.add(name, (int) value);
        }
        else if (value instanceof Long)
        {
            jsonObject.add(name, (long) value);
        }
        else if (value instanceof String || value instanceof Enum)
        {
            jsonObject.add(name, value.toString());
        }
        else if (value instanceof Model)
        {
            Model model = (Model) value;
            JsonObject newJsonObject = toJsonObject(model);

            jsonObject.add(name, newJsonObject);
        }
        else if (value instanceof Iterable)
        {
            JsonArray newJsonArray = new JsonArray();

            for (Object element : (Iterable<?>) value)
            {
                addToArray(newJsonArray, element);
            }

            jsonObject.add(name, newJsonArray);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported JSON object type: " + value.getClass().getName());
        }
    }

    private static void addToArray(JsonArray jsonArray, Object value)
    {
        if (value instanceof Boolean)
        {
            jsonArray.add((boolean) value);
        }
        else if (value instanceof Double || value instanceof Float)
        {
            jsonArray.add((double) value);
        }
        else if (value instanceof Integer)
        {
            jsonArray.add((int) value);
        }
        else if (value instanceof Long)
        {
            jsonArray.add((long) value);
        }
        else if (value instanceof String || value instanceof Enum)
        {
            jsonArray.add(value.toString());
        }
        else if (value instanceof Model)
        {
            Model model = (Model) value;
            JsonObject newJsonObject = toJsonObject(model);

            jsonArray.add(newJsonObject);
        }
        else if (value instanceof Iterable)
        {
            JsonArray newJsonArray = new JsonArray();

            for (Object element : (Iterable<?>) value)
            {
                addToArray(newJsonArray, element);
            }

            jsonArray.add(newJsonArray);
        }
        else
        {
            throw new IllegalArgumentException("Unsupported JSON object type: " + value.getClass().getName());
        }
    }
}
