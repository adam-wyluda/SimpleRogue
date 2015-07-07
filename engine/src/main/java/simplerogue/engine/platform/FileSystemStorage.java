package simplerogue.engine.platform;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import simplerogue.engine.resource.ResourceUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class FileSystemStorage implements Storage
{
    private File gameHome;

    public FileSystemStorage(File gameHome)
    {
        this.gameHome = gameHome;
    }

    @Override
    public void createFile(String path)
    {
        File gameFile = getGameFile(path);

        try
        {
            gameFile.mkdirs();
            gameFile.createNewFile();
        }
        catch (IOException e)
        {
            // TODO Handle exceptions properly
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readFileToString(String path)
    {
        try
        {
            return ResourceUtil.readStreamAsString(getInputStream(path));
        }
        catch (IOException e)
        {
            // TODO Handle exceptions properly
            throw new RuntimeException(e);
        }
    }

    @Override
    public int[] readFileToInts(String path)
    {
        try
        {
            return ResourceUtil.readStreamAsIntArray(getInputStream(path));
        }
        catch (IOException e)
        {
            // TODO Handle exceptions properly
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeStringToFile(String path, String content)
    {
        try
        {
            IOUtils.write(content, getFileOutputStream(path));
        }
        catch (IOException e)
        {
            // TODO Handle exceptions properly
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeIntsToFile(String path, int[] content)
    {
        try
        {
            ResourceUtil.writeIntArrayToStream(new DataOutputStream(getFileOutputStream(path)), content);
        }
        catch (IOException e)
        {
            // TODO Handle exceptions properly
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> listFiles(String path)
    {
        File directory = getGameFile(path);
        List<String> files = Lists.newArrayList();

        List<String> filesInFileSystem = getFilesInFileSystem(directory);
        List<String> filesInResources = getFilesInResources(path);

        files.addAll(filesInFileSystem);
        files.addAll(filesInResources);

        return files;
    }

    @Override
    public void deleteFile(String path)
    {
        File file = getGameFile(path);
        try
        {
            FileUtils.forceDelete(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private List<String> getFilesInFileSystem(File directory)
    {
        List<String> result = Lists.newArrayList();

        if (directory.exists() && directory.isDirectory())
        {
            for (File file : directory.listFiles())
            {
                String name = file.isDirectory() ? file.getName() + "/" : file.getName();

                result.add(name);
            }
        }

        return result;
    }

    private List<String> getFilesInResources(String path)
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

    private OutputStream getFileOutputStream(String path) throws FileNotFoundException
    {
        File file = getGameFile(path);
        FileOutputStream stream = new FileOutputStream(file);

        return stream;
    }

    private InputStream getInputStream(String path)
    {
        InputStream stream = null;

        try
        {
            stream = getFileInputStream(path);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        if (stream == null)
        {
            stream = getClass().getResourceAsStream(path);
        }

        return stream;
    }

    private InputStream getFileInputStream(String path) throws FileNotFoundException
    {
        File file = getGameFile(path);
        FileInputStream stream = null;

        if (file.exists())
        {
            stream = new FileInputStream(file);
        }

        return stream;
    }

    private File getGameFile(String path)
    {
        File gameFile = new File(gameHome, path);

        return gameFile;
    }
}
