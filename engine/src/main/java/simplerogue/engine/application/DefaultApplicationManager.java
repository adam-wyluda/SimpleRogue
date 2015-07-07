package simplerogue.engine.application;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.manager.AbstractManager;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author Adam Wyłuda
 */
public class DefaultApplicationManager extends AbstractManager implements ApplicationManager
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultApplicationManager.class);

    private static final String METADATA_XML_PATH = "/metadata.xml";
    private static final String VERSION_KEY = "engine.version";

    @Getter
    private Version engineVersion;

    @Override
    public void init()
    {
        Map<String, String> metadata = loadMetadata();

        try
        {
            engineVersion = Version.fromString(metadata.get(VERSION_KEY));
        }
        catch (Exception ex)
        {
            engineVersion = Version.fromString("0");
        }
    }

    private Map<String, String> loadMetadata()
    {
        Properties properties = new Properties();
        try
        {
            properties.loadFromXML(getClass().getResourceAsStream(METADATA_XML_PATH));
        }
        catch (IOException e)
        {
            LOG.error("Error while loading engine metadata");
            throw new RuntimeException(e);
        }
        return Maps.fromProperties(properties);
    }

    @Override
    public void exit()
    {
        LOG.info("Terminating application");

        System.exit(0);
    }
}
