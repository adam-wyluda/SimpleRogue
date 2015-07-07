package simplerogue.engine.application;

import com.google.common.base.Splitter;
import lombok.Data;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
public class Version
{
    private int major;
    private int minor;
    private String ending = "";
    private String fullString;

    public static Version fromString(String string)
    {
        Version version = new Version();
        version.setFullString(string);

        List<String> minusSplit = Splitter.on("-").limit(2).splitToList(string);
        version.setEnding(minusSplit.size() == 2 ? minusSplit.get(1) : "");

        List<String> dotSplit = Splitter.on(".").splitToList(minusSplit.get(0));
        version.setMajor(Integer.parseInt(dotSplit.get(0)));
        version.setMinor(dotSplit.size() >= 2 ? Integer.parseInt(dotSplit.get(1)) : 0);

        return version;
    }

    public boolean isSnapshot()
    {
        return ending.equals("SNAPSHOT");
    }

    /**
     * Returns true if this version is higher.
     */
    public boolean isHigherThan(Version second)
    {
        if (getMajor() > second.getMajor())
        {
            return true;
        }
        if (getMajor() < second.getMajor())
        {
            return false;
        }

        // Majors must be equal now
        if (getMinor() > second.getMinor())
        {
            return true;
        }
        if (getMinor() < second.getMinor())
        {
            return false;
        }

        // When major and minors are equal then snapshot is lower
        if (!isSnapshot() && second.isSnapshot())
        {
            return true;
        }
        if (isSnapshot() && !second.isSnapshot())
        {
            return false;
        }

        // It's hard to determine which endings (besides SNAPSHOT) mean higher version so we return false
        return false;
    }
}
