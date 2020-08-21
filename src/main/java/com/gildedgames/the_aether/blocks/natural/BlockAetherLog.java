package com.gildedgames.the_aether.blocks.natural;

import java.util.ArrayList;
import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.tools.*;
import com.gildedgames.the_aether.items.util.EnumAetherToolType;
import com.gildedgames.the_aether.items.tools.*;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.event.ForgeEventFactory;

public class BlockAetherLog extends BlockLog {

	public BlockAetherLog() {
		super();
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);

		int size = meta == 0 ? 2 : 1;

		ItemStack stack = player.getCurrentEquippedItem();


		if (this.canSilkHarvest(worldIn, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player))
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ItemStack itemstack = this.createStackedBlock(meta);

			if (itemstack != null)
			{
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, worldIn, this, x, y, z, meta, 0, 1.0f, true, player);
			for (ItemStack is : items)
			{
				this.dropBlockAsItem(worldIn, x, y, z, is);
			}
		}
		else
		{
			if (stack != null && ((stack.getItem() instanceof ItemAetherTool && ((ItemAetherTool) stack.getItem()).toolType == EnumAetherToolType.AXE) || stack.getItem() == Items.diamond_axe)) {
				if (stack.getItem() instanceof ItemZaniteTool || stack.getItem() instanceof ItemGravititeTool || stack.getItem() instanceof ItemValkyrieTool || stack.getItem() == Items.diamond_axe) {
					if (this == BlocksAether.golden_oak_log) {
						this.dropBlockAsItem(worldIn, x, y, z, new ItemStack(ItemsAether.golden_amber, 1 + worldIn.rand.nextInt(2)));
					}

					this.dropBlockAsItem(player.worldObj, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
				} else if (stack.getItem() instanceof ItemSkyrootTool) {
					for (int i = 0; i < size; ++i) {
						this.dropBlockAsItem(player.worldObj, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
					}
				} else {
					this.dropBlockAsItem(player.worldObj, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
				}
			} else {
				super.harvestBlock(worldIn, player, x, y, z, meta);
			}
		}
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return Item.getItemFromBlock(BlocksAether.skyroot_log);
	}

	@Override
	public int damageDropped(int meta) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		this.field_150167_a = new IIcon[1];
		this.field_150166_b = new IIcon[1];

		for (int i = 0; i < this.field_150167_a.length; ++i) {
			this.field_150167_a[i] = registry.registerIcon(this.getTextureName() + "_side");
			this.field_150166_b[i] = registry.registerIcon(this.getTextureName() + "_top");
		}
	}

}