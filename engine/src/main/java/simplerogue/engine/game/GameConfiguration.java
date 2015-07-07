package simplerogue.engine.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Actor;
import simplerogue.engine.object.AbstractReifiable;
import simplerogue.engine.object.Loadable;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.Named;

/**
 * Global game properties. Stored in game save.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.game.GameConfigurator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GameConfiguration extends AbstractReifiable implements Named, Loadable
{
    public static final String NAME = "configurator_name";

    private static final String PLAYER_ID = "player_id";
    private static final String TURN_COUNT = "turn_count";

    /**
     * Name of this configuration. Links to the generator of configurator.
     *
     * @see GameConfigurator#getName()
     */
    private String name;
    private Actor player;
    private int turnCount;

    @Override
    public void load(Model model)
    {
        name = model.getString(NAME);
        player = model.getInstance(PLAYER_ID);
        turnCount = model.getInt(TURN_COUNT);
    }

    @Override
    public Model save()
    {
        Model model = new Model();

        model.put(NAME, name);
        model.putInstance(PLAYER_ID, player);
        model.put(TURN_COUNT, turnCount);

        return model;
    }
}
