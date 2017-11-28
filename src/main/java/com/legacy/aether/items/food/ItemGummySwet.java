package com.legacy.aether.items.food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int meta = 0; meta < EnumGummySwetType.values().length ; ++meta)
		{
			par3List.add(new ItemStack(par1, 1, meta));
		}
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	playerIn.heal(playerIn.getMaxHealth());
    	if (!playerIn.capabilities.isCreativeMode)
    	--stack.stackSize;
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }


	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumGummySwetType.getType(meta).toString();
	}

}