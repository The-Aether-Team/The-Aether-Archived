package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.items.ItemsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.the_aether.api.AetherAPI;

public class ItemWhiteApple extends ItemAetherFood
{

	public ItemWhiteApple()
	{
		super(0);

		this.setAlwaysEdible();
	}

	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer playerEntity)
    {
		IPlayerAether player = AetherAPI.getInstance().get(playerEntity);
		((PlayerAether) player).setCured(300);

		if (!world.isRemote)
		{
			playerEntity.curePotionEffects(new ItemStack(ItemsAether.white_apple));
		}
    }

}