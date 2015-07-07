package simplerogue.engine.application;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Adam Wyłuda
 */
public class VersionTest
{
    @Test
    public void testFromString()
    {
        Version version = Version.fromString("1.2-SNAPSHOT");
        assertEquals(1, version.getMajor());
        assertEquals(2, version.getMinor());
        assertEquals("SNAPSHOT", version.getEnding());
        assertTrue(version.isSnapshot());

        version = Version.fromString("4.5.3");
        assertEquals(4, version.getMajor());
        assertEquals(5, version.getMinor());
        assertEquals("", version.getEnding());
        assertFalse(version.isSnapshot());

        version = Version.fromString("1-alpha");
        assertEquals(1, version.getMajor());
        assertEquals(0, version.getMinor());
        assertEquals("alpha", version.getEnding());
        assertFalse(version.isSnapshot());
    }

    @Test
    public void testIsHigherThan()
    {
        assertTrue(v("2.0").isHigherThan(v("1.0")));
        assertTrue(v("1.1").isHigherThan(v("1.0")));
        assertTrue(v("1.0").isHigherThan(v("1.0-SNAPSHOT")));
        assertTrue(v("2").isHigherThan(v("1.5")));
        assertTrue(v("2-SNAPSHOT").isHigherThan(v("1.5-gamma")));

        assertFalse(v("1.0").isHigherThan(v("1.0")));
        assertFalse(v("1.0-SNAPSHOT").isHigherThan(v("1.0-SNAPSHOT")));
        assertFalse(v("1.0-alpha").isHigherThan(v("1.0-beta")));
        assertFalse(v("1.0-beta").isHigherThan(v("1.0-alpha")));
    }

    // For better test readability
    private Version v(String string)
    {
        return Version.fromString(string);
    }
}
