package simplerogue.world.game;

import com.google.common.base.Optional;
import lombok.Data;
import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.object.Model;
import simplerogue.world.level.creature.Creature;

/**
 * @author Adam Wyłuda
 */
@Data
public class DefaultGameConfiguration extends GameConfiguration
{
    private static final String PLAYER_NAME = "player_name";
    private static final String DIFFICULTY = "difficulty";

    private Optional<Creature> selectedCreature = Optional.absent();

    private String playerName = "Unnamed";
    private Difficulty difficulty = Difficulty.NORMAL;

    @Override
    public void load(Model model)
    {
        super.load(model);

        playerName = model.getString(PLAYER_NAME);
        difficulty = model.getEnum(Difficulty.class, DIFFICULTY);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(PLAYER_NAME, playerName);
        save.put(DIFFICULTY, difficulty);

        return save;
    }
}
