package simplerogue.world.ai;

import simplerogue.engine.ai.AbstractEffectHandler;
import simplerogue.engine.game.GameUtil;
import simplerogue.engine.level.Direction;
import simplerogue.engine.level.Field;
import simplerogue.world.level.effect.ExpandingEffect;

/**
 * @author Adam Wyłuda
 */
public abstract class ExpandingEffectHandler<T extends ExpandingEffect> extends AbstractEffectHandler<T>
{
    @Override
    public void apply(T effect)
    {
        applyEffect(effect);
        expand(effect);
    }

    protected abstract void applyEffect(T effect);

    protected void expand(T effect)
    {
        Field field = effect.getField();
        effect.lowerIntensity();

        if (effect.getIntensity() <= 0)
        {
            GameUtil.removeFromGame(effect);
        }
        else
        {
            for (Direction direction : Direction.NON_IDEMPOTENT)
            {
                Field nearField = direction.transform(field);

                if (!nearField.isBlocking() && !hasEffect(nearField, effect.getClass()))
                {
                    tryExpand(effect, nearField);
                }
            }
        }
    }

    protected abstract void tryExpand(T fire, Field field);

    protected boolean hasEffect(Field field, Class<? extends ExpandingEffect> type)
    {
        return field.lookupElement(type).isPresent();
    }
}
