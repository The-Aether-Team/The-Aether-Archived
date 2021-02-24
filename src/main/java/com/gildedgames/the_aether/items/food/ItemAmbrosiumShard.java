package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.AetherConfig;

import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAmbrosiumShard extends ItemAetherFood
{

	public ItemAmbrosiumShard()
	{
		super(0);
		this.setCreativeTab(AetherCreativeTabs.material);
		
		this.setAlwaysEdible();
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);

		if (worldIn.getBlockState(pos) != BlocksAether.enchanted_aether_grass && AetherWorld.viableGrassBlocks.contains(worldIn.getBlockState(pos).getBlock()))
		{
			worldIn.setBlockState(pos, BlocksAether.enchanted_aether_grass.getDefaultState());
		}
    	else
    	{
    		return EnumActionResult.FAIL;
    	}

    	if (!playerIn.capabilities.isCreativeMode)
    	{
    		heldItem.shrink(1);
    	}

        return EnumActionResult.SUCCESS;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);

    	if (!AetherConfig.gameplay_changes.ambro_is_edible && playerIn.shouldHeal())
    	{
        	if (!playerIn.capabilities.isCreativeMode)
        	{
        		heldItem.shrink(1);
        	}
        	
    		playerIn.heal(1F);

    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
    	}
    	else if (AetherConfig.gameplay_changes.ambro_is_edible)
    	{
    		return super.onItemRightClick(worldIn, playerIn, hand);
    	}
    	else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
        }
    }
	
	@Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
		if (AetherConfig.gameplay_changes.ambro_is_edible)
		{
			player.heal(2F);
			super.onFoodEaten(stack, worldIn, player);
		}
    }

}