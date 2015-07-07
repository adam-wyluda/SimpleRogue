package simplerogue.world.action;

import com.google.common.collect.Lists;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.world.level.creature.Creature;
import simplerogue.world.level.status.Modifier;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class ModifierTurnPerformer implements ActionManager.TurnPerformer
{
    @Override
    public void perform()
    {
        for (Creature creature : Managers.get(ObjectManager.class).lookupObjects(Creature.class))
        {
            applyModifiers(creature);
        }
    }

    private void applyModifiers(Creature creature)
    {
        List<Modifier> toBeRemoved = Lists.newArrayList();

        for (Modifier modifier : creature.getModifiers())
        {
            if (!modifier.apply(creature))
            {
                toBeRemoved.add(modifier);
            }
        }

        creature.getModifiers().removeAll(toBeRemoved);
    }
}
