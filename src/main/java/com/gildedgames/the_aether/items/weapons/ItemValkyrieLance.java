package com.gildedgames.the_aether.items.weapons;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class ItemValkyrieLance extends ItemSword {

	public ItemValkyrieLance() {
		super(ToolMaterial.EMERALD);

		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.none;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public boolean getIsRepairable(ItemStack repairingItem, ItemStack material) {
		return false;
	}

}