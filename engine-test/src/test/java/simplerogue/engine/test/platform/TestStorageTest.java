package simplerogue.engine.test.platform;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Adam Wyłuda
 */
public class TestStorageTest
{
    @Test
    public void testCreateFiles()
    {
        TestStorage testStorage = new TestStorage();

        List<String> children;

        children = testStorage.listFiles("/x/y/");
        assertTrue(children.isEmpty());

        testStorage.createFile("/x/y/z");
        testStorage.createFile("/x/y/w/");

        children = testStorage.listFiles("/x/");
        assertEquals(1, children.size());
        assertThat(children, hasItem("y/"));

        children = testStorage.listFiles("/x/y/");
        assertEquals(2, children.size());
        assertThat(children, hasItem("z"));
        assertThat(children, hasItem("w/"));

        testStorage.writeIntsToFile("/x/y/abc.map", new int[]{1, 2, 3});

        children = testStorage.listFiles("/x/y/");
        assertEquals(3, children.size());
        assertThat(children, hasItem("abc.map"));

        testStorage.writeStringToFile("/x/y/d/e/f.txt", "some text");

        children = testStorage.listFiles("/x/y/");
        assertEquals(4, children.size());
        assertThat(children, hasItem("d/"));
    }

    @Test
    public void testWriteAndReadFiles()
    {
        TestStorage testStorage = new TestStorage();

        testStorage.writeStringToFile("/a/b.txt", "hello");
        assertThat(testStorage.listFiles("/a/"), hasItem("b.txt"));

        String string = testStorage.readFileToString("/a/b.txt");
        assertEquals("hello", string);

        testStorage.writeIntsToFile("/a/c.map", new int[]{1, 2, 3});
        assertThat(testStorage.listFiles("/a/"), hasItem("c.map"));

        int[] ints = testStorage.readFileToInts("/a/c.map");
        assertArrayEquals(new int[]{1, 2, 3}, ints);
    }
}