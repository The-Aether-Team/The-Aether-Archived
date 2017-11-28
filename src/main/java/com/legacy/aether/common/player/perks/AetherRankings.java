package com.legacy.aether.common.player.perks;

import java.util.HashMap;
import java.util.UUID;

public class AetherRankings 
{

	public static HashMap<String, UUID> ranks = new HashMap<String, UUID>();

	public static void initialization()
	{
		//Developer
		addDeveloperRank("6a0e8505-1556-4ee9-bec0-6af32f05888d"); //115kino

		//Gilded Games
		addGGRank("13655ac1-584d-4785-b227-650308195121"); // Brandon Pearce
		addGGRank("3c0e4411-3421-40bd-b092-056d3e99b98a"); // Oscar Payn
		addGGRank("dc4cf9b2-f601-4eb4-9436-2924836b9f42"); // Jaryt Bustard
		addGGRank("ffb94179-dd54-400d-9ece-834720cd7be9"); // Collin Soares

		//Retired Gilded Games
		addRetiredGGRank("4bfb28a3-005d-4fc9-9238-a55c6c17b575"); // Jon Lachney
		addRetiredGGRank("2afd6a1d-1531-4985-a104-399c0c19351d"); // Brandon Potts
		addRetiredGGRank("b5ee3d5d-2ad7-4642-b9d4-6b041ad600a4"); // Emile van Krieken

		//Celebrities
		addCelebrityRank("0e305085-6ef0-4e46-a7b0-18e78827c44b"); // Mr360Games
		addCelebrityRank("5f820c39-5883-4392-b174-3125ac05e38c"); // CaptainSparklez
		addCelebrityRank("8d945389-6105-4a8d-8be7-088da387d173"); // ClashJTM
		addCelebrityRank("20073cb8-a092-47e2-9a60-bca856e62faf"); // ChimneySwift
		addCelebrityRank("0c063bfd-3521-413d-a766-50be1d71f00e"); // AntVenom

		//Tester
		addTesterRank("1d680bb6-2a9a-4f25-bf2f-a1af74361d69"); // KingPhygieBoo
		addTesterRank("cf51ef47-04a8-439a-aa41-47d871b0b837"); // Kito
		addTesterRank("93822537-d79f-4672-b9a8-04aae16131d2"); // KidOfTheForest
		addTesterRank("f2914dae-441a-4254-aa5c-2ec4d917b7a6"); // Jesterguy
		addTesterRank("869aed85-9dc0-4187-92d7-6064c202a844");
		addTesterRank("8ab9311e-6b8d-4633-80d5-e1798b1c6a96");

		//Retired
		addRetiredRank("6e8be0ba-e4bb-46af-aea8-2c1f5eec5bc2"); // Brendan Freeman
		addRetiredRank("5f112332-0993-4f52-a5ab-9a55dc3173cb"); // JorgeQ
	}

	public static UUID getUUID(String string)
	{
		return UUID.fromString(string);
	}

	public static boolean isRankedPlayer(UUID uuid)
	{
		if (ranks.get("Celebrity-" + uuid.toString()) != null)
		{
			return true;
		}
		else if (ranks.get("Aether Legacy Developer-" + uuid.toString()) != null)
		{
			return true;
		}
		else if (ranks.get("Aether Legacy Tester-" + uuid.toString()) != null)
		{
			return true;
		}
		else if (ranks.get("Gilded Games-" + uuid.toString()) != null)
		{
			return true;
		}
		else if (ranks.get("Retired Developer-" + uuid.toString()) != null)
		{
			return true;
		}
		else if (ranks.get("Retired Gilded Games-" + uuid.toString()) != null)
		{
			return true;
		}

		return false;
	}

	public static void addCelebrityRank(String uuid)
	{
		ranks.put("Celebrity-" + uuid, getUUID(uuid));
	}

	public static void addDeveloperRank(String uuid)
	{
		ranks.put("Aether Legacy Developer-" + uuid, getUUID(uuid));
	}

	public static void addTesterRank(String uuid)
	{
		ranks.put("Aether Legacy Tester-" + uuid, getUUID(uuid));
	}

	public static void addGGRank(String uuid)
	{
		ranks.put("Gilded Games-" + uuid, getUUID(uuid));
	}

	public static void addRetiredGGRank(String uuid)
	{
		ranks.put("Retired Gilded Games-" + uuid, getUUID(uuid));
	}

	public static void addRetiredRank(String uuid)
	{
		ranks.put("Retired Developer-" + uuid, getUUID(uuid));
	}

}