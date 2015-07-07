package simplerogue.engine.object;

import lombok.Data;
import simplerogue.engine.manager.Managers;

/**
 * @author Adam Wyłuda
 */
@Data
public class AbstractObjectInstance extends AbstractReifiable implements ObjectInstance, Reifiable
{
    private int id;
    private String prototype;

    @Override
    public Prototype getPrototype()
    {
        return Managers.get(ObjectManager.class).getPrototype(prototype);
    }

    @Override
    public void load(Model model)
    {
        prototype = ObjectUtil.getPrototypeName(model);
    }

    @Override
    public Model save()
    {
        Model model = new Model();

        return model;
    }
}
