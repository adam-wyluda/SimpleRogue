package simplerogue.engine.levelgenerator.processor;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractSortableLevelProcessor extends AbstractLevelProcessor implements SortableLevelProcessor
{
    private List<String> runBefore;
    private List<String> runAfter;
}
