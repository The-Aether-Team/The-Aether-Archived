package com.legacy.aether.entities.util;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.properties.EntityProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.legacy.aether.AetherConfig;

public class PlayerStartupLoot implements EntityProperty
{

	private final boolean check;

	public PlayerStartupLoot(boolean checking)
	{
		this.check = checking;
	}

	@Override
	public boolean testProperty(Random random, Entity entityIn)
	{
		return !AetherConfig.gameplay_changes.disable_startup_loot;
	}

	public static class Serializer extends EntityProperty.Serializer<PlayerStartupLoot>
	{

		public Serializer()
		{
			super(new ResourceLocation("aether_loot_spawn"), PlayerStartupLoot.class);
		}

		@Override
		public JsonElement serialize(PlayerStartupLoot property, JsonSerializationContext serializationContext)
		{
			return new JsonPrimitive(property.check);
		}

		@Override
		public PlayerStartupLoot deserialize(JsonElement element, JsonDeserializationContext deserializationContext)
		{
			return new PlayerStartupLoot(JsonUtils.getBoolean(element, "aether_loot_spawn"));
		}

	}

}