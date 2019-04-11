package com.legacy.aether.world.storage.loot.conditions;

import net.minecraft.world.storage.loot.conditions.LootConditionManager;

public class LootConditionsAether
{

	public static void initialization()
	{
		LootConditionManager.registerCondition(new ValkyrieCapeEnabled.Serializer());
	}
	
}
