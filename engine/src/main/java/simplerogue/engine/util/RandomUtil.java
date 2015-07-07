package simplerogue.engine.util;

import java.util.List;
import java.util.Random;

/**
 * @author Adam Wyłuda
 */
public class RandomUtil
{
    private static final Random RANDOM = new Random();

    public static int randomInt(int max)
    {
        return RANDOM.nextInt(max);
    }

    public static int randomInt(int min, int max)
    {
        if (min == max)
        {
            return max;
        }

        return RANDOM.nextInt(max - min) + min;
    }

    public static boolean randomBoolean()
    {
        return RANDOM.nextBoolean();
    }

    public static boolean randomBoolean(double chance)
    {
        return RANDOM.nextDouble() < chance;
    }

    public static <T> T randomElement(List<T> list)
    {
        int index = randomInt(list.size());

        T result = list.get(index);

        return result;
    }

    public static <T> T randomElement(T[] array)
    {
        return array[randomInt(array.length)];
    }
}
