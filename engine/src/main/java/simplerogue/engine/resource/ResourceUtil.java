package simplerogue.engine.resource;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class ResourceUtil
{
    public static String getNameFromPath(String path)
    {
        path = path.trim();

        String[] split = path.split("/");
        String name = split[split.length - 1];

        return name;
    }

    public static String getParentPath(String path)
    {
        String name = getNameFromPath(path);
        int nameIndex = path.lastIndexOf(name);

        String parent = path.substring(0, nameIndex);

        return parent;
    }

    public static boolean isDirectory(String path)
    {
        return path.endsWith("/");
    }

    public static String readStreamAsString(InputStream stream) throws IOException
    {
        return IOUtils.toString(stream);
    }

    public static int[] readStreamAsIntArray(InputStream stream) throws IOException
    {
        List<Integer> ints = Lists.newArrayList();
        DataInputStream dataStream = new DataInputStream(stream);

        while (dataStream.available() > 0)
        {
            int value = dataStream.readInt();
            ints.add(value);
        }

        int[] result = Ints.toArray(ints);
        return result;
    }

    public static void writeIntArrayToStream(DataOutputStream stream, int[] ints) throws IOException
    {
        for (int datum : ints)
        {
            stream.writeInt(datum);
        }
    }
}
