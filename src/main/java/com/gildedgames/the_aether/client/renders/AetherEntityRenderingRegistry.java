package com.gildedgames.the_aether.client.renders;

import com.gildedgames.the_aether.client.renders.entities.*;
import com.gildedgames.the_aether.client.renders.entities.layer.AccessoriesLayer;
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
import com.gildedgames.the_aether.entities.projectile.EntityHammerProjectile;
import com.gildedgames.the_aether.entities.projectile.EntityLightningKnife;
import com.gildedgames.the_aether.entities.projectile.EntityPhoenixArrow;
import com.gildedgames.the_aether.entities.projectile.EntityZephyrSnowball;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityFireBall;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityIceyBall;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityThunderBall;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartBase;
import com.gildedgames.the_aether.tile_entities.TileEntityChestMimic;
import com.gildedgames.the_aether.tile_entities.TileEntityTreasureChest;
import com.gildedgames.the_aether.client.renders.entities.*;
import com.gildedgames.the_aether.client.renders.entities.layer.LayerElytraAether;
import com.gildedgames.the_aether.tile_entities.TileEntitySkyrootBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.client.renders.entities.projectile.DartBaseRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.FireBallRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.HammerProjectileRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.IceyBallRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.LightningKnifeRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.PhoenixArrowRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.ThunderBallRenderer;
import com.gildedgames.the_aether.client.renders.entities.projectile.ZephyrSnowballRenderer;
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
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.HashSet;
import java.util.List;

public class AetherEntityRenderingRegistry 
{

	public static void initialize()
	{
		/* Misc */
		register(EntityHammerProjectile.class, HammerProjectileRenderer.class);
		register(EntityFloatingBlock.class, FloatingBlockRenderer.class);
		register(EntityParachute.class, ParachuteRenderer.class);
		register(EntityZephyrSnowball.class, ZephyrSnowballRenderer.class);
		register(EntityPhoenixArrow.class, PhoenixArrowRenderer.class);
		register(EntityLightningKnife.class, LightningKnifeRenderer.class);

		/* Darts */
		register(EntityDartBase.class, DartBaseRenderer.class);

		/* Crystals */
		register(EntityFireBall.class, FireBallRenderer.class);
		register(EntityIceyBall.class, IceyBallRenderer.class);
		register(EntityThunderBall.class, ThunderBallRenderer.class);

		/* Bosses */
		register(EntitySlider.class, SliderRenderer.class);
		register(EntityValkyrieQueen.class, ValkyrieQueenRenderer.class);
		register(EntitySunSpirit.class, SunSpiritRenderer.class);

		/* Hostile */
		register(EntityMimic.class, MimicRenderer.class);
		register(EntitySentry.class, SentryRenderer.class);
		register(EntityAechorPlant.class, AechorPlantRenderer.class);
		register(EntityFireMinion.class, FireMinionRenderer.class);
		register(EntityZephyr.class, ZephyrRenderer.class);
		register(EntityValkyrie.class, ValkyrieRenderer.class);
		register(EntityCockatrice.class, CockatriceRenderer.class);

		/* Passive */
		register(EntityMoa.class, MoaRenderer.class);
		register(EntityPhyg.class, PhygRenderer.class);
		register(EntityFlyingCow.class, FlyingCowRenderer.class);
		register(EntitySheepuff.class, SheepuffRenderer.class);
		register(EntityAerwhale.class, AerwhaleRenderer.class);
		register(EntityAerbunny.class, AerbunnyRenderer.class);
		register(EntitySwet.class, SwetRenderer.class);
		register(EntityMiniCloud.class, MiniCloudRenderer.class);
		register(EntityTNTPresent.class, TNTPresentRenderer.class);
		register(EntityWhirlwind.class, WhirlwindRenderer.class);
	}

	@SuppressWarnings("deprecation")
	public static void registerTileEntities()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChest.class, new TreasureChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestMimic.class, new ChestMimicRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootBed.class, new SkyrootBedRenderer());

		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlocksAether.treasure_chest), 0, TileEntityTreasureChest.class);
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlocksAether.chest_mimic), 0, TileEntityChestMimic.class);
	}

	public static void initializePlayerLayers()
	{
		for (RenderLivingBase<?> playerRender : new HashSet<>(Minecraft.getMinecraft().getRenderManager().getSkinMap().values()))
		{
			playerRender.addLayer(new AccessoriesLayer((ModelPlayer) playerRender.getMainModel()));
			playerRender.addLayer(new LayerElytraAether(playerRender));

			List<LayerRenderer<EntityLivingBase>> fieldOutside = ObfuscationReflectionHelper.getPrivateValue(RenderLivingBase.class, playerRender, "layerRenderers", "field_177097_h");
			fieldOutside.removeIf(layerRenderer -> layerRenderer.getClass() == LayerElytra.class);
		}
	}

	public static <ENTITY extends Entity> void register(Class<ENTITY> classes, final Class<? extends Render<ENTITY>> render)
	{
		RenderingRegistry.registerEntityRenderingHandler(classes, new IRenderFactory<ENTITY>() {

			@Override
			public Render<ENTITY> createRenderFor(RenderManager manager) 
			{
				try
				{
					return render.getConstructor(RenderManager.class).newInstance(manager);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				return null;
			}
		});
	}

}