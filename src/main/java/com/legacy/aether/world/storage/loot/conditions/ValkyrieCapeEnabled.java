package com.legacy.aether.world.storage.loot.conditions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.legacy.aether.Aether;
import com.legacy.aether.AetherConfig;

import net.minecraft.util.JsonUtils;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class ValkyrieCapeEnabled implements LootCondition
{
	private final boolean enabled;
	
	public ValkyrieCapeEnabled(boolean enabledIn)
	{
		this.enabled = enabledIn;
	}
	
	@Override
	public boolean testCondition(Random rand, LootContext context)
	{
		return AetherConfig.gameplay_changes.valkyrie_cape == enabled;
	}
	
	public static class Serializer extends LootCondition.Serializer<ValkyrieCapeEnabled>
	{

		protected Serializer()
		{
			super(Aether.locate("valkyrie_cape_enabled"), ValkyrieCapeEnabled.class);
		}

		@Override
		public void serialize(JsonObject json, ValkyrieCapeEnabled value, JsonSerializationContext context)
		{
			json.addProperty("enabled", value.enabled);
		}

		@Override
		public ValkyrieCapeEnabled deserialize(JsonObject json, JsonDeserializationContext context)
		{
			return new ValkyrieCapeEnabled(json.has("enabled")? JsonUtils.getBoolean(json, "enabled") : true);
		}
		
	}

}
