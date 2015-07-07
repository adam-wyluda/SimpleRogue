package simplerogue.engine.levelgenerator.processor;

import simplerogue.engine.level.FieldArea;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelManager;
import simplerogue.engine.levelgenerator.LevelRequest;
import simplerogue.engine.manager.Managers;

/**
 * Creates empty {@link simplerogue.engine.level.Level} instance.
 *
 * @author Adam Wyłuda
 */
public class InitialLevelProcessor extends AbstractLevelProcessor
{
    public static final String NAME = "initial";

    @Override
    public String getName()
    {
        return NAME;
    }

    @Override
    public void process(LevelRequest request)
    {
        LevelManager levelManager = Managers.get(LevelManager.class);

        String type = request.getLevelType();
        Level level = levelManager.createLevel(type);

        String name = levelManager.generateLevelName(request.getGenerator());
        level.setName(name);

        int height = request.getHeight();
        int width = request.getWidth();

        FieldArea area = FieldArea.create(height, width);
        level.setArea(area);

        request.setLevel(level);
    }
}
