package simplerogue.engine.resource;

import simplerogue.engine.object.Model;

import java.util.List;

/**
 * Represents tree-like ({@link simplerogue.engine.object.Model}) structure in file system tree.
 *
 * @author Adam Wyłuda
 */
public interface TreeResource extends FileResource
{
    /**
     * Parses file resource to single {@link simplerogue.engine.object.Model}.
     */
    Model readModel();

    /**
     * Reads file as array of models.
     */
    List<Model> readModelArray();

    /**
     * Saves {@link simplerogue.engine.object.Model} to file.
     */
    void saveModel(Model model);

    /**
     * Saves {@link simplerogue.engine.object.Model} to file.
     */
    void saveModel(List<Model> array);
}
