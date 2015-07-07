package simplerogue.world;

import com.google.common.collect.Lists;
import simplerogue.engine.action.ActionManager;
import simplerogue.engine.game.GameConfigurator;
import simplerogue.engine.manager.Managers;
import simplerogue.engine.ui.UIManager;
import simplerogue.engine.ui.game.GameLayer;
import simplerogue.engine.world.World;
import simplerogue.world.action.GameOverActionListener;
import simplerogue.world.action.ModifierTurnPerformer;
import simplerogue.world.action.PlayerStateActionListener;
import simplerogue.world.ai.BloatAI;
import simplerogue.world.ai.FireHandler;
import simplerogue.world.ai.GasHandler;
import simplerogue.world.ai.MonsterAI;
import simplerogue.world.ai.PotionHandler;
import simplerogue.world.game.DefaultGameConfigurator;
import simplerogue.world.level.DefaultLevel;
import simplerogue.world.level.creature.Bloat;
import simplerogue.world.level.creature.Monster;
import simplerogue.world.level.creature.Player;
import simplerogue.world.level.effect.Fire;
import simplerogue.world.level.effect.Gas;
import simplerogue.world.level.field.Floor;
import simplerogue.world.level.item.Armor;
import simplerogue.world.level.item.Bow;
import simplerogue.world.level.item.Potion;
import simplerogue.world.level.item.Weapon;
import simplerogue.world.levelgenerator.DefaultLevelGenerator;
import simplerogue.world.ui.DefaultGameMenuLayer;
import simplerogue.world.ui.DefaultTargetHandler;
import simplerogue.world.ui.EquipmentMenuLayer;
import simplerogue.world.ui.GameActionProvider;
import simplerogue.world.ui.PlayerStatsMenuLayer;
import simplerogue.world.ui.PlayerStatusLayer;

/**
 * @author Adam Wyłuda
 */
public class DefaultWorld extends World
{
    public static final String NAME = "default_world";

    public DefaultWorld()
    {
        setName(NAME);

        setConfigurators(Lists.<GameConfigurator> newArrayList(new DefaultGameConfigurator()));

        getAis().add(new MonsterAI());
        getAis().add(new BloatAI());
        getItemHandlers().add(new PotionHandler());
        getEffectHandlers().add(new FireHandler());
        getEffectHandlers().add(new GasHandler());

        getObjectTypes().put(Floor.NAME, Floor.class);

        getObjectTypes().put(Player.NAME, Player.class);
        getObjectTypes().put(Monster.NAME, Monster.class);
        getObjectTypes().put(Bloat.NAME, Bloat.class);

        getObjectTypes().put(Armor.NAME, Armor.class);
        getObjectTypes().put(Bow.NAME, Bow.class);
        getObjectTypes().put(Potion.NAME, Potion.class);
        getObjectTypes().put(Weapon.NAME, Weapon.class);

        getObjectTypes().put(Fire.NAME, Fire.class);
        getObjectTypes().put(Gas.NAME, Gas.class);

        addObjectTypes(WorldConsts.MONSTERS, Monster.class);
        addObjectTypes(WorldConsts.BOWS, Bow.class);
        addObjectTypes(WorldConsts.SWORDS, Weapon.class);
        addObjectTypes(WorldConsts.ARMORS, Armor.class);
        addObjectTypes(WorldConsts.HEALTH_POTIONS, Potion.class);

        getLevelTypes().put(DefaultLevel.NAME, DefaultLevel.class);

        getLevelGenerators().add(new DefaultLevelGenerator());
    }

    @Override
    public void configureUI()
    {
        final UIManager uiManager = Managers.get(UIManager.class);

        final PlayerStatusLayer playerStatusLayer = new PlayerStatusLayer();
        uiManager.registerLayer(playerStatusLayer);

        uiManager.registerLayer(new PlayerStatsMenuLayer());
        uiManager.registerLayer(new EquipmentMenuLayer());

        GameLayer gameLayer = GameLayer.getInstance();
        gameLayer.setActionProvider(new GameActionProvider());
        gameLayer.setGameMenu(new DefaultGameMenuLayer());
        gameLayer.setTargetHandler(new DefaultTargetHandler());
    }

    @Override
    public void configureGame()
    {
        ActionManager actionManager = Managers.get(ActionManager.class);

        actionManager.addListener(new GameOverActionListener());
        actionManager.addListener(new PlayerStateActionListener());

        actionManager.addTurnPerformer(new ModifierTurnPerformer());
    }
}
