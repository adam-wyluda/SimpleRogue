package simplerogue.world.levelgenerator;

import lombok.Getter;
import lombok.Setter;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.levelgenerator.util.BSP;
import simplerogue.engine.object.Model;
import simplerogue.world.game.Difficulty;
import simplerogue.world.level.DefaultLevel;

/**
 * @author Adam Wyłuda
 */
public class DefaultLevelRequest extends LevelRequest
{
    private static final String MONSTER_COUNT = "monster_count";

    @Getter
    @Setter
    private int levelNumber = 1;

    @Getter
    @Setter
    private int monsterCount = 10;

    @Getter
    @Setter
    private BSP bsp;

    @Getter
    @Setter
    private Difficulty difficulty;

    public DefaultLevelRequest()
    {
        super(DefaultLevelGenerator.NAME);
    }

    @Override
    public String getLevelType()
    {
        return DefaultLevel.NAME;
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        monsterCount = model.getInt(MONSTER_COUNT);
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(MONSTER_COUNT, monsterCount);

        return save;
    }
}
