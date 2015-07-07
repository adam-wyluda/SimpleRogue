package simplerogue.world.levelgenerator;

import simplerogue.engine.game.GameManager;
import simplerogue.engine.levelgenerator.processor.AbstractLevelProcessor;
import simplerogue.engine.manager.Managers;
import simplerogue.world.WorldConsts;
import simplerogue.world.game.DefaultGameConfiguration;
import simplerogue.world.game.Difficulty;

/**
 * @author Adam Wyłuda
 */
public class MonsterGenerator extends AbstractLevelProcessor<DefaultLevelRequest>
{
    private static final String NAME = "monster_generator";

    @Override
    public void process(DefaultLevelRequest request)
    {
        GameManager gameManager = Managers.get(GameManager.class);

        int monsterCount = request.getMonsterCount();
        Difficulty difficulty;

        if (!request.isInitial())
        {
            DefaultGameConfiguration config = gameManager.getGame().getConfig().reify(DefaultGameConfiguration.class);
            difficulty = config.getDifficulty();
        }
        else
        {
            difficulty = request.getDifficulty();
        }

        monsterCount *= difficulty.getMonsterMultiplier();
        double probability = difficulty.getMonsterChance();
        GeneratorHelper.addObjectsWithDecreasingChance(request, WorldConsts.MONSTERS, monsterCount, probability);
        GeneratorHelper.addObjectToRandomRoom(request, WorldConsts.BIG_MONSTER_NAME);
        GeneratorHelper.addObjectsWithEvenDistribution(request, WorldConsts.BLOATS, 5);
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
