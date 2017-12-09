package com.legacy.aether.client.renders.items.definitions;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

import com.legacy.aether.Aether;

public class NotchHammerDefinition implements ItemMeshDefinition
{

	public ModelResourceLocation notch_hammer, notch;

	public NotchHammerDefinition()
	{
		this.notch_hammer = new ModelResourceLocation(Aether.modAddress() + "notch_hammer", "inventory");
		this.notch = new ModelResourceLocation(Aether.modAddress() + "hammer_projectile", "inventory");
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) 
	{
		if (stack.getItemDamage() == 1565)
		{
			return this.notch;
		}

		return this.notch_hammer;
	}

}