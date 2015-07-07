package simplerogue.engine.object;

/**
 * Instance of an object in game (like player or item).
 *
 * @author Adam Wyłuda
 */
public interface ObjectInstance extends Reifiable, HasPrototype, Loadable
{
    String ID = "id";

    int getId();

    void setId(int id);
}
