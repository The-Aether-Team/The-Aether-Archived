package com.gildedgames.the_aether;

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

	public static final WorldGenOptions world_gen = new WorldGenOptions();

	public static class WorldGenOptions
	{
		@Config.Comment("Enables natural christmas decor")
		public boolean christmas_time = false;
		
		@Config.Comment("Enables naturally generating tallgrass")
		public boolean tallgrass_enabled = false;
		
		@Config.Comment("Enables natural Pink Aercloud generation")
		public boolean pink_aerclouds = false;
	}
	
	public static final VisualOptions visual_options = new VisualOptions();
	
	public static class VisualOptions
	{
		@Config.Comment("Changes Aether mobs to use their old models, if applicable")
		public boolean legacy_models = false;
		
		@Config.Comment("Aerclouds will use their more saturated colors from later updates")
		public boolean updated_aercloud_colors = false;
		
		@Config.Comment("Disables the random trivia/tips you see during loading screens")
		@Config.RequiresMcRestart
		public boolean trivia_disabled = false;

		@Config.Comment("Enables the Aether Menu")
		public boolean menu_enabled = false;

		@Config.Comment("Enables the Aether Menu toggle button")
		public boolean menu_button = true;

		@Config.Comment("Determines whether the Aether b1.7.3 resource pack should be generated.")
		public boolean install_resourcepack = true;

		@Config.Comment("Changes whether the Altar should be named Enchanter or not.")
		public boolean legacy_altar_name = false;
	}
	
	public static final GameplayChanges gameplay_changes = new GameplayChanges();
	
	public static class GameplayChanges
	{
		@Config.Comment("The max amount of life shards that can be used per player")
		public int max_life_shards = 10;
		
		@Config.Comment("If enabled, Aether Portals can only be lit by Skyroot Buckets")
		public boolean skyroot_bucket_only = false;
		
		@Config.Comment("Makes it so you have to eat Ambrosium, instead of just right clicking to heal")
		public boolean ambro_is_edible = false;
		
		@Config.Comment("Swaps the Golden Feather with the Valkyrie Cape in dungeon loot")
		public boolean valkyrie_cape = false;

		@Config.Comment("Disables spawn of Aether Legacy portal for use with portal being provided by another mod.")
		public boolean disable_portal = false;
		
		@Config.Comment("Disables startup loot when entering the Aether")
		public boolean disable_startup_loot = false;

		@Config.Comment("Removes the requirement for a player to be an operator to use the Sun Altar in multiplayer.")
		public boolean sun_altar_multiplayer = false;
		
		@Config.Comment("If disabed, the Sun Spirit's dialog will only show once per world.")
		public boolean repeat_sun_spirit_dialog = true;
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