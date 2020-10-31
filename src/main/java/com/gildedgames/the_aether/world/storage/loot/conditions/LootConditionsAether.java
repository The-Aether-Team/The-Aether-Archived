package com.gildedgames.the_aether.world.storage.loot.conditions;

import net.minecraft.world.storage.loot.conditions.LootConditionManager;

public class LootConditionsAether
{

	public static void initialization()
	{
		LootConditionManager.registerCondition(new ValkyrieCapeEnabled.Serializer());
		LootConditionManager.registerCondition(new GoldenFeatherEnabled.Serializer());
	}
	
}
