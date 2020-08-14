package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWhiteApple extends ItemAetherFood {

	public ItemWhiteApple() {
		super(0);

		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {

		PlayerAether.get(player).setCured(300);

		if (!world.isRemote)
		{
			player.curePotionEffects(new ItemStack(ItemsAether.white_apple));
		}
	}
}