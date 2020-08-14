package com.gildedgames.the_aether.items.weapons;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemZaniteSword extends ItemSword {

	public float[] level = new float[]{3.0F, 4.0F, 5.0F, 6.0F, 7.0F};

	public ItemZaniteSword() {
		super(ToolMaterial.IRON);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
		return null;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

		if (stack.getItem() instanceof ItemZaniteSword) {
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double) this.calculateIncrease(stack), 0));
		}

		return multimap;
	}

	private float calculateIncrease(ItemStack tool) {
		int current = tool.getItemDamage();

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
	}

	private boolean isBetween(int max, int origin, int min) {
		return origin <= max && origin >= min ? true : false;
	}

	@Override
	public boolean getIsRepairable(ItemStack repairingItem, ItemStack material) {
		return material.getItem() == ItemsAether.zanite_gemstone;
	}

	@Override
	public float getDigSpeed(ItemStack itemstack, Block block, int meta) {
		return super.getDigSpeed(itemstack, block, meta) * (2.0F * (float) itemstack.getItemDamage() / (float) itemstack.getMaxDamage() + 0.5F);
	}

}