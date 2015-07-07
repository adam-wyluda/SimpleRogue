package simplerogue.engine.view;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author Adam Wyłuda
 */
public class CharArrayTest
{
    @Test
    public void testFromFormattedString()
    {
        String string = "ABC @(RED|GREEN)123 @(|30,40,50)DEF";

        CharArray array = CharArray.fromFormattedString(string);

        assertEquals(11, array.getArray().length);

        Char sample1 = array.getArray()[0];
        Char sample2 = array.getArray()[5];
        Char sample3 = array.getArray()[9];

        assertEquals('A', sample1.getCharacter());
        assertEquals(Color.WHITE, sample1.getColor());
        assertEquals(Color.BLACK, sample1.getBackgroundColor());

        assertEquals('2', sample2.getCharacter());
        assertEquals(Color.RED, sample2.getColor());
        assertEquals(Color.GREEN, sample2.getBackgroundColor());

        assertEquals('E', sample3.getCharacter());
        assertEquals(Color.RED, sample3.getColor());
        assertEquals(Color.create(30, 40, 50), sample3.getBackgroundColor());
    }

    @Test
    public void testSplit()
    {
        String string = "ABC @(RED|GREEN)123 @(|30,40,50)DEF";

        CharArray array = CharArray.fromFormattedString(string);
        CharArray[] split = array.split(4);

        assertEquals(3, split.length);
        assertEquals(4, split[0].length());
        assertEquals(4, split[1].length());
        assertEquals(3, split[2].length());
    }
}
