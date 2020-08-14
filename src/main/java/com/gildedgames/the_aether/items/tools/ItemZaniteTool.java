package com.gildedgames.the_aether.items.tools;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumAetherToolType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemZaniteTool extends ItemAetherTool {

	public float[] level = new float[]{2F, 4F, 6F, 8F, 12F};

	public ItemZaniteTool(float damage, EnumAetherToolType toolType) {
		super(damage, ToolMaterial.IRON, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == ItemsAether.zanite_gemstone;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return this.calculateIncrease(stack, this.toolType.getStrVsBlock(stack, block));
	}

	private float calculateIncrease(ItemStack tool, float original) {
		boolean AllowedCalculations = original != 4.0F ? false : true;
		int current = tool.getItemDamage();

		if (AllowedCalculations) {
			if (isBetween(tool.getMaxDamage(), current, tool.getMaxDamage() - 50)) {
				return level[4];
			} else if (isBetween(tool.getMaxDamage() - 51, current, tool.getMaxDamage() - 110)) {
				return level[3];
			} else if (isBetween(tool.getMaxDamage() - 111, current, tool.getMaxDamage() - 200)) {
				return level[2];
			} else if (isBetween(tool.getMaxDamage() - 201, current, tool.getMaxDamage() - 239)) {
				return level[1];
			} else {
				return level[0];
			}
		} else {
			return 1.0F;
		}
	}

	private boolean isBetween(int max, int origin, int min) {
		return origin <= max && origin >= min ? true : false;
	}

}