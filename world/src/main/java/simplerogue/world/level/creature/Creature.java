package simplerogue.world.level.creature;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import simplerogue.engine.game.GameManager;
import simplerogue.engine.level.Actor;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.object.Model;
import simplerogue.engine.view.Char;
import simplerogue.world.game.DefaultGameConfiguration;
import simplerogue.world.level.status.Modifier;

import java.util.List;

/**
 * @author Adam Wyłuda
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Creature extends Actor
{
    public static final String NAME = "creature";

    private static final String MAX_HP = "max_hp";
    private static final String HP = "hp";
    private static final String STRENGTH = "strength";
    private static final String PERCEPTION = "perception";
    private static final String STAMINA = "stamina";
    private static final String MODIFIERS = "modifiers";

    private int maxHp;
    private int hp;

    private int strength;
    private int perception;
    private int stamina;

    private List<Modifier> modifiers;

    @Override
    public Char getCharacter()
    {
        Char character = super.getCharacter();

        Optional<Creature> selectedCreature = Managers.get(GameManager.class)
                .getGame()
                .getConfig().reify(DefaultGameConfiguration.class)
                .getSelectedCreature();

        if (selectedCreature.isPresent() && this == selectedCreature.get())
        {
            character = Char.create(character.getCharacter(),
                    character.getColor().negate(),
                    character.getBackgroundColor().negate());
        }

        return character;
    }

    @Override
    public void load(Model model)
    {
        super.load(model);

        maxHp = model.getInt(MAX_HP);
        hp = model.has(HP) ? model.getInt(HP) : maxHp;
        strength = model.getInt(STRENGTH);
        perception = model.getInt(PERCEPTION);
        stamina = model.getInt(STAMINA);

        modifiers = Lists.newArrayList();
        if (model.has(MODIFIERS))
        {
            for (Model modifierModel : model.<Iterable<Model>>get(MODIFIERS))
            {
                Modifier modifier = Modifier.create(modifierModel);
                modifiers.add(modifier);
            }
        }
    }

    @Override
    public Model save()
    {
        Model save = super.save();

        save.put(MAX_HP, maxHp);
        save.put(HP, hp);
        save.put(STRENGTH, strength);
        save.put(PERCEPTION, perception);
        save.put(STAMINA, stamina);
        save.putLoadables(MODIFIERS, modifiers);

        return save;
    }
}
