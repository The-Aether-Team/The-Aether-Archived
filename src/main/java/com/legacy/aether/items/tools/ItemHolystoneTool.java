package com.legacy.aether.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.util.EnumAetherToolType;

public class ItemHolystoneTool extends ItemAetherTool {

	public ItemHolystoneTool(EnumAetherToolType toolType) {
		super(ToolMaterial.STONE, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Item.getItemFromBlock(BlocksAether.holystone);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLiving) {
		if (!world.isRemote && world.rand.nextInt(100) <= 5) {
			EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(ItemsAether.ambrosium_shard, 1));

			world.spawnEntityInWorld(entityItem);
		}

		return super.onBlockDestroyed(stack, world, block, x, y, z, entityLiving);
	}

}