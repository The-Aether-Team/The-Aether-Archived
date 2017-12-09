package com.legacy.aether.items.food;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import com.legacy.aether.items.util.EnumGummySwetType;

public class ItemGummySwet extends ItemAetherFood
{

	public ItemGummySwet()
	{
		super(20);

		this.setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		for (int meta = 0; meta < EnumGummySwetType.values().length ; ++meta)
		{
			subItems.add(new ItemStack(itemIn, 1, meta));
		}
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);

    	playerIn.heal(playerIn.getMaxHealth());

    	if (!playerIn.capabilities.isCreativeMode)
    	{
    		heldItem.shrink(1);
    	}

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
    }


	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumGummySwetType.getType(meta).toString();
	}

}