package simplerogue.engine.levelgenerator;

import lombok.Data;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.Portal;
import simplerogue.engine.object.Loadable;
import simplerogue.engine.object.Model;

/**
 * Level generation request. <p/>
 *
 * Created by {@link LevelGenerator#createRequest()}. <p/>
 *
 * There are three kinds of properties:
 * <ul>
 *     <li><b>input</b> - these properties must be provided to generator</li>
 *     <li><b>exchange</b> - information shared between different processors</li>
 *     <li><b>output</b> - product of generator (like level instance)</li>
 * </ul>
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.levelgenerator.LevelGenerator
 * @see simplerogue.engine.levelgenerator.processor.LevelProcessor
 */
@Data
public class LevelRequest implements Loadable
{
    private static final String GENERATOR = "generator";
    private static final String LEVEL_TYPE = "level_type";
    private static final String FROM = "from";
    private static final String HEIGHT = "height";
    private static final int DEFAULT_HEIGHT = 100;
    private static final String WIDTH = "width";
    private static final int DEFAULT_WIDTH = 100;
    private static final String INITIAL = "initial";
    private static final String ENTRY_PORTAL = "entry_portal";

    /**
     * Type of this request, must match level generator.
     *
     * @see simplerogue.engine.levelgenerator.LevelGenerator
     */
    private String generator;

    /**
     * Input property. <p/>
     *
     * Portal in another level that points to this level.
     */
    private Portal from;

    /**
     * Output property. <p/>
     *
     * Entry of this level, pointed by <i>from</i> portal.
     *
     * @see #getFrom()
     */
    private Portal entryPortal;

    /**
     * Input property. <p/>
     *
     * Height of generated level.
     */
    private int height;

    /**
     * Input property. <p/>
     *
     * Width of generated level.
     */
    private int width;

    /**
     * Input property. <p/>
     *
     * Initial level will contain player.
     *
     * @see #getPlayer()
     */
    private boolean initial;

    /**
     * Input property. <p/>
     *
     * Must match type of created level.
     *
     * @see simplerogue.engine.world.World#getLevelTypes()
     */
    private String levelType;

    /**
     * Output property. <p/>
     *
     * Resulting level.
     */
    private Level level;

    /**
     * Output property. <p/>
     *
     * If generated level is initial, this property contains reference to player instance so generator could
     * put it in some place.
     */
    private Actor player;

    protected LevelRequest(String generator)
    {
        this.generator = generator;

        setLevelType(Level.NAME);
        setHeight(DEFAULT_HEIGHT);
        setWidth(DEFAULT_WIDTH);
        setInitial(false);
    }

    @Override
    public void load(Model model)
    {
        generator = getGenerator(model);
        levelType = model.getString(LEVEL_TYPE);

        from = (Portal) model.getInstanceOptional(FROM).orNull();
        entryPortal = (Portal) model.getInstanceOptional(ENTRY_PORTAL).orNull();

        height = model.getInt(HEIGHT);
        width = model.getInt(WIDTH);
        initial = model.getBoolean(INITIAL);
    }

    @Override
    public Model save()
    {
        Model model = new Model();

        model.put(GENERATOR, generator);
        model.put(LEVEL_TYPE, levelType);

        model.putInstance(FROM, from);
        model.putInstance(ENTRY_PORTAL, entryPortal);

        model.put(HEIGHT, height);
        model.put(WIDTH, width);
        model.put(INITIAL, initial);

        return model;
    }

    /**
     * Creates default level request instance.
     */
    public static LevelRequest create(String generator)
    {
        return new LevelRequest(generator);
    }

    public static String getGenerator(Model model)
    {
        return model.getString(GENERATOR);
    }
}
