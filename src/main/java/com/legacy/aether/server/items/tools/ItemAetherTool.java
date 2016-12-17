package com.legacy.aether.server.items.tools;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.legacy.aether.server.items.util.EnumAetherToolType;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public abstract class ItemAetherTool extends ItemTool
{

    private static final float[] ATTACK_DAMAGES = new float[] {6.0F, 8.0F, 8.0F, 8.0F, 6.0F};

    private static final float[] ATTACK_SPEEDS = new float[] { -3.2F, -3.2F, -3.1F, -3.0F, -3.0F};

	private String toolClass;

	public Random random = new Random();

	public EnumAetherToolType toolType;

	public ItemAetherTool(ToolMaterial toolMaterial, EnumAetherToolType toolType)
	{
		super(toolType.getDamageVsEntity(), 2.0F, toolMaterial, toolType.getToolBlockSet());
		this.setCreativeTab(AetherCreativeTabs.tools);
		this.toolType = toolType;
		
        if (toolType == EnumAetherToolType.PICKAXE)
        {
        	this.toolClass = "pickaxe";
        	this.damageVsEntity = 1.0F + toolMaterial.getDamageVsEntity();
        	this.attackSpeed = -2.8F;
        }
        else if (toolType == EnumAetherToolType.AXE)
        {
        	this.toolClass = "axe";
            this.damageVsEntity = ATTACK_DAMAGES[toolMaterial.ordinal()] + toolMaterial.getDamageVsEntity();
            this.attackSpeed = ATTACK_SPEEDS[toolMaterial.ordinal()];
        }
        else if (toolType == EnumAetherToolType.SHOVEL)
        {
        	this.toolClass = "shovel";
        	this.damageVsEntity = 1.5F + toolMaterial.getDamageVsEntity();
        	this.attackSpeed = -3.0F;
        }
	}

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        int level = super.getHarvestLevel(stack, toolClass);
        if (level == -1 && toolClass != null && toolClass.equals(this.toolClass))
        {
            return this.toolMaterial.getHarvestLevel();
        }
        else
        {
            return level;
        }
    }

	@Override
	public boolean canHarvestBlock(IBlockState block)
	{
		return this.toolType.canHarvestBlock(this.toolMaterial, block);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState block)
	{
		float aetherStrength = this.toolType.getStrVsBlock(stack, block);
		return aetherStrength == 4.0F ? aetherStrength : super.getStrVsBlock(stack, block);
	}

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
    }

}