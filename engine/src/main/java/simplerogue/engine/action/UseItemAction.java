package simplerogue.engine.action;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.level.Actor;
import simplerogue.engine.level.Item;
import simplerogue.engine.object.ObjectInstance;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UseItemAction <T extends Item> extends AbstractAction
{
    private T item;
    private Actor user;
    private ObjectInstance target;

    private UseItemAction(T item, Actor user, ObjectInstance target)
    {
        super(item);

        this.item = item;
        this.user = user;
        this.target = target;
    }

    public UseItemAction create(T item, Actor user, ObjectInstance target)
    {
        return new UseItemAction(item, user, target);
    }

    @Override
    public void perform()
    {
        item.getHandler().useItem(item, user, target);
    }
}
