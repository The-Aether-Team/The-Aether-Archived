package com.gildedgames.the_aether.entities;

import com.gildedgames.the_aether.entities.block.EntityFloatingBlock;
import com.gildedgames.the_aether.entities.block.EntityTNTPresent;
import com.gildedgames.the_aether.entities.bosses.EntityFireMinion;
import com.gildedgames.the_aether.entities.bosses.EntityValkyrie;
import com.gildedgames.the_aether.entities.bosses.slider.EntitySlider;
import com.gildedgames.the_aether.entities.bosses.sun_spirit.EntitySunSpirit;
import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.gildedgames.the_aether.entities.passive.EntityAerwhale;
import com.gildedgames.the_aether.entities.passive.EntityMiniCloud;
import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import com.gildedgames.the_aether.entities.projectile.*;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityFireBall;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityIceyBall;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityThunderBall;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartEnchanted;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartGolden;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;
import com.gildedgames.the_aether.entities.projectile.*;
import net.minecraft.entity.Entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.hostile.EntityAechorPlant;
import com.gildedgames.the_aether.entities.hostile.EntityCockatrice;
import com.gildedgames.the_aether.entities.hostile.EntityMimic;
import com.gildedgames.the_aether.entities.hostile.EntitySentry;
import com.gildedgames.the_aether.entities.hostile.EntityWhirlwind;
import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import com.gildedgames.the_aether.entities.passive.mountable.EntityAerbunny;
import com.gildedgames.the_aether.entities.passive.mountable.EntityFlyingCow;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.entities.passive.mountable.EntityParachute;
import com.gildedgames.the_aether.entities.passive.mountable.EntityPhyg;
import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;

import static net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity;

public class AetherEntities 
{

	public static void initialization()
	{
		register(EntityMoa.class, "moa", 0, 0x87bfef, 0x7a7a7a);
		register(EntityPhyg.class, "phyg", 1, 0xffc1d0, 0xffd939);
		register(EntityFlyingCow.class, "flying_cow", 2, 0xd8d8d8, 0xffd939);
		register(EntitySheepuff.class, "sheepuff", 3, 0xe2fcff, 0xcb9090);
		register(EntityAerbunny.class, "aerbunny", 4, 0xe2fcff, 0xffdff9);
		register(EntityAerwhale.class, "aerwhale", 5, 0x79b7d1, 0xe0d25c);
		register(EntitySwet.class, "swet", 6, 0xcdda4f, 0x4fb1da);
		register(EntityCockatrice.class, "cockatrice", 7, 0x6cb15c, 0x6c579d);
		register(EntitySentry.class, "sentry", 8, 0x838c9a, 0x2561ba);
		register(EntityZephyr.class, "zephyr", 9, 0xdfdfdf, 0x99cfe8);
		register(EntityAechorPlant.class, "aechor_plant", 10, 0x076178, 0x4bc69e);
		register(EntityMimic.class, "mimic", 11, 0xb18132, 0x605a4e);

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
		register(EntityWhirlwind.class, "whirlwind", 32, 0x9fc3f7, 0xffffff);
		register(EntityTippedPhoenixArrow.class, "tipped_phoenix_arrow", 33);
	}

	public static void register(Class<? extends Entity> entityClass, String entityName, int entityID)
	{
		registerModEntity(Aether.locate(entityName), entityClass, entityName, entityID, Aether.modid, 80, 3, true);
	}

	public static void register(Class<? extends Entity> entityClass, String entityName, int entityID, int primaryEggColor, int secondaryEggColor)
	{
		registerModEntity(Aether.locate(entityName), entityClass, entityName, entityID, Aether.instance, 80, 3, false, primaryEggColor, secondaryEggColor);
	}

}