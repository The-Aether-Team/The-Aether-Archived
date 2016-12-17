package com.legacy.aether.server.items;

import com.legacy.aether.server.entities.passive.EntityAerwhale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemAerwhaleEgg extends Item 
{

	public ItemAerwhaleEgg()
	{
		super();

		this.setCreativeTab(null);
	}

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --stack.stackSize;
        }

		EntityAerwhale aerwhale = new EntityAerwhale(worldIn);

		aerwhale.moveToBlockPosAndAngles(pos.up(10), 1.0F, 1.0F);

		if (!worldIn.isRemote)
		{
			worldIn.spawnEntityInWorld(aerwhale);
		}

		return EnumActionResult.SUCCESS;
    }

}