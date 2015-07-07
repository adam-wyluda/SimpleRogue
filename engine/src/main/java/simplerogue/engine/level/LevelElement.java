package simplerogue.engine.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.AbstractReifiable;
import simplerogue.engine.object.HasPrototype;
import simplerogue.engine.object.Loadable;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectInstance;
import simplerogue.engine.object.ObjectManager;
import simplerogue.engine.object.ObjectUtil;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.view.Char;

/**
 * Object that is presented on level map.
 *
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LevelElement extends AbstractReifiable implements HasPrototype, Loadable
{
    public static final String NAME = "level_element";

    private static final String BLOCKING = "blocking";

    private Level level;

    private String prototype;

    /**
     * A level element is blocking, when no other field element can move on it.
     * <p/>
     * If not overriden, this method returns true if blocking property is defined in its properties.
     */
    private boolean blocking;

    private int posY;
    private int posX;

    /**
     * Presentation of this level element.
     */
    private Char character = Char.DEFAULT_CHAR;

    @Override
    public void load(Model model)
    {
        prototype = ObjectUtil.getPrototypeName(model);
        blocking = model.has(BLOCKING) ? model.getBoolean(BLOCKING) : false;
        posY = LevelUtil.getPosY(model);
        posX = LevelUtil.getPosX(model);
        character = Char.create(model);
    }

    @Override
    public Model save()
    {
        Model model = new Model();

        model.put(BLOCKING, blocking);
        model.put(LevelUtil.POS_Y, posY);
        model.put(LevelUtil.POS_X, posX);

        return model;
    }

    @Override
    public Prototype getPrototype()
    {
        return Managers.get(ObjectManager.class).getPrototype(prototype);
    }

    @Override
    public String toString()
    {
        String type = this.getPrototype().getType();
        String id = "";

        if (this.is(ObjectInstance.class))
        {
            id = String.format("/%d", this.reify(ObjectInstance.class).getId());
        }

        return type + id;
    }
}
