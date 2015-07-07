package simplerogue.engine.level;

import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.ai.AIManager;
import simplerogue.engine.ai.FieldHandler;
import simplerogue.engine.ai.NullFieldHandler;
import simplerogue.engine.ai.PortalHandler;
import simplerogue.engine.levelgenerator.LevelGeneratorManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectManager;

/**
 * Moves objects between levels.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.level.Level
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Portal<T extends LevelRequest> extends ActiveField
{
    public static final String NAME = "portal";

    private static final String LEVEL_REQUEST = "level_request";
    private static final String NEXT_PORTAL = "next_portal";

    /**
     * Level request for this portal.
     */
    private T levelRequest;
    /**
     * Target portal.
     */
    private Portal nextPortal;

    public static <P extends Portal> P create(String prototypeName, LevelRequest request)
    {
        P newPortal = Managers.get(ObjectManager.class).createObject(prototypeName);

        request.setFrom(newPortal);
        newPortal.setLevelRequest(request);

        return newPortal;
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        LevelGeneratorManager generatorManager = Managers.get(LevelGeneratorManager.class);

        Model levelRequestModel = model.has(LEVEL_REQUEST)
                ? model.getModel(LEVEL_REQUEST)
                : null;

        if (levelRequestModel != null)
        {
            String generator = LevelRequest.getGenerator(levelRequestModel);
            levelRequest = generatorManager.createRequest(generator);
            levelRequest.load(levelRequestModel);
        }

        nextPortal = (Portal) model.getInstanceOptional(NEXT_PORTAL).orNull();
    }

    @Override
    public Model save()
    {
        Model model = super.save();

        model.putLoadable(LEVEL_REQUEST, levelRequest);
        model.putInstance(NEXT_PORTAL, nextPortal);

        return model;
    }

    /**
     * If alternative handler was not specified, it will override it with {@link simplerogue.engine.ai.PortalHandler}.
     */
    @Override
    public FieldHandler getFieldHandler()
    {
        FieldHandler superHandler = super.getFieldHandler();

        return !superHandler.is(NullFieldHandler.class)
                ? superHandler
                : Managers.get(AIManager.class).getFieldHandler(PortalHandler.NAME);
    }

    public boolean isPointingToOtherPortal()
    {
        return nextPortal != null;
    }

    public void bind(Portal anotherPortal)
    {
        setNextPortal(anotherPortal);
        anotherPortal.setNextPortal(this);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
