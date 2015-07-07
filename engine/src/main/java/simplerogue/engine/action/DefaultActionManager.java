package simplerogue.engine.action;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simplerogue.engine.ai.AI;
import simplerogue.engine.ai.AIInterest;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.EffectHandler;
import simplerogue.engine.ai.FieldHandler;
import simplerogue.engine.level.ActiveField;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Effect;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelElement;
import simplerogue.engine.manager.AbstractManager;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectInstance;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class DefaultActionManager extends AbstractManager implements ActionManager, WorldListener
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultActionManager.class);

    private AIManager aiManager;
    private ObjectManager objectManager;

    private List<Listener> listeners;
    private List<TurnPerformer> turnPerformers = Lists.newArrayList();

    @Override
    public void init()
    {
        aiManager = Managers.get(AIManager.class);
        objectManager = Managers.get(ObjectManager.class);
    }

    @Override
    public void makeTurn()
    {
        LOG.debug("Making turn");

        // objectManager.isRegistered() call is necessary because an object could be removed
        // as a result of other object action

        for (ObjectInstance object : objectManager.getAllObjects())
        {
            if (object.is(Actor.class) && objectManager.isRegistered(object))
            {
                makeTurn(object.reify(Actor.class));
            }
        }

        for (ObjectInstance object : objectManager.getAllObjects())
        {
            if (object.is(Effect.class) && objectManager.isRegistered(object))
            {
                makeTurn(object.reify(Effect.class));
            }
        }

        for (ObjectInstance object : objectManager.getAllObjects())
        {
            if (object.is(ActiveField.class) && objectManager.isRegistered(object))
            {
                makeTurn(object.reify(ActiveField.class));
            }
        }

        for (TurnPerformer turnPerformer : turnPerformers)
        {
            turnPerformer.perform();
        }

        turnPerformed();
    }

    @Override
    public void worldChanged(World world)
    {
        listeners = Lists.newArrayList();
    }

    private void makeTurn(ActiveField activeField)
    {
        FieldHandler<ActiveField> handler = activeField.getFieldHandler();

        for (FieldElement element : activeField.getElements())
        {
            handler.handle(activeField, element);
        }
    }

    private void makeTurn(Actor actor)
    {
        AI ai = actor.getAi();

        ai.makeTurn(actor);
    }

    private void makeTurn(Effect effect)
    {
        EffectHandler<Effect> effectHandler = effect.getHandler();

        effectHandler.apply(effect);
    }

    @Override
    public void submitAction(Action action)
    {
        LOG.debug("Performing action {} by {} with id {}",
                action.getClass().getSimpleName(),
                action.getOrigin().getClass().getSimpleName(),
                action.getOrigin().getId());

        action.perform();
        notifyAboutAction(action);
    }

    @Override
    public void addListener(Listener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void addTurnPerformer(TurnPerformer performer)
    {
        turnPerformers.add(performer);
    }

    private void turnPerformed()
    {
        for (Listener listener : listeners)
        {
            listener.turnPerformed();
        }
    }

    private void notifyAboutAction(Action action)
    {
        ObjectInstance origin = action.getOrigin();
        List<ObjectInstance> objects = objectManager.getAllObjects();
        Level level = null;

        if (origin.is(LevelElement.class))
        {
            LevelElement levelElement = origin.reify(LevelElement.class);
            level = levelElement.getLevel();
        }

        for (ObjectInstance object : objects)
        {
            notifyAboutAction(object, action, level);
        }
    }

    private void notifyAboutAction(ObjectInstance object, Action action, Level originLevel)
    {
        if (object.is(Actor.class))
        {
            Actor actor = object.reify(Actor.class);
            AI ai = actor.getAi();

            if (ai.getInterest() == AIInterest.LEVEL && actor.getLevel() == originLevel
                    || ai.getInterest() == AIInterest.GLOBAL)
            {
                ai.actionPerformed(actor, action);
            }
        }
    }
}
