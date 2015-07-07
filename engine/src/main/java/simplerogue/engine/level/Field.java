package simplerogue.engine.level;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.object.Model;
import simplerogue.engine.object.ObjectUtil;
import simplerogue.engine.view.Char;

import java.util.List;

/**
 * A part of {@link simplerogue.engine.level.FieldArea}.
 *
 * @author Adam Wyłuda
 * @see simplerogue.engine.level.FieldArea
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Field extends LevelElement
{
    public static final String NAME = "field";

    /**
     * A list of elements which are placed on this field, not persisted.
     */
    private List<FieldElement> elements = Lists.newLinkedList();

    private boolean cached;
    private int fieldId;

    @Override
    public void load(Model model)
    {
        super.load(model);

        fieldId = ObjectUtil.getFieldId(model);
        cached = ObjectUtil.isCached(model);
    }

    /**
     * If this field is defined as blocking in its properties then it will return true. Otherwise it is blocking
     * if any of its level elements ({@link #getElements()}) is blocking.
     */
    @Override
    public boolean isBlocking()
    {
        if (super.isBlocking())
        {
            return true;
        }

        for (FieldElement element : elements)
        {
            if (element.isBlocking())
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public Char getCharacter()
    {
        if (elements.isEmpty())
        {
            return super.getCharacter();
        }

        return elements.get(elements.size() - 1).getCharacter();
    }

    public <E extends FieldElement> Optional<E> lookupElement(Class<E> clazz)
    {
        E result = null;

        for (FieldElement element : elements)
        {
            if (element.is(clazz))
            {
                result = element.reify(clazz);
                break;
            }
        }

        return Optional.fromNullable(result);
    }

    public <E extends FieldElement> List<E> lookupElements(Class<E> clazz)
    {
        List<E> result = Lists.newArrayList();

        for (FieldElement element : elements)
        {
            if (element.is(clazz))
            {
                E e = element.reify(clazz);
                result.add(e);
            }
        }

        return result;
    }

    public void putElement(FieldElement element)
    {
        getLevel().putElement(element, getPosY(), getPosX());
    }

    public void putElements(List<? extends FieldElement> elements)
    {
        for (FieldElement element : elements)
        {
            putElement(element);
        }
    }

    public void removeElement(FieldElement element)
    {
        getLevel().removeElement(element);
    }
}
