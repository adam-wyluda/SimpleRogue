package simplerogue.engine.test.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.object.Model;
import simplerogue.engine.test.TestConsts;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestGameConfiguration extends GameConfiguration
{
    private Difficulty difficulty;

    @Override
    public void load(Model model)
    {
        super.load(model);

        difficulty = model.getEnum(Difficulty.class, TestConsts.GAME_DIFFICULTY);
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.put(TestConsts.GAME_DIFFICULTY, difficulty);

        return model;
    }
}
