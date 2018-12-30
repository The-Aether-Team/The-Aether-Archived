package com.legacy.aether.entities;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.legacy.aether.Aether;
import com.legacy.aether.entities.block.EntityFloatingBlock;
import com.legacy.aether.entities.block.EntityTNTPresent;
import com.legacy.aether.entities.bosses.EntityFireMinion;
import com.legacy.aether.entities.bosses.EntityValkyrie;
import com.legacy.aether.entities.bosses.slider.EntitySlider;
import com.legacy.aether.entities.bosses.sun_spirit.EntitySunSpirit;
import com.legacy.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.legacy.aether.entities.hostile.EntityAechorPlant;
import com.legacy.aether.entities.hostile.EntityCockatrice;
import com.legacy.aether.entities.hostile.EntityMimic;
import com.legacy.aether.entities.hostile.EntitySentry;
import com.legacy.aether.entities.hostile.EntityWhirlwind;
import com.legacy.aether.entities.hostile.EntityZephyr;
import com.legacy.aether.entities.passive.EntityAerwhale;
import com.legacy.aether.entities.passive.EntityMiniCloud;
import com.legacy.aether.entities.passive.EntitySheepuff;
import com.legacy.aether.entities.passive.mountable.EntityAerbunny;
import com.legacy.aether.entities.passive.mountable.EntityFlyingCow;
import com.legacy.aether.entities.passive.mountable.EntityMoa;
import com.legacy.aether.entities.passive.mountable.EntityParachute;
import com.legacy.aether.entities.passive.mountable.EntityPhyg;
import com.legacy.aether.entities.passive.mountable.EntitySwet;
import com.legacy.aether.entities.projectile.EntityHammerProjectile;
import com.legacy.aether.entities.projectile.EntityLightningKnife;
import com.legacy.aether.entities.projectile.EntityPhoenixArrow;
import com.legacy.aether.entities.projectile.EntityPoisonNeedle;
import com.legacy.aether.entities.projectile.EntityZephyrSnowball;
import com.legacy.aether.entities.projectile.crystals.EntityCrystal;
import com.legacy.aether.entities.projectile.darts.EntityDartEnchanted;
import com.legacy.aether.entities.projectile.darts.EntityDartGolden;
import com.legacy.aether.entities.projectile.darts.EntityDartPoison;
import com.legacy.aether.entities.util.AetherMoaTypes;
import com.legacy.aether.entities.util.EntityAetherItem;
import com.legacy.aether.items.ItemAetherSpawnEgg;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitiesAether {

	public static Map<Class<?>, String> classToStringMapping = new HashMap<Class<?>, String>();

	public static Map<Integer, Class<?>> IDtoClassMapping = new HashMap<Integer, Class<?>>();

	private static Map<Class<?>, Integer> classToIDMapping = new HashMap<Class<?>, Integer>();

	private static Map<String, Integer> stringToIDMapping = new HashMap<String, Integer>();

	private static final Logger logger = LogManager.getLogger();

	public static void initialization() {
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