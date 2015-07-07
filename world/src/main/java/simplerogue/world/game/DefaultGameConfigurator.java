package simplerogue.world.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import simplerogue.engine.game.EnumGameProperty;
import simplerogue.engine.game.Game;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.game.GameProperty;
import simplerogue.engine.game.StringGameProperty;
import simplerogue.engine.level.Level;
import simplerogue.engine.levelgenerator.LevelGenerator;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.manager.Managers;
import simplerogue.world.levelgenerator.DefaultLevelGenerator;
import simplerogue.world.levelgenerator.DefaultLevelRequest;

import java.util.List;
import java.util.Map;

/**
 * @author Adam Wyłuda
 */
public class DefaultGameConfigurator implements GameConfigurator<DefaultGameConfiguration>
{
    public static final String NAME = "default_game_configurator";

    @Override
    public DefaultGameConfiguration createInitialConfiguration()
    {
        DefaultGameConfiguration config = new DefaultGameConfiguration();
        config.setName(NAME);

        return config;
    }

    @Override
    public Game configureGame(DefaultGameConfiguration config)
    {
        Game game = new Game();

        game.setConfig(config);

        Map<String, Level> levelMap = Maps.newHashMap();
        game.setLevelMap(levelMap);

        LevelGeneratorManager generatorManager = Managers.get(LevelGeneratorManager.class);

        DefaultLevelRequest request = generatorManager.createRequest(DefaultLevelGenerator.NAME);
        request.setInitial(true);
        request.setDifficulty(config.getDifficulty());

        LevelGenerator generator = generatorManager.getGenerator(request);
        Level level = generator.createLevel(request);
        levelMap.put(level.getName(), level);

        config.setPlayer(request.getPlayer());

        return game;
    }

    @Override
    public List<GameProperty> getProperties()
    {
        // TODO Reflection based property factory?

        GameProperty playerNameProperty = new StringGameProperty<DefaultGameConfiguration>("Player name")
        {
            @Override
            public String getValue(DefaultGameConfiguration config)
            {
                return config.getPlayerName();
            }

            @Override
            public void setValue(DefaultGameConfiguration config, String value)
            {
                config.setPlayerName(value);
            }
        };

        GameProperty difficultyProperty = new EnumGameProperty<Difficulty, DefaultGameConfiguration>(Difficulty.class)
        {
            @Override
            public Difficulty getValue(DefaultGameConfiguration config)
            {
                return config.getDifficulty();
            }

            @Override
            public void setValue(DefaultGameConfiguration config, Difficulty value)
            {
                config.setDifficulty(value);
            }
        };

        return Lists.newArrayList(playerNameProperty, difficultyProperty);
    }

    @Override
    public String getName()
    {
        return "default_game_configurator";
    }
}
