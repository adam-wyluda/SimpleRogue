package simplerogue.engine.resource;

import simplerogue.engine.resource.json.ModelJSONTransformer;
import simplerogue.engine.object.Model;
import simplerogue.engine.platform.Storage;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class JSONTreeResource extends DefaultFileResource implements TreeResource
{
    public JSONTreeResource(String path, Storage storage)
    {
        super(path, storage);
    }

    @Override
    public Model readModel()
    {
        String source = storage.readFileToString(getPath());
        Model model = ModelJSONTransformer.toModel(source);

        return model;
    }

    @Override
    public List<Model> readModelArray()
    {
        String source = storage.readFileToString(getPath());
        List<Model> array = ModelJSONTransformer.toModelList(source);

        return array;
    }

    @Override
    public void saveModel(Model model)
    {
        String jsonSource = ModelJSONTransformer.toJson(model);
        storage.writeStringToFile(getPath(), jsonSource);
    }

    @Override
    public void saveModel(List<Model> array)
    {
        String jsonSource = ModelJSONTransformer.toJson(array);
        storage.writeStringToFile(getPath(), jsonSource);
    }
}
