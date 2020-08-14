package com.gildedgames.the_aether.items.tools;

import com.gildedgames.the_aether.entities.block.EntityFloatingBlock;
import com.gildedgames.the_aether.items.util.EnumAetherToolType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class ItemGravititeTool extends ItemAetherTool {

	public ItemGravititeTool(float damage, EnumAetherToolType toolType) {
		super(damage, ToolMaterial.EMERALD, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Item.getItemFromBlock(BlocksAether.enchanted_gravitite);
	}

	@Override
	public boolean onItemUse(ItemStack heldItem, EntityPlayer player, World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		EntityFloatingBlock entity = new EntityFloatingBlock(world, x, y, z, block, meta);

		if ((this.getDigSpeed(heldItem, block, meta) == this.efficiencyOnProperMaterial || ForgeHooks.isToolEffective(heldItem, block, meta)) && world.isAirBlock(x, y + 1, z)) {
			if (world.getTileEntity(x, y, z) != null || world.getBlock(x, y, z).getBlockHardness(world, x, y, z) == -1.0F) {
				return false;
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(entity);
				world.setBlockToAir(x, y, z);
			}

			heldItem.damageItem(4, player);
		}

		return true;
	}

}