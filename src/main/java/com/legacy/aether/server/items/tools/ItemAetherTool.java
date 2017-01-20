package com.legacy.aether.server.items.tools;

import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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
		super(1.0F, 2.0F, toolMaterial, toolType.getToolBlockSet());
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
    public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState)
    {
		if (blockState != null && blockState.getBlock().isToolEffective(this.toolClass, blockState))
		{
            return this.toolMaterial.getHarvestLevel();
		}

		return super.getHarvestLevel(stack, toolClass, player, blockState);
    }

	@Override
	public boolean canHarvestBlock(IBlockState block)
	{
		return this.toolType.canHarvestBlock(this.toolMaterial, block);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState block)
	{
        for (String type : getToolClasses(stack))
        {
            if (block.getBlock().isToolEffective(type, block))
                return efficiencyOnProperMaterial;
        }

		return this.toolType.getStrVsBlock(stack, block) == 4.0F ? this.efficiencyOnProperMaterial : 1.0F;
	}

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
    }

    public float getEffectiveSpeed()
    {
    	return this.efficiencyOnProperMaterial;
    }

}