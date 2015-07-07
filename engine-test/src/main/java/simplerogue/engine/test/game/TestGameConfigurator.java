package simplerogue.engine.test.game;

import com.google.common.collect.Lists;
import simplerogue.engine.game.EnumGameProperty;
import simplerogue.engine.game.Game;
import simplerogue.engine.game.GameConfiguration;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.game.GameProperty;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.test.TestConsts;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class TestGameConfigurator implements GameConfigurator<GameConfiguration>
{
    @Override
    public String getName()
    {
        return TestConsts.TEST_GAME_CONFIGURATOR_NAME;
    }

    @Override
    public GameConfiguration createInitialConfiguration()
    {
        GameConfiguration config = new TestGameConfiguration();

        config.setName(getName());

        return config;
    }

    @Override
    public Game configureGame(GameConfiguration config)
    {
        Game game = new Game();

        game.setConfig(config);

        LevelRequest request = Managers.get(LevelGeneratorManager.class).createRequest(TestConsts.TEST_GENERATOR_NAME);
        request.setInitial(true);

        LevelManager levelManager = Managers.get(LevelManager.class);
        Level level = levelManager.generateLevel(request).getLevel();
        game.getLevelMap().put(level.getName(), level);

        Actor player = request.getPlayer();
        config.setPlayer(player);

        return game;
    }

    @Override
    public List<GameProperty> getProperties()
    {
        return Lists.<GameProperty> newArrayList(createDifficultyConfigurator());
    }

    private EnumGameProperty<Difficulty, TestGameConfiguration> createDifficultyConfigurator()
    {
        return new EnumGameProperty<Difficulty, TestGameConfiguration>(
                TestConsts.GAME_DIFFICULTY_DESCRIPTION, Difficulty.class)
        {
            @Override
            public Difficulty getValue(TestGameConfiguration config)
            {
                return config.getDifficulty();
            }

            @Override
            public void setValue(TestGameConfiguration config, Difficulty value)
            {
                config.setDifficulty(value);
            }
        };
    }
}
