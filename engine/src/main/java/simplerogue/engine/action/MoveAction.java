package simplerogue.engine.action;

import lombok.Data;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.game.GameUtil;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.engine.level.FieldElement;
import simplerogue.engine.level.Item;
import simplerogue.engine.level.Level;
import simplerogue.engine.manager.Managers;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
public class MoveAction extends AbstractAction<FieldElement>
{
    // New position
    private int posY;
    private int posX;

    private MoveAction(FieldElement element, int posY, int posX)
    {
        super(element);

        this.posY = posY;
        this.posX = posX;
    }

    public static MoveAction create(FieldElement element, Direction direction)
    {
        int posY = direction.transformY(element.getPosY());
        int posX = direction.transformX(element.getPosX());

        return new MoveAction(element, posY, posX);
    }

    public static MoveAction create(FieldElement element, int posY, int posX)
    {
        return new MoveAction(element, posY, posX);
    }

    @Override
    public void perform()
    {
        FieldElement element = getOrigin();
        Level level = element.getLevel();

        Field oldField = element.getField();
        Field newField = level.getArea().getFieldAt(posY, posX);

        if (!newField.isBlocking())
        {
            oldField.getElements().remove(element);
            newField.getElements().add(element);

            element.setPosY(posY);
            element.setPosX(posX);

            if (element.is(Actor.class))
            {
                Actor actor = element.reify(Actor.class);
                takeItems(actor, newField);
            }
        }
    }

    private void takeItems(Actor actor, Field field)
    {
        Actor player = Managers.get(GameManager.class).getPlayer();

        // Take all items
        List<Item> items = field.lookupElements(Item.class);

        actor.getItems().addAll(items);
        field.getElements().removeAll(items);

        // To prevent loading it back to the level
        // TOODO Taken items should be stored in some global registry
        // Maybe not?
        for (Item item : items)
        {
            item.setLevel(null);
            item.setPosY(-1);
            item.setPosX(-1);

            if (actor == player)
            {
                String message = String.format(
                        "@(GRAY|)You take @(GREEN|)%s@(GRAY|)",
                        item.getName());
                GameUtil.appendMessage(message);
            }
        }
    }
}
