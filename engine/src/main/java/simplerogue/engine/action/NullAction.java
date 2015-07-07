package simplerogue.engine.action;

import simplerogue.engine.object.ObjectInstance;

/**
 * @author Adam Wyłuda
 */
public class NullAction extends AbstractAction
{
    private ObjectInstance origin;

    private NullAction(ObjectInstance origin)
    {
        super(origin);

        this.origin = origin;
    }

    public static NullAction create(ObjectInstance origin)
    {
        return new NullAction(origin);
    }

    @Override
    public void perform()
    {
    }
}
