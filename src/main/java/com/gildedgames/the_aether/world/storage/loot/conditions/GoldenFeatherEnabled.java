package com.gildedgames.the_aether.world.storage.loot.conditions;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

public class GoldenFeatherEnabled implements LootCondition
{
    private final boolean enabled;

    public GoldenFeatherEnabled(boolean enabledIn)
    {
        this.enabled = enabledIn;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context)
    {
        return AetherConfig.gameplay_changes.golden_feather == enabled;
    }

    public static class Serializer extends LootCondition.Serializer<GoldenFeatherEnabled>
    {

        protected Serializer()
        {
            super(Aether.locate("golden_feather_enabled"), GoldenFeatherEnabled.class);
        }

        @Override
        public void serialize(JsonObject json, GoldenFeatherEnabled value, JsonSerializationContext context)
        {
            json.addProperty("enabled", value.enabled);
        }

        @Override
        public GoldenFeatherEnabled deserialize(JsonObject json, JsonDeserializationContext context)
        {
            return new GoldenFeatherEnabled(json.has("enabled")? JsonUtils.getBoolean(json, "enabled") : true);
        }

    }

}
