package com.legacy.aether.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.player.PlayerAether;

public class ItemWhiteApple extends ItemAetherFood {

	public ItemWhiteApple() {
		super(0);

		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		PlayerAether.get(player).inflictCure(300);
	}

}