package com.legacy.aether.client.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.legacy.aether.client.renders.entities.factory.AechorPlantFactory;
import com.legacy.aether.client.renders.entities.factory.AerbunnyFactory;
import com.legacy.aether.client.renders.entities.factory.AerwhaleFactory;
import com.legacy.aether.client.renders.entities.factory.CockatriceFactory;
import com.legacy.aether.client.renders.entities.factory.DartFactory;
import com.legacy.aether.client.renders.entities.factory.FireBallFactory;
import com.legacy.aether.client.renders.entities.factory.FireMinionFactory;
import com.legacy.aether.client.renders.entities.factory.FloatingBlockFactory;
import com.legacy.aether.client.renders.entities.factory.FlyingCowFactory;
import com.legacy.aether.client.renders.entities.factory.HammerProjectileFactory;
import com.legacy.aether.client.renders.entities.factory.IceyBallFactory;
import com.legacy.aether.client.renders.entities.factory.LightningKnifeFactory;
import com.legacy.aether.client.renders.entities.factory.MimicFactory;
import com.legacy.aether.client.renders.entities.factory.MiniCloudFactory;
import com.legacy.aether.client.renders.entities.factory.MoaFactory;
import com.legacy.aether.client.renders.entities.factory.ParachuteFactory;
import com.legacy.aether.client.renders.entities.factory.PhoenixArrowFactory;
import com.legacy.aether.client.renders.entities.factory.PhygFactory;
import com.legacy.aether.client.renders.entities.factory.SentryFactory;
import com.legacy.aether.client.renders.entities.factory.SheepuffFactory;
import com.legacy.aether.client.renders.entities.factory.SliderFactory;
import com.legacy.aether.client.renders.entities.factory.SunSpiritFactory;
import com.legacy.aether.client.renders.entities.factory.SwetFactory;
import com.legacy.aether.client.renders.entities.factory.TNTPresentFactory;
import com.legacy.aether.client.renders.entities.factory.ThunderBallFactory;
import com.legacy.aether.client.renders.entities.factory.ValkyrieFactory;
import com.legacy.aether.client.renders.entities.factory.ValkyrieQueenFactory;
import com.legacy.aether.client.renders.entities.factory.ZephyrFactory;
import com.legacy.aether.client.renders.entities.factory.ZephyrSnowballFactory;
import com.legacy.aether.client.renders.entities.layer.AccessoriesLayer;
import com.legacy.aether.common.blocks.BlocksAether;
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
import com.legacy.aether.common.tile_entities.TileEntityChestMimic;
import com.legacy.aether.common.tile_entities.TileEntityTreasureChest;

public class AetherEntityRenderingRegistry 
{

	public static void initialize()
	{
		/* Misc */
		register(EntityHammerProjectile.class, new HammerProjectileFactory());
		register(EntityFloatingBlock.class, new FloatingBlockFactory());
		register(EntityParachute.class, new ParachuteFactory());
		register(EntityZephyrSnowball.class, new ZephyrSnowballFactory());
		register(EntityPhoenixArrow.class, new PhoenixArrowFactory());
		register(EntityLightningKnife.class, new LightningKnifeFactory());

		/* Darts */
		register(EntityDartEnchanted.class, new DartFactory<EntityDartEnchanted>());
		register(EntityPoisonNeedle.class, new DartFactory<EntityPoisonNeedle>());
		register(EntityDartGolden.class, new DartFactory<EntityDartGolden>());
		register(EntityDartPoison.class, new DartFactory<EntityDartPoison>());

		/* Crystals */
		register(EntityFireBall.class, new FireBallFactory());
		register(EntityIceyBall.class, new IceyBallFactory());
		register(EntityThunderBall.class, new ThunderBallFactory());

		/* Bosses */
		register(EntitySlider.class, new SliderFactory());
		register(EntityValkyrieQueen.class, new ValkyrieQueenFactory());
		register(EntitySunSpirit.class, new SunSpiritFactory());

		/* Hostile */
		register(EntityMimic.class, new MimicFactory());
		register(EntitySentry.class, new SentryFactory());
		register(EntityAechorPlant.class, new AechorPlantFactory());
		register(EntityFireMinion.class, new FireMinionFactory());
		register(EntityZephyr.class, new ZephyrFactory());
		register(EntityValkyrie.class, new ValkyrieFactory());
		register(EntityCockatrice.class, new CockatriceFactory());

		/* Passive */
		register(EntityMoa.class, new MoaFactory());
		register(EntityPhyg.class, new PhygFactory());
		register(EntityFlyingCow.class, new FlyingCowFactory());
		register(EntitySheepuff.class, new SheepuffFactory());
		register(EntityAerwhale.class, new AerwhaleFactory());
		register(EntityAerbunny.class, new AerbunnyFactory());
		register(EntitySwet.class, new SwetFactory());
		register(EntityMiniCloud.class, new MiniCloudFactory());
		register(EntityTNTPresent.class, new TNTPresentFactory());
	}

	@SuppressWarnings("deprecation")
	public static void registerTileEntities()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChest.class, new TreasureChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestMimic.class, new ChestMimicRenderer());

		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlocksAether.treasure_chest), 0, TileEntityTreasureChest.class);
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlocksAether.chest_mimic), 0, TileEntityChestMimic.class);
	}

	public static void initializePlayerLayers()
	{
		RenderPlayer slim_render = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim");
		slim_render.addLayer(new AccessoriesLayer(true, (ModelPlayer) slim_render.getMainModel()));

		RenderPlayer default_render = Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default");
		default_render.addLayer(new AccessoriesLayer(false, (ModelPlayer) default_render.getMainModel()));
	}

	public static <ENTITY extends Entity> void register(Class<ENTITY> classes, IRenderFactory<? super ENTITY> factory)
	{
		RenderingRegistry.registerEntityRenderingHandler(classes, factory);
	}

}