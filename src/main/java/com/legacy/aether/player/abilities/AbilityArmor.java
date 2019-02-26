package com.legacy.aether.player.abilities;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.legacy.aether.api.player.util.IAetherAbility;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.movement.AetherLiquidMovement;

public class AbilityArmor implements IAetherAbility
{

	private PlayerAether playerAether;

	private AetherLiquidMovement playerMovement;

	private boolean jumpBoosted;

	private Random rand = new Random();

	public AbilityArmor(PlayerAether player)
	{
		this.playerAether = player;
		this.playerMovement = new AetherLiquidMovement(this.playerAether);
	}

	@Override
	public boolean shouldExecute()
	{
		return true;
	}

	@Override
	public void onUpdate()
	{
		if (this.playerAether.getAccessoryInventory().isWearingNeptuneSet())
		{
			this.playerMovement.onUpdate();
		}

		if (this.playerAether.getAccessoryInventory().isWearingGravititeSet())
		{
			if (this.playerAether.isJumping() && !this.jumpBoosted)
			{
				this.playerAether.getEntity().motionY = 1D;
				this.jumpBoosted = true;
			}

			this.playerAether.getEntity().fallDistance = -1F;
		}

		if (this.playerAether.getEntity().isWet())
		{
			if (this.playerAether.getAccessoryInventory().wearingArmor(new ItemStack(ItemsAether.phoenix_boots)))
			{
				this.damagePhoenixArmor(this.playerAether.getEntity(), ItemsAether.obsidian_boots, 0);
			}

			if (this.playerAether.getAccessoryInventory().wearingArmor(new ItemStack(ItemsAether.phoenix_leggings)))
			{
				this.damagePhoenixArmor(this.playerAether.getEntity(), ItemsAether.obsidian_leggings, 1);
			}

			if (this.playerAether.getAccessoryInventory().wearingArmor(new ItemStack(ItemsAether.phoenix_chestplate)))
			{
				this.damagePhoenixArmor(this.playerAether.getEntity(), ItemsAether.obsidian_chestplate, 2);
			}

			if (this.playerAether.getAccessoryInventory().wearingArmor(new ItemStack(ItemsAether.phoenix_helmet)))
			{
				this.damagePhoenixArmor(this.playerAether.getEntity(), ItemsAether.obsidian_helmet, 3);
			}
		}

		if (this.playerAether.getAccessoryInventory().isWearingPhoenixSet())
		{
			this.playerAether.getEntity().extinguish();
			this.playerMovement.onUpdate();

			if (this.playerAether.getEntity().world.isRemote)
			{
				FMLClientHandler.instance().getClient().world.spawnParticle(EnumParticleTypes.FLAME, this.playerAether.getEntity().posX + (this.rand.nextGaussian() / 5D), this.playerAether.getEntity().posY + (this.rand.nextGaussian() / 5D), this.playerAether.getEntity().posZ + (this.rand.nextGaussian() / 3D), 0.0D, 0.0D, 0.0D);
			}
		}

		if (this.playerAether.getAccessoryInventory().wearingArmor(new ItemStack(ItemsAether.sentry_boots)))
		{
			this.playerAether.getEntity().fallDistance = 0F;
		}

		if (!this.playerAether.isJumping() && this.playerAether.getEntity().onGround)
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