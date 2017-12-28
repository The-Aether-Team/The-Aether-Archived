package com.legacy.aether.client.renders.items.definitions;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

import com.legacy.aether.Aether;

public class NotchHammerDefinition implements ItemMeshDefinition
{

	public ModelResourceLocation notch_hammer, notch, jeb_hammer;

	public NotchHammerDefinition()
	{
		this.notch_hammer = new ModelResourceLocation(Aether.modAddress() + "notch_hammer", "inventory");
		this.notch = new ModelResourceLocation(Aether.modAddress() + "hammer_projectile", "inventory");
		this.jeb_hammer = new ModelResourceLocation(Aether.modAddress() + "jeb_hammer", "inventory");
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) 
	{
		if (stack.getItemDamage() == 1565)
		{
			return this.notch;
		}
		
		if (stack.getDisplayName().toLowerCase().equals("hammer of jeb"))
		{
			return this.jeb_hammer;
		}
		else
		{
			return this.notch_hammer;
		}
	}

}