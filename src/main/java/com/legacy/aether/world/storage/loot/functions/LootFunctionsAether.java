package com.legacy.aether.world.storage.loot.functions;

import net.minecraft.world.storage.loot.functions.LootFunctionManager;

public class LootFunctionsAether
{

	public static void initialization()
	{
		LootFunctionManager.registerFunction(new SetMoaType.Serializer());
	}
	
}
