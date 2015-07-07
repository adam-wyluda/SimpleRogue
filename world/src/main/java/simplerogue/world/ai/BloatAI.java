package simplerogue.world.ai;

import simplerogue.engine.action.Action;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.action.MoveAction;
import simplerogue.engine.ai.AbstractAI;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Effect;
import simplerogue.engine.level.Level;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.world.level.creature.Bloat;

/**
 * @author Adam Wyłuda
 */
public class BloatAI extends AbstractAI<Bloat>
{
    public static final String NAME = "bloat_ai";

    @Override
    public void makeTurn(Bloat bloat)
    {
        if (bloat.getHp() < bloat.getMaxHp())
        {
            Level level = bloat.getLevel();
            Effect effect = bloat.createEffect();

            level.putElement(effect, bloat.getPosY(), bloat.getPosX());
            level.removeElement(bloat);
            Managers.get(ObjectManager.class).removeObject(bloat);
        }
        else
        {
            Action action = MoveAction.create(bloat, Direction.random());
            Managers.get(ActionManager.class).submitAction(action);
        }
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}
