package com.legacy.aether.items.tools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.util.EnumAetherToolType;

public class ItemValkyrieTool extends ItemAetherTool 
{

	public ItemValkyrieTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }
}