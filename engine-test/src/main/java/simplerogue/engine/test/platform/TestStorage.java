package simplerogue.engine.test.platform;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import simplerogue.engine.resource.ResourceUtil;
import simplerogue.engine.platform.Storage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Virtual (in-memory) storage for tests.
 *
 * @author Adam Wyłuda
 */
public class TestStorage implements Storage
{
    /**
     * Virtual directories. Maps full path to its children (relative generator).
     */
    private Multimap<String, String> directories = HashMultimap.create();

    // Temporary files
    private Map<String, String> storedStringResources = Maps.newHashMap();
    private Map<String, int[]> storedIntResources = Maps.newHashMap();

    @Override
    public void createFile(String path)
    {
        if (!ResourceUtil.isDirectory(path))
        {
            storedStringResources.put(path, "");
            storedIntResources.put(path, new int[0]);
        }

        createPath(path);
    }

    @Override
    public String readFileToString(String path)
    {
        if (storedStringResources.containsKey(path))
        {
            return storedStringResources.get(path);
        }

        try
        {
            return ResourceUtil.readStreamAsString(getClass().getResourceAsStream(path));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] readFileToInts(String path)
    {
        if (storedIntResources.containsKey(path))
        {
            return storedIntResources.get(path);
        }

        try
        {
            return ResourceUtil.readStreamAsIntArray(getClass().getResourceAsStream(path));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeStringToFile(String path, String content)
    {
        createPath(path);
        storedStringResources.put(path, content);
    }

    @Override
    public void writeIntsToFile(String path, int[] content)
    {
        createPath(path);
        storedIntResources.put(path, content);
    }

    @Override
    public List<String> listFiles(String path)
    {
        List<String> result = Lists.newArrayList();

        result.addAll(getRealResources(path));
        result.addAll(getVirtualDirectories(path));

        return result;
    }

    @Override
    public void deleteFile(String path)
    {

    }

    private List<String> getRealResources(String path)
    {
        List<String> result = Lists.newArrayList();

        URL resourceUrl = getClass().getResource(path);

        if (resourceUrl != null)
        {
            File resourceFile = new File(resourceUrl.getFile());

            if (resourceFile != null && resourceFile.exists())
            {
                for (File file : resourceFile.listFiles())
                {
                    String name = file.getName() + (file.isDirectory() ? "/" : "");
                    result.add(name);
                }
            }
        }

        return result;
    }

    private List<String> getVirtualDirectories(String path)
    {
        List<String> result = Lists.newArrayList();

        for (String dir : directories.get(path))
        {
            result.add(dir);
        }

        return result;
    }

    private void createPath(String path)
    {
        String[] split = path.split("/");

        StringBuilder incrementalPath = new StringBuilder("/");
        boolean isDirectory = ResourceUtil.isDirectory(path);

        for (int i = 0; i < split.length; i++)
        {
            String dir = split[i];

            if (dir.trim().isEmpty())
            {
                continue;
            }

            if (i != split.length - 1 || (i == split.length - 1 && isDirectory))
            {
                // If it's not the last element then put it as a directory
                dir += "/";
                directories.put(incrementalPath.toString(), dir);
                incrementalPath.append(dir);
            }
            else
            {
                directories.put(incrementalPath.toString(), dir);
                incrementalPath.append(dir + "/");
            }
        }
    }
}
