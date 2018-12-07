package com.legacy.aether.items.tools;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Multimap;
import com.legacy.aether.items.util.EnumAetherToolType;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public abstract class ItemAetherTool extends ItemTool {

	private static final float[] ATTACK_DAMAGES = new float[]{6.0F, 8.0F, 8.0F, 8.0F, 6.0F};

	private float attackDamage;

	private String toolClass;

	public Random random = new Random();

	public EnumAetherToolType toolType;

	public ItemAetherTool(ToolMaterial toolMaterial, EnumAetherToolType toolType) {
		super(1.0F, toolMaterial, toolType.getToolBlockSet());

		this.toolType = toolType;

		if (toolType == EnumAetherToolType.PICKAXE) {
			this.toolClass = "pickaxe";
			this.attackDamage = 1.0F + toolMaterial.getDamageVsEntity();
		} else if (toolType == EnumAetherToolType.AXE) {
			this.toolClass = "axe";
			this.attackDamage = ATTACK_DAMAGES[toolMaterial.ordinal()] + toolMaterial.getDamageVsEntity();
		} else if (toolType == EnumAetherToolType.SHOVEL) {
			this.toolClass = "shovel";
			this.attackDamage = 1.5F + toolMaterial.getDamageVsEntity();
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