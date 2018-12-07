package com.legacy.aether.client.renders;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;

import com.legacy.aether.client.renders.block.AetherFlowerRenderer;
import com.legacy.aether.client.renders.block.BerryBushRenderer;
import com.legacy.aether.client.renders.block.TreasureChestBlockRenderer;
import com.legacy.aether.client.renders.entity.AechorPlantRenderer;
import com.legacy.aether.client.renders.entity.AerbunnyRenderer;
import com.legacy.aether.client.renders.entity.AerwhaleRenderer;
import com.legacy.aether.client.renders.entity.CockatriceRenderer;
import com.legacy.aether.client.renders.entity.CrystalRenderer;
import com.legacy.aether.client.renders.entity.DartBaseRenderer;
import com.legacy.aether.client.renders.entity.FireMinionRenderer;
import com.legacy.aether.client.renders.entity.FloatingBlockRenderer;
import com.legacy.aether.client.renders.entity.FlyingCowRenderer;
import com.legacy.aether.client.renders.entity.HammerProjectileRenderer;
import com.legacy.aether.client.renders.entity.LightningKnifeRenderer;
import com.legacy.aether.client.renders.entity.MimicRenderer;
import com.legacy.aether.client.renders.entity.MiniCloudRenderer;
import com.legacy.aether.client.renders.entity.MoaRenderer;
import com.legacy.aether.client.renders.entity.ParachuteRenderer;
import com.legacy.aether.client.renders.entity.PhoenixArrowRenderer;
import com.legacy.aether.client.renders.entity.PhygRenderer;
import com.legacy.aether.client.renders.entity.SentryRenderer;
import com.legacy.aether.client.renders.entity.SheepuffRenderer;
import com.legacy.aether.client.renders.entity.SliderRenderer;
import com.legacy.aether.client.renders.entity.SunSpiritRenderer;
import com.legacy.aether.client.renders.entity.SwetRenderer;
import com.legacy.aether.client.renders.entity.TNTPresentRenderer;
import com.legacy.aether.client.renders.entity.ValkyrieQueenRenderer;
import com.legacy.aether.client.renders.entity.ValkyrieRenderer;
import com.legacy.aether.client.renders.entity.WhirlwindRenderer;
import com.legacy.aether.client.renders.entity.ZephyrRenderer;
import com.legacy.aether.client.renders.entity.ZephyrSnowballRenderer;
import com.legacy.aether.client.renders.items.PhoenixBowRenderer;
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
import com.legacy.aether.entities.projectile.EntityZephyrSnowball;
import com.legacy.aether.entities.projectile.crystals.EntityCrystal;
import com.legacy.aether.entities.projectile.darts.EntityDartBase;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.tileentity.TileEntityTreasureChest;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RendersAether
{

	public static void initialization()
	{
		/* Misc */
		register(EntityHammerProjectile.class, new HammerProjectileRenderer());
		register(EntityFloatingBlock.class, new FloatingBlockRenderer());
		register(EntityParachute.class, new ParachuteRenderer());
		register(EntityZephyrSnowball.class, new ZephyrSnowballRenderer());
		register(EntityPhoenixArrow.class, new PhoenixArrowRenderer());
		register(EntityLightningKnife.class, new LightningKnifeRenderer());

		/* Darts */
		register(EntityDartBase.class, new DartBaseRenderer());

		/* Crystals */
		register(EntityCrystal.class, new CrystalRenderer());

		/* Bosses */
		register(EntitySlider.class, new SliderRenderer());
		register(EntityValkyrieQueen.class, new ValkyrieQueenRenderer());
		register(EntitySunSpirit.class, new SunSpiritRenderer());

		/* Hostile */
		register(EntityMimic.class, new MimicRenderer());
		register(EntitySentry.class, new SentryRenderer());
		register(EntityAechorPlant.class, new AechorPlantRenderer());
		register(EntityFireMinion.class, new FireMinionRenderer());
		register(EntityZephyr.class, new ZephyrRenderer());
		register(EntityValkyrie.class, new ValkyrieRenderer());
		register(EntityCockatrice.class, new CockatriceRenderer());

		/* Passive */
		register(EntityMoa.class, new MoaRenderer());
		register(EntityPhyg.class, new PhygRenderer());
		register(EntityFlyingCow.class, new FlyingCowRenderer());
		register(EntitySheepuff.class, new SheepuffRenderer());
		register(EntityAerwhale.class, new AerwhaleRenderer());
		register(EntityAerbunny.class, new AerbunnyRenderer());
		register(EntitySwet.class, new SwetRenderer());
		register(EntityMiniCloud.class, new MiniCloudRenderer());
		register(EntityTNTPresent.class, new TNTPresentRenderer());
		register(EntityWhirlwind.class, new WhirlwindRenderer());

		MinecraftForgeClient.registerItemRenderer(ItemsAether.phoenix_bow, new PhoenixBowRenderer());
		RenderingRegistry.registerBlockHandler(new BerryBushRenderer());
		RenderingRegistry.registerBlockHandler(new TreasureChestBlockRenderer());
		RenderingRegistry.registerBlockHandler(new AetherFlowerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChest.class, new TreasureChestRenderer());
	}

	public static void register(Class<? extends Entity> entityClass, Render render)
	{
		RenderingRegistry.registerEntityRenderingHandler(entityClass, render);
	}
}