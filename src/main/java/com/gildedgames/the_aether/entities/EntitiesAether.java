package com.gildedgames.the_aether.entities;

import java.util.HashMap;
import java.util.Map;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.gildedgames.the_aether.entities.passive.EntityAerwhale;
import com.gildedgames.the_aether.entities.passive.EntityMiniCloud;
import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityCrystal;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartEnchanted;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartGolden;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;
import com.gildedgames.the_aether.items.ItemAetherSpawnEgg;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gildedgames.the_aether.entities.block.EntityFloatingBlock;
import com.gildedgames.the_aether.entities.block.EntityTNTPresent;
import com.gildedgames.the_aether.entities.bosses.EntityFireMinion;
import com.gildedgames.the_aether.entities.bosses.EntityValkyrie;
import com.gildedgames.the_aether.entities.bosses.slider.EntitySlider;
import com.gildedgames.the_aether.entities.bosses.sun_spirit.EntitySunSpirit;
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
import com.gildedgames.the_aether.entities.projectile.EntityHammerProjectile;
import com.gildedgames.the_aether.entities.projectile.EntityLightningKnife;
import com.gildedgames.the_aether.entities.projectile.EntityPhoenixArrow;
import com.gildedgames.the_aether.entities.projectile.EntityPoisonNeedle;
import com.gildedgames.the_aether.entities.projectile.EntityZephyrSnowball;
import com.gildedgames.the_aether.entities.util.AetherMoaTypes;
import com.gildedgames.the_aether.entities.util.EntityAetherItem;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitiesAether {

	public static Map<Class<?>, String> classToStringMapping = new HashMap<Class<?>, String>();

	public static Map<Integer, Class<?>> IDtoClassMapping = new HashMap<Integer, Class<?>>();

	private static Map<Class<?>, Integer> classToIDMapping = new HashMap<Class<?>, Integer>();

	private static Map<String, Integer> stringToIDMapping = new HashMap<String, Integer>();

	private static final Logger logger = LogManager.getLogger();

	public static void initialization() {
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

		register(EntitySlider.class, "slider", 12, 80, 3, true);
		register(EntityValkyrieQueen.class, "valkyrie_queen", 13, 80, 3, true);
		register(EntitySunSpirit.class, "sun_spirit", 14, 80, 3, true);

		register(EntityDartGolden.class, "golden_dart", 15, 64, 20, false);
		register(EntityDartPoison.class, "poison_dart", 16, 64, 20, false);
		register(EntityDartEnchanted.class, "enchanted_dart", 17, 64, 20, false);
		register(EntityPoisonNeedle.class, "poison_needle", 18, 64, 20, false);

		register(EntityCrystal.class, "crystal", 19, 64, 4, false);
		register(EntityWhirlwind.class, "whirlwind", 20, 80, 3, false);
		register(EntityValkyrie.class, "valkyrie", 22, 80, 3, true);
		register(EntityFireMinion.class, "fire_minion", 23, 80, 3, true);
		register(EntityMiniCloud.class, "mini_cloud", 24, 80, 3, false);

		register(EntityFloatingBlock.class, "floating_block", 25, 160, 20, true);
		register(EntityTNTPresent.class, "tnt_present", 26, 160, 20, true);

		register(EntityPhoenixArrow.class, "phoenix_arrow", 27, 64, 20, false);
		register(EntityZephyrSnowball.class, "zephyr_snowball", 28, 64, 20, false);
		register(EntityHammerProjectile.class, "hammer_projectile", 29, 64, 20, false);
		register(EntityLightningKnife.class, "lightning_knife", 30, 64, 10, true);
		register(EntityParachute.class, "parachute", 31, 160, 20, true);
		register(EntityAetherItem.class, "aether_item", 32, 160, 20, true);

		AetherMoaTypes.initialization();
	}

	public static void register(Class<? extends Entity> entityClass, String entityName, int entityID, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, Aether.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	public static void register(Class<? extends Entity> entityClass, String entityName, int entityID, int primaryEggColor, int secondaryEggColor) {
		addMapping(entityClass, entityName, entityID, primaryEggColor, secondaryEggColor);
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, Aether.instance, 80, 3, true);
	}

	private static void addMapping(Class<?> entityClass, String entityName, int entityID, int primaryEggColor, int secondaryEggColor) {
		if (IDtoClassMapping.containsKey(Integer.valueOf(entityID))) {
			throw new IllegalArgumentException("ID is already registered: " + entityID);
		} else {
			classToStringMapping.put(entityClass, entityName);
			IDtoClassMapping.put(Integer.valueOf(entityID), entityClass);
			classToIDMapping.put(entityClass, Integer.valueOf(entityID));
			stringToIDMapping.put(entityName, Integer.valueOf(entityID));
			ItemAetherSpawnEgg.entityEggs.put(Integer.valueOf(entityID), new AetherEggInfo(entityID, primaryEggColor, secondaryEggColor));
		}
	}

	public static Entity createEntityByID(int id, World p_75616_1_) {
		Entity entity = null;

		try {
			Class<?> oclass = getClassFromID(id);

			if (oclass != null) {
				entity = (Entity) oclass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_75616_1_});
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		if (entity == null) {
			logger.warn("Skipping Aether Entity with id " + id);
		}

		return entity;
	}

	public static int getEntityID(Entity p_75619_0_) {
		Class<?> oclass = p_75619_0_.getClass();
		return classToIDMapping.containsKey(oclass) ? ((Integer) classToIDMapping.get(oclass)).intValue() : -1;
	}

	public static Class<?> getClassFromID(int p_90035_0_) {
		return (Class<?>) IDtoClassMapping.get(Integer.valueOf(p_90035_0_));
	}

	public static String getStringFromID(int p_75617_0_) {
		Class<?> oclass = getClassFromID(p_75617_0_);

		return oclass != null ? (String) classToStringMapping.get(oclass) : null;
	}

	public static class AetherEggInfo
	{
		public final int spawnedID;
		public final int primaryColor;
		public final int secondaryColor;

		public AetherEggInfo(int spawnedID, int primaryColor, int secondaryColor)
		{
			this.spawnedID = spawnedID;
			this.primaryColor = primaryColor;
			this.secondaryColor = secondaryColor;
		}
	}

}