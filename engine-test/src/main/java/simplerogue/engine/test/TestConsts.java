package simplerogue.engine.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import simplerogue.engine.ai.AI;
import simplerogue.engine.ai.EffectHandler;
import simplerogue.engine.ai.FieldHandler;
import simplerogue.engine.ai.ItemHandler;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.level.Level;
import simplerogue.engine.level.LevelElement;
import simplerogue.engine.levelgenerator.LevelGenerator;
import simplerogue.engine.object.Prototype;
import simplerogue.engine.resource.Resources;
import simplerogue.engine.test.ai.TestAI;
import simplerogue.engine.test.ai.TestEffectHandler;
import simplerogue.engine.test.ai.TestFieldHandler;
import simplerogue.engine.test.ai.TestItemHandler;
import simplerogue.engine.test.game.TestGameConfigurator;
import simplerogue.engine.test.world.ai.MonsterAI;
import simplerogue.engine.test.world.level.TestLevel;
import simplerogue.engine.test.world.level.actor.Monster;
import simplerogue.engine.test.world.level.field.Wall;
import simplerogue.engine.test.world.level.item.Bow;
import simplerogue.engine.test.world.level.item.Staff;
import simplerogue.engine.test.world.level.item.Sword;
import simplerogue.engine.test.world.levelgenerator.TestLevelGenerator;

import java.util.List;
import java.util.Map;

/**
 * All test constants in one place.
 *
 * @author Adam Wyłuda
 */
public class TestConsts
{
    // World
    public static final String WORLD_NAME = "test_world";

    public static final List<AI> AIS = Lists.newArrayList();
    public static final List<EffectHandler> EFFECT_HANDLERS = Lists.newArrayList();
    public static final List<FieldHandler> FIELD_HANDLERS = Lists.newArrayList();
    public static final List<ItemHandler> ITEM_HANDLERS = Lists.newArrayList();
    public static final List<GameConfigurator> GAME_CONFIGURATORS = Lists.newArrayList();

    public static final String TEST_AI_NAME = "test_ai";
    public static final String TEST_EFFECT_HANDLER_NAME = "test_effect_handler";
    public static final String TEST_FIELD_HANDLER_NAME = "test_field_handler";
    public static final String TEST_ITEM_HANDLER_NAME = "test_item_handler";
    public static final String TEST_GAME_CONFIGURATOR_NAME = "test_game_configurator";

    public static final String MONSTER_AI_NAME = "monster";

    static
    {
        GAME_CONFIGURATORS.add(new TestGameConfigurator());

        AIS.add(new TestAI());
        AIS.add(new MonsterAI());

        EFFECT_HANDLERS.add(new TestEffectHandler());
        FIELD_HANDLERS.add(new TestFieldHandler());
        ITEM_HANDLERS.add(new TestItemHandler());
    }

    // Game configurations
    public static final String GAME_DIFFICULTY = "difficulty";
    public static final String GAME_DIFFICULTY_DESCRIPTION = "Difficulty";

    // Prototypes
    public static final List<Prototype> PROTOTYPES = Lists.newArrayList();

    // Resource prototypes
    public static final String PLAYER_NAME = "player";

    public static final String WALL_NAME = "wall";
    public static final int WALL_ID = 5;
    public static final String WALL_TRANSPARENCY = "transparency";

    public static final String FLOOR_NAME = "floor";
    public static final int FLOOR_ID = 10;

    public static final String SWORD_NAME = "sword";
    public static final String SWORD_ATTACK = "attack";

    public static final String BOW_NAME = "bow";
    public static final String BOW_RANGE = "range";

    public static final String STAFF_NAME = "staff";
    public static final String STAFF_LENGTH = "length";

    public static final String MONSTER_NAME = "monster";
    public static final String MONSTER_SIZE = "size";

    // Object and level types
    public static final Map<String, Class<? extends LevelElement>> OBJECT_TYPES = Maps.newHashMap();

    public static final Map<String,Class<? extends Level>> LEVEL_TYPES = Maps.newHashMap();
    public static final String TEST_LEVEL_TYPE = "test_level";

    static
    {
        OBJECT_TYPES.put(WALL_NAME, Wall.class);

        OBJECT_TYPES.put(SWORD_NAME, Sword.class);
        OBJECT_TYPES.put(BOW_NAME, Bow.class);
        OBJECT_TYPES.put(STAFF_NAME, Staff.class);

        OBJECT_TYPES.put(MONSTER_NAME, Monster.class);

        LEVEL_TYPES.put(TEST_LEVEL_TYPE, TestLevel.class);
    }

    // Resources
    public static final String PROTOTYPES_PATH = Resources.PROTOTYPES + "test_world/";
    public static final String FIELDS_PATH = PROTOTYPES_PATH + "fields.json";

    // Saves
    public static final String SAVE_NAME = "n1";
    public static final String SAVE_PATH = Resources.SAVES + SAVE_NAME + "/";

    public static final String SECRET_PROPERTY = "secret_property";
    public static final String SECRET_VALUE = "secret_value";

    public static final String LEVEL_NAME = "l1";
    public static final int LEVEL_HEIGHT = 100;
    public static final int LEVEL_WIDTH = 100;
    public static final String LEVEL_PROPERTY = "level_property";
    public static final String LEVEL_PROPERTY_VALUE = "value_x";

    public static final int WALL_POS_Y = 5;

    // Objects
    public static final int SWORD_ID = 101;

    public static final int PLAYER_ID = 51;
    public static final int PLAYER_SWORD_ID = 102;
    public static final int PLAYER_SWORD_ATTACK = 1500;
    public static final int PLAYER_BOW_ID = 103;

    public static final int MONSTER_ID = 52;

    // Level generators
    public static final List<LevelGenerator> LEVEL_GENERATORS = Lists.newArrayList();
    public static final String TEST_GENERATOR_NAME = "test_generator";

    static
    {
        TestConsts.LEVEL_GENERATORS.add(new TestLevelGenerator());
    }
}
