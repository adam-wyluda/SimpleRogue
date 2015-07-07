package simplerogue.world.ui;

import com.google.common.base.Optional;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.Level;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.game.GameLayer;
import simplerogue.world.game.DefaultGameConfiguration;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.creature.Player;

/**
 * @author Adam Wyłuda
 */
public class DefaultTargetHandler implements GameLayer.TargetHandler
{
    @Override
    public void handleTargetSelected(int targetY, int targetX)
    {
        GameManager gameManager = Managers.get(GameManager.class);

        Player player = gameManager.getPlayer();
        Level level = player.getLevel();

        DefaultGameConfiguration config = gameManager.getGame().getConfig().reify(DefaultGameConfiguration.class);
        if (config.getSelectedCreature().isPresent())
        {
            config.setSelectedCreature(Optional.<Creature> absent());
        }

        Optional<Creature> creatureOptional = level.getArea()
                .getFieldAt(targetY, targetX)
                .lookupElement(Creature.class);

        if (creatureOptional.isPresent() && creatureOptional.get() != gameManager.getPlayer())
        {
            Creature creature = creatureOptional.get();
            config.setSelectedCreature(Optional.of(creature));
        }

        GameLayer.getInstance().setMode(GameLayer.GameMode.ACTION);
    }
}
