package com.legacy.aether.blocks.natural;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.tools.ItemAetherTool;
import com.legacy.aether.items.tools.ItemSkyrootTool;
import com.legacy.aether.items.util.EnumAetherToolType;

public class BlockAetherOre extends Block {

	public BlockAetherOre(int level) {
		super(Material.rock);

		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeStone);
		this.setHarvestLevel("pickaxe", level);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);

		ItemStack stack = player.getCurrentEquippedItem();

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack) > 0) {
			super.harvestBlock(worldIn, player, x, y, z, meta);

			return;
		}

		if (stack != null && stack.getItem() instanceof ItemSkyrootTool && ((ItemAetherTool) stack.getItem()).toolType == EnumAetherToolType.PICKAXE) {
			for (int i = 0; i < 2; ++i) {
				this.dropBlockAsItem(worldIn, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
			}
		} else {
			super.harvestBlock(worldIn, player, x, y, z, meta);
		}
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return this == BlocksAether.zanite_ore ? ItemsAether.zanite_gemstone : ItemsAether.ambrosium_shard;
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random) {
		if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, fortune)) {
			int j = random.nextInt(fortune + 2) - 1;

			if (j < 0) {
				j = 0;
			}

			return this.quantityDropped(random) * (j + 1);
		} else {
			return this.quantityDropped(random);
		}
	}

	@Override
	public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_) {
		Random random = new Random();

		if (this.getItemDropped(p_149690_5_, random, p_149690_7_) != Item.getItemFromBlock(this)) {
			int amount = 0;

			if (this == BlocksAether.ambrosium_ore) {
				amount = MathHelper.getRandomIntegerInRange(random, 0, 2);
			} else if (this == BlocksAether.zanite_ore) {
				amount = MathHelper.getRandomIntegerInRange(random, 2, 5);
			}

			return amount;
		}

		return 0;
	}

}