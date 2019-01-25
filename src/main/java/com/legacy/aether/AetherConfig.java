package com.legacy.aether;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.config.Configuration;

public class AetherConfig 
{

	private static int max_life_shards;

	private static boolean christmas_content, tallgrass;

	private static int aether_biome_id, aether_dimension_id;

	private static boolean disable_trivia, old_mobs;

	private static boolean skyrootBucketOnly;

	public static void init(File location)
	{
		File newFile = new File(location + "/aether" + "/AetherI.cfg");

		try
		{
			newFile.createNewFile();
		}
		catch (IOException e)
		{

		}

		Configuration config = new Configuration(newFile);

		config.load();

		christmas_content = config.get("Aether World Generation", "Christmas Content", false).getBoolean(false);
		tallgrass = config.get("Aether World Generation", "Enable Tall Grass", false).getBoolean(false);

		aether_dimension_id = config.get("World Identification", "Aether Dimension ID", 4).getInt(4);
		aether_biome_id = config.get("World Identification", "Aether Biome ID", 127).getInt(127);

		skyrootBucketOnly = config.get("Misc", "Activate portal with only Skyroot bucket", false).getBoolean(false);

		disable_trivia = config.get("Trivia", "Disable random trivia", false).getBoolean(false);
		
		old_mobs = config.get("Misc", "Enable Legacy Visuals", false).getBoolean(false);

		max_life_shards = config.get("Gameplay", "Max Life Shards", 10).getInt(10);

		config.save();
	}

	public static int getAetherDimensionID()
	{
		return AetherConfig.aether_dimension_id;
	}

	public static int getAetherBiomeID()
	{
		return AetherConfig.aether_biome_id;
	}

	public static int getMaxLifeShards()
	{
		return AetherConfig.max_life_shards;
	}

	public static boolean triviaDisabled()
	{
		return AetherConfig.disable_trivia;
	}
	
	public static boolean oldMobsEnabled()
	{
		return AetherConfig.old_mobs;
	}


	public static boolean shouldLoadHolidayContent()
	{
		return AetherConfig.christmas_content;
	}
	
	public static boolean tallgrassEnabled()
	{
		return AetherConfig.tallgrass;
	}
	
	public static boolean activateOnlyWithSkyroot()
	{
		return AetherConfig.skyrootBucketOnly;
	}

}