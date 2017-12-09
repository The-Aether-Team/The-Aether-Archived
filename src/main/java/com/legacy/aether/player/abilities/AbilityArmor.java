package com.legacy.aether.player.abilities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.movement.AetherLiquidMovement;

public class AbilityArmor extends Ability
{

	private AetherLiquidMovement player_movement;

	private boolean jumpBoosted;

	private Random rand = new Random();

	public AbilityArmor(PlayerAether player)
	{
		super(player);
		
		this.player_movement = new AetherLiquidMovement(this.playerAether);
	}

	@Override
	public void onUpdate()
	{
		if (this.playerAether.isWearingNeptuneSet())
		{
			this.player_movement.onUpdate();
		}

		if (this.playerAether.isWearingGravititeSet())
		{
			if (this.playerAether.isJumping() && !this.jumpBoosted)
			{
				this.player.motionY = 1D;
				this.jumpBoosted = true;
			}

			this.player.fallDistance = -1F;
		}

		if (this.player.isWet())
		{
			if (this.playerAether.wearingArmor(ItemsAether.phoenix_boots))
			{
				this.damagePhoenixArmor(this.player, ItemsAether.obsidian_boots, 0);
			}

			if (this.playerAether.wearingArmor(ItemsAether.phoenix_leggings))
			{
				this.damagePhoenixArmor(this.player, ItemsAether.obsidian_leggings, 1);
			}

			if (this.playerAether.wearingArmor(ItemsAether.phoenix_chestplate))
			{
				this.damagePhoenixArmor(this.player, ItemsAether.obsidian_chestplate, 2);
			}

			if (this.playerAether.wearingArmor(ItemsAether.phoenix_helmet))
			{
				this.damagePhoenixArmor(this.player, ItemsAether.obsidian_helmet, 3);
			}
		}

		if (this.playerAether.isWearingPhoenixSet())
		{
			this.player.extinguish();
			this.player_movement.onUpdate();

			if (this.player.world.isRemote)
			{
				FMLClientHandler.instance().getClient().world.spawnParticle(EnumParticleTypes.FLAME, this.player.posX + (this.rand.nextGaussian() / 5D), this.player.posY + (this.rand.nextGaussian() / 5D), this.player.posZ + (this.rand.nextGaussian() / 3D), 0.0D, 0.0D, 0.0D);
			}
		}

		if (this.playerAether.wearingArmor(ItemsAether.sentry_boots))
		{
			this.player.fallDistance = 0F;
		}

		if (!this.playerAether.isJumping() && this.player.onGround)
		{
			this.jumpBoosted = false;
		}

	}

	public void damagePhoenixArmor(EntityPlayer player, Item outcome, int slot)
	{
		player.inventory.armorInventory.get(slot).damageItem(1, player);

        if(player.inventory.armorInventory.get(slot).isEmpty())
        {
        	player.inventory.armorInventory.set(slot, new ItemStack(outcome, 1, 0));
		}
	}

}