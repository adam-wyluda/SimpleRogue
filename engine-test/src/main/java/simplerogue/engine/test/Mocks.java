package simplerogue.engine.test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import simplerogue.engine.resource.ResourceUtil;
import simplerogue.engine.object.Reifiable;
import simplerogue.engine.resource.Resource;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Mock utilities.
 *
 * @author Adam Wyłuda
 */
public class Mocks
{
    private Mocks()
    {
    }

    public static <T extends Reifiable> T reifiable(final Class<T> type)
    {
        final T mock = mock(type);

        when(mock.is(any(Class.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                Class<?> argument = (Class<?>) invocation.getArguments()[0];
                boolean result = type.isAssignableFrom(argument);

                return result;
            }
        });

        when(mock.reify(any(Class.class))).then(new Answer<Object>()
        {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable
            {
                Class<?> argument = (Class<?>) invocation.getArguments()[0];

                return argument.cast(mock);
            }
        });

        return mock;
    }

    public static <T extends Resource> T resource(Class<T> type, String path)
    {
        T mock = reifiable(type);

        when(mock.getPath()).thenReturn(path);
        when(mock.getName()).thenReturn(ResourceUtil.getNameFromPath(path));

        return mock;
    }
}
