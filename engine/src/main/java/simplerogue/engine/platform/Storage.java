package simplerogue.engine.platform;

import java.util.List;

/**
 * Persistent data storage.
 *
 * @author Adam Wyłuda
 */
public interface Storage
{
    /**
     * Creates file/directory at given path, does nothing if directory already exists.
     */
    void createFile(String path);

    /**
     * Reads file at given path to string.
     */
    String readFileToString(String path);

    /**
     * Reads file at given path to int array.
     */
    int[] readFileToInts(String path);

    /**
     * Writes string to file. Creates the file if necessary.
     */
    void writeStringToFile(String path, String content);

    /**
     * Writes int array to file. Creates the file if necessary.
     */
    void writeIntsToFile(String path, int[] content);

    /**
     * @return List of file names relative to given path.
     */
    List<String> listFiles(String path);

    void deleteFile(String path);
}
