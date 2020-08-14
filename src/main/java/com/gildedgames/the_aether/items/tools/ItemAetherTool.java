package com.gildedgames.the_aether.items.tools;

import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.NonNullList;

import com.gildedgames.the_aether.items.util.EnumAetherToolType;

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

		this.toolType = toolType;
		
        if (toolType == EnumAetherToolType.PICKAXE)
        {
        	this.toolClass = "pickaxe";
        	this.attackDamage = 1.0F + toolMaterial.getAttackDamage();
        	this.attackSpeed = -2.8F;
        }
        else if (toolType == EnumAetherToolType.AXE)
        {
        	this.toolClass = "axe";
            this.attackDamage = ATTACK_DAMAGES[toolMaterial.ordinal()];
            this.attackSpeed = ATTACK_SPEEDS[toolMaterial.ordinal()];
        }
        else if (toolType == EnumAetherToolType.SHOVEL)
        {
        	this.toolClass = "shovel";
        	this.attackDamage = 1.5F + toolMaterial.getAttackDamage();
        	this.attackSpeed = -3.0F;
        }
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
    	if (tab == AetherCreativeTabs.tools || tab == CreativeTabs.SEARCH)
    	{
            items.add(new ItemStack(this));
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
    public float getDestroySpeed(ItemStack stack, IBlockState block)
    {
        for (String type : getToolClasses(stack))
        {
            if (block.getBlock().isToolEffective(type, block))
                return efficiency;
        }

		return this.toolType.getStrVsBlock(stack, block) == 4.0F ? this.efficiency : 1.0F;
	}

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
    }

    public float getEffectiveSpeed()
    {
    	return this.efficiency;
    }

}