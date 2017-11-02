package com.legacy.aether.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.legacy.aether.api.AetherRegistry;
import com.legacy.aether.api.moa.AetherMoaType;
import com.legacy.aether.api.moa.MoaProperties;
import com.legacy.aether.common.Aether;
import com.legacy.aether.common.entities.block.EntityFloatingBlock;
import com.legacy.aether.common.entities.block.EntityTNTPresent;
import com.legacy.aether.common.entities.bosses.EntityFireMinion;
import com.legacy.aether.common.entities.bosses.EntityValkyrie;
import com.legacy.aether.common.entities.bosses.slider.EntitySlider;
import com.legacy.aether.common.entities.bosses.sun_spirit.EntitySunSpirit;
import com.legacy.aether.common.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.legacy.aether.common.entities.hostile.EntityAechorPlant;
import com.legacy.aether.common.entities.hostile.EntityCockatrice;
import com.legacy.aether.common.entities.hostile.EntityMimic;
import com.legacy.aether.common.entities.hostile.EntitySentry;
import com.legacy.aether.common.entities.hostile.EntityZephyr;
import com.legacy.aether.common.entities.passive.EntityAerwhale;
import com.legacy.aether.common.entities.passive.EntityMiniCloud;
import com.legacy.aether.common.entities.passive.EntitySheepuff;
import com.legacy.aether.common.entities.passive.mountable.EntityAerbunny;
import com.legacy.aether.common.entities.passive.mountable.EntityFlyingCow;
import com.legacy.aether.common.entities.passive.mountable.EntityMoa;
import com.legacy.aether.common.entities.passive.mountable.EntityParachute;
import com.legacy.aether.common.entities.passive.mountable.EntityPhyg;
import com.legacy.aether.common.entities.passive.mountable.EntitySwet;
import com.legacy.aether.common.entities.projectile.EntityHammerProjectile;
import com.legacy.aether.common.entities.projectile.EntityLightningKnife;
import com.legacy.aether.common.entities.projectile.EntityPhoenixArrow;
import com.legacy.aether.common.entities.projectile.EntityPoisonNeedle;
import com.legacy.aether.common.entities.projectile.EntityZephyrSnowball;
import com.legacy.aether.common.entities.projectile.crystals.EntityFireBall;
import com.legacy.aether.common.entities.projectile.crystals.EntityIceyBall;
import com.legacy.aether.common.entities.projectile.crystals.EntityThunderBall;
import com.legacy.aether.common.entities.projectile.darts.EntityDartEnchanted;
import com.legacy.aether.common.entities.projectile.darts.EntityDartGolden;
import com.legacy.aether.common.entities.projectile.darts.EntityDartPoison;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class AetherEntities 
{

	public static AetherMoaType BLUE_MOA, ORANGE_MOA, WHITE_MOA, BLACK_MOA;

	public static void initialization()
	{
		register(EntityMoa.class, "moa", 0, 0x9fc3f7, 0x343e44);
		register(EntityPhyg.class, "phyg", 1, 0x9fc3f7, 0xdb635f);
		register(EntityFlyingCow.class, "flying_cow", 2, 0x9fc3f7, 0x3e3122);
		register(EntitySheepuff.class, "sheepuff", 3, 0x9fc3f7, 0xcb9090);
		register(EntityAerbunny.class, "aerbunny", 4, 0x9fc3f7, 0x917575);
		register(EntityAerwhale.class, "aerwhale", 5, 0x9fc3f7, 0x81939d);
		register(EntitySwet.class, "swet", 6, 0x9fc3f7, 0x5f809c);
		register(EntityCockatrice.class, "cockatrice", 7, 0x9fc3f7, 0x3d2338);
		register(EntitySentry.class, "sentry", 8, 0x9fc3f7, 0xadadad);
		register(EntityZephyr.class, "zephyr", 9, 0x9fc3f7, 0x799fac);
		register(EntityAechorPlant.class, "aechor_plant", 10, 0x9fc3f7, 0x29a793);
		register(EntityMimic.class, "mimic", 11, 0x9fc3f7, 0xffffff);

		register(EntitySlider.class, "slider", 12);
		register(EntityValkyrieQueen.class, "valkyrie_queen", 13);
		register(EntitySunSpirit.class, "sun_spirit", 14);

		register(EntityDartGolden.class, "golden_dart", 15);
		register(EntityDartPoison.class, "poison_dart", 16);
		register(EntityDartEnchanted.class, "enchanted_dart", 17);
		register(EntityPoisonNeedle.class, "poison_needle", 18);

		register(EntityFireBall.class, "fire_ball", 19);
		register(EntityIceyBall.class, "ice_ball", 20);
		register(EntityThunderBall.class, "thunder_ball", 21);

		register(EntityValkyrie.class, "valkyrie", 22);
		register(EntityFireMinion.class, "fire_minion", 23);
		register(EntityMiniCloud.class, "mini_cloud", 24);

		register(EntityFloatingBlock.class, "floating_block", 25);
		register(EntityTNTPresent.class, "tnt_present", 26);

		register(EntityPhoenixArrow.class, "phoenix_arrow", 27);
		register(EntityZephyrSnowball.class, "zephyr_snowball", 28);
		register(EntityHammerProjectile.class, "hammer_projectile", 29);
		register(EntityLightningKnife.class, "lightning_knife", 30);
		register(EntityParachute.class, "parachute", 31);

		BLUE_MOA = AetherRegistry.getInstance().register(new ResourceLocation("aether_legacy", "blue"), new AetherMoaType(0x7777FF, new MoaProperties(3, 100, 0.3F), AetherCreativeTabs.misc));
		ORANGE_MOA = AetherRegistry.getInstance().register(new ResourceLocation("aether_legacy", "orange"), new AetherMoaType(-0xC3D78, new MoaProperties(2, 50, 0.6F), AetherCreativeTabs.misc));
		WHITE_MOA = AetherRegistry.getInstance().register(new ResourceLocation("aether_legacy", "white"), new AetherMoaType(0xFFFFFF, new MoaProperties(4, 20, 0.3F), AetherCreativeTabs.misc));
		BLACK_MOA = AetherRegistry.getInstance().register(new ResourceLocation("aether_legacy", "black"), new AetherMoaType(0x222222, new MoaProperties(8, 5, 0.3F), AetherCreativeTabs.misc));

		DataSerializerRegistry.initialize();
	}

	public static void register(Class<? extends Entity> entityClass, String entityName, int entityID)
	{
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, Aether.modid, 80, 3, true);
	}

	public static void register(Class<? extends Entity> entityClass, String entityName, int entityID, int primaryEggColor, int secondaryEggColor)
	{
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, Aether.instance, 80, 3, false, primaryEggColor, secondaryEggColor);
	}

}