package com.legacy.aether.world.storage.loot.functions;

import java.util.Random;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.legacy.aether.Aether;
import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.moa.AetherMoaType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

public class SetMoaType extends LootFunction
{
	private final ResourceLocation moaTypeId;
	
	protected SetMoaType(ResourceLocation moaTypeIdIn, LootCondition[] conditionsIn)
	{
		super(conditionsIn);
		this.moaTypeId = moaTypeIdIn;
	}

	@Override
	public ItemStack apply(ItemStack stack, Random rand, LootContext context)
	{
		int typeId = getMoaTypeId();
		
		if(typeId == -1) {
			return stack;
		}
		
		NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
        }
        
        nbttagcompound.setInteger("typeId", typeId);
        
        stack.setTagCompound(nbttagcompound);
        return stack;
	}

	private int getMoaTypeId() {
		AetherMoaType type = AetherAPI.getInstance().getMoaType(moaTypeId);
		
		if (type == null)
		{
			return -1;
		}
		else
		{
			return AetherAPI.getInstance().getMoaTypeId(type);
		}
	}
	
	public static class Serializer extends LootFunction.Serializer<SetMoaType>
	{

		protected Serializer()
		{
			super(Aether.locate("set_moa_type"), SetMoaType.class);
		}

		@Override
		public void serialize(JsonObject object, SetMoaType function, JsonSerializationContext serializationContext)
		{
			object.addProperty("moa_type", function.moaTypeId.toString());
		}

		@Override
		public SetMoaType deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
		{
			String type = JsonUtils.getString(object, "moa_type");
			if (type.indexOf(':') == -1)
			{
				return new SetMoaType(Aether.locate(type), conditionsIn);
			}
			else
			{
				return new SetMoaType(new ResourceLocation(type), conditionsIn);
			}
		}
		
	}
}
