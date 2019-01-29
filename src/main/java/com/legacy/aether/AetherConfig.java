package com.legacy.aether;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * PLEASE NOTE: IF YOU ARE GOING TO CHANGE ANY EXISTING
 * CONFIGURATION VARIABLES, YOU MUST DELETE YOUR EXISTING
 * aether_legacy.cfg FILE IN THE CONFIG FOLDER OF YOUR
 * TESTING DIRECTORY (normally named "run")
 */
@Config(modid = Aether.modid)
@Config.LangKey(Aether.modid + ".config.title")
public class AetherConfig
{
	public static final Dimension dimension = new Dimension();

	public static class Dimension
	{
		@Config.RangeInt(min=2, max=256)
		@Config.RequiresMcRestart
		@Config.Comment("Set the Dimension ID for the Aether.")
		public int aether_dimension_id = 4;
	}
	
	/*
	 * public static class Biome
	 * {
	 * @Config.RangeInt(min=2, max=256)
	 * @Config.RequiresMcRestart
	 * @Config.Comment("Set the Biome ID for the Aether Highlands.")
	 * public int aether_biome_id = 127;
	 * }
	 */

	public static final WorldGenOptions world_gen = new WorldGenOptions();

	public static class WorldGenOptions
	{
		public final Christmas christmas_content = new Christmas();

		public final TallGrass tall_grass = new TallGrass();

		public final PinkAerclouds pink_aerclouds = new PinkAerclouds();
		
		public static class Christmas
		{
			@Config.Comment("Enables natural christmas decor")
			public boolean christmasTime = false;
		}
		
		public static class TallGrass
		{
			@Config.Comment("Enables naturally generating tallgrass")
			public boolean tallgrassEnabled = false;
		}
		
		public static class PinkAerclouds
		{
			@Config.Comment("Enables natural Pink Aercloud generation")
			public boolean pinkAerclouds = false;
		}
	}
	
	public static final VisualOptions visual_options = new VisualOptions();
	
	public static class VisualOptions
	{
		public final OldMobModels legacy_models = new OldMobModels();
		
		public final UpdatedAercloudColors updated_aerclouds = new UpdatedAercloudColors();
		
		public final DisableTrivia trivia = new DisableTrivia();
		
		public static class OldMobModels
		{
			@Config.Comment("Changes Aether mobs to use their old models, if applicable")
			public boolean legacyModels = false;
		}
		
		public static class UpdatedAercloudColors
		{
			@Config.Comment("Aerclouds will use their more saturated colors from later updates")
			public boolean updatedAercloudColors = false;
		}
		
		public static class DisableTrivia
		{
			@Config.Comment("Disables the random trivia/tips you see during loading screens")
			@Config.RequiresMcRestart
			public boolean triviaDisabled = false;
		}
		
		public static class EnableMenu
		{
			@Config.Comment("Enables the Aether Menu")
			public boolean menuEnabled = false;
		}
	}
	
	public static final GameplayChanges gameplay_changes = new GameplayChanges();
	
	public static class GameplayChanges
	{
		public final LifeShardCount max_shards = new LifeShardCount();
		
		public final SkyrootBucketOnly skyroot_bucket_only = new SkyrootBucketOnly();
		
		public final EdibleAmbro edible_ambrosium = new EdibleAmbro();
		
		public static class LifeShardCount
		{
			@Config.Comment("The max amount of life shards that can be used per player")
			public int maxLifeShards = 10;
		}
		
		public static class SkyrootBucketOnly
		{
			@Config.Comment("If enabled, Aether Portals can only be lit by Skyroot Buckets")
			public boolean skyrootBucketOnly = false;
		}
		
		public static class EdibleAmbro
		{
			@Config.Comment("Makes it so you have to eat Ambrosium, instead of just right clicking to heal")
			//@Config.RequiresMcRestart
			public boolean ambroIsEdible = false;
		}
	}

	@Mod.EventBusSubscriber(modid = Aether.modid)
	private static class EventHandler
	{
		/**
		 * Inject the new values and save to the config file when the config has been changed from the GUI.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if (event.getModID().equals(Aether.modid))
			{
				ConfigManager.sync(Aether.modid, Config.Type.INSTANCE);
			}
		}
	}
}