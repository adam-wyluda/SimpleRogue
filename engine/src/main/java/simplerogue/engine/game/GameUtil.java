package simplerogue.engine.game;

import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.Item;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.save.GameSave;
import simplerogue.engine.world.World;
import simplerogue.engine.world.WorldListener;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
public class GameUtil
{
    public static void notifyAboutWorldChange(World world)
    {
        for (WorldListener listener : Managers.lookup(WorldListener.class))
        {
            listener.worldChanged(world);
        }
    }

    public static String getConfiguratorName(GameSave save)
    {
        return save.getProperties().getString(GameConfiguration.NAME);
    }

    public static void removeFromGame(FieldElement element)
    {
        if (element.is(Actor.class))
        {
            Actor actor = element.reify(Actor.class);
            List<Item> items = actor.getItems();

            Field field = actor.getField();
            field.putElements(items);
        }

        element.getLevel().removeElement(element);
        Managers.get(ObjectManager.class).removeObject(element);
    }

    public static Game getGame()
    {
        return Managers.get(GameManager.class).getGame();
    }

    public static void appendMessage(String message)
    {
        getGame().getMessages().add(message);
    }
}
