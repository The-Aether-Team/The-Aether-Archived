package com.gildedgames.the_aether.items.tools;

import java.util.Random;
import java.util.Set;

import com.gildedgames.the_aether.items.util.EnumAetherToolType;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Multimap;

public abstract class ItemAetherTool extends ItemTool {

	private float attackDamage;

	private String toolClass;

	public Random random = new Random();

	public EnumAetherToolType toolType;

	public ItemAetherTool(float damage, ToolMaterial toolMaterial, EnumAetherToolType toolType) {
		super(damage, toolMaterial, toolType.getToolBlockSet());

		this.toolType = toolType;

		if (toolType == EnumAetherToolType.PICKAXE) {
			this.toolClass = "pickaxe";
		} else if (toolType == EnumAetherToolType.AXE) {
			this.toolClass = "axe";
		} else if (toolType == EnumAetherToolType.SHOVEL) {
			this.toolClass = "shovel";
		}

		this.setCreativeTab(AetherCreativeTabs.tools);
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		int level = super.getHarvestLevel(stack, toolClass);

		if (level == -1 && toolClass != null && toolClass.equals(this.toolClass)) {
			return this.toolMaterial.getHarvestLevel();
		}

		return level;
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		return this.toolType.canHarvestBlock(this.toolMaterial, block);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		for (String type : getToolClasses(stack)) {
			if (block.isToolEffective(type, meta))
				return this.efficiencyOnProperMaterial;
		}

		return this.toolType.getStrVsBlock(stack, block) == 4.0F ? this.efficiencyOnProperMaterial : 1.0F;
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double) this.attackDamage, 0));
		return multimap;
	}

	public float getEffectiveSpeed() {
		return this.efficiencyOnProperMaterial;
	}

}