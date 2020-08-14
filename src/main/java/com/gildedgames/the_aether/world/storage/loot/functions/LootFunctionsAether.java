package com.gildedgames.the_aether.world.storage.loot.functions;

import net.minecraft.world.storage.loot.functions.LootFunctionManager;

public class LootFunctionsAether
{

	public static void initialization()
	{
		LootFunctionManager.registerFunction(new SetMoaType.Serializer());
	}
	
}
