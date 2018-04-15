package com.legacy.aether;

import java.io.File;
import java.io.IOException;

import net.minecraftforge.common.config.Configuration;

public class AetherConfig 
{

	private static int max_life_shards;

	private static boolean christmas_content;

	private static int aether_biome_id, aether_dimension_id;

	private static boolean disable_trivia;

	private static boolean skyrootBucketOnly;

	private static boolean developer_mode;

	public static void init(File location)
	{
		File newFile = new File(location + "/aether" + "/Aether_Legacy.cfg");

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

		aether_dimension_id = config.get("World Identification", "Aether Dimension ID", 4).getInt(4);
		aether_biome_id = config.get("World Identification", "Aether Biome ID", 127).getInt(127);

		skyrootBucketOnly = config.get("Misc", "Activate portal with only Skyroot bucket", false).getBoolean(false);

		disable_trivia = config.get("Trivia", "Disable random trivia", false).getBoolean(false);

		max_life_shards = config.get("Gameplay", "Max Life Shards", 10).getInt(10);

		developer_mode = config.get("Developer Options", "Enable extra logging for certain features", false).getBoolean(false);

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

	public static float getMaxLifeShards()
	{
		return (AetherConfig.max_life_shards * 2.0F);
	}

	public static boolean triviaDisabled()
	{
		return AetherConfig.disable_trivia;
	}

	public static boolean shouldLoadHolidayContent()
	{
		return AetherConfig.christmas_content;
	}

	public static boolean activateOnlyWithSkyroot()
	{
		return AetherConfig.skyrootBucketOnly;
	}

	//This method should be before the method below, because if it is below the developerMode() boolean method,
	//I'm assuming that the original configuration developer mode setting will take place regardless of this method.
	public static void autoDeveloperMode(String versionNumber)
	{
		if (versionNumber.contains("dev"))
		{
			developer_mode = true;
			AetherLogger.print("It appears that you are using a development build of Aether Legacy. As such, developer mode has automatically been enabled regardless of configuration setting.");
		}
	}

	public static boolean developerMode()
	{
		return AetherConfig.developer_mode;
	}

}