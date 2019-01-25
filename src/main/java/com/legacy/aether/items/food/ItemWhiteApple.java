package com.legacy.aether.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.api.AetherAPI;

public class ItemWhiteApple extends ItemAetherFood
{

	public ItemWhiteApple()
	{
		super(0);

		this.setAlwaysEdible();
	}

	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
    	AetherAPI.getInstance().get(player).inflictCure(300);
    }

}