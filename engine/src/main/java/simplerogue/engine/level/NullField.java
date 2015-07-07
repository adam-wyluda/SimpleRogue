package simplerogue.engine.level;

/**
 * @author Adam Wyłuda
 */
public class NullField extends Field
{
    public static NullField INSTANCE = new NullField();

    public static final String NAME = "null_field";
    public static final int ID = 0;
}
