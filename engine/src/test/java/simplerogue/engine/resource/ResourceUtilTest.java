package simplerogue.engine.resource;

import org.junit.Test;
import simplerogue.engine.resource.ResourceUtil;

import static org.junit.Assert.assertEquals;

/**
 * @author Adam Wyłuda
 */
public class ResourceUtilTest
{
    @Test
    public void testGetNameFromPath() throws Exception
    {
        assertEquals("abc", ResourceUtil.getNameFromPath("/x/zc/abc"));
        assertEquals("abc", ResourceUtil.getNameFromPath("/x/zc/abc/"));
    }

    @Test
    public void testGetParentPath() throws Exception
    {
        assertEquals("/x/zc/", ResourceUtil.getParentPath("/x/zc/abc"));
        assertEquals("/x/zc/", ResourceUtil.getParentPath("/x/zc/abc/"));
    }
}