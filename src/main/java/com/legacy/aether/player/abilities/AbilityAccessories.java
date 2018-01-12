package com.legacy.aether.player.abilities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;

public class AbilityAccessories extends Ability
{

	private boolean invisibilityUpdate, stepUpdate;

	public AbilityAccessories(PlayerAether player) 
	{
		super(player);
	}

	@Override
	public void onUpdate() 
	{
		if (this.player.ticksExisted % 400 == 0)
		{
			this.playerAether.accessories.damageItemStackIfWearing(new ItemStack(ItemsAether.zanite_ring));
			this.playerAether.accessories.damageItemStackIfWearing(new ItemStack(ItemsAether.zanite_pendant));
		}

		if (!this.player.world.isRemote && this.playerAether.wearingAccessory(ItemsAether.ice_ring) || this.playerAether.wearingAccessory(ItemsAether.ice_pendant))
		{
			int i = MathHelper.floor(this.player.posX);
			int j = MathHelper.floor(this.player.getEntityBoundingBox().minY);
			int k = MathHelper.floor(this.player.posZ);

			for (int l = i - 1; l <= i + 1; l++)
			{
				for (int i1 = j - 1; i1 <= j + 1; i1++)
				{
					for (int j1 = k - 1; j1 <= k + 1; j1++)
					{
						IBlockState state = this.player.world.getBlockState(new BlockPos.MutableBlockPos().setPos(l, i1, j1));
						Block block = state.getBlock();
						BlockPos pos = new BlockPos(l, i1, j1);

						if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
						{
							if (((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0)
							{ this.player.world.setBlockState(pos, Blocks.ICE.getDefaultState()); }
							else { this.player.world.setBlockState(pos, Blocks.AIR.getDefaultState()); }
						}
						else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
						{
							if (((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0)
							{ this.player.world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState()); }
							else { this.player.world.setBlockState(pos, Blocks.AIR.getDefaultState()); }
						}
						else
						{
							continue;
						}

						this.playerAether.accessories.damageItemStackIfWearing(new ItemStack(ItemsAether.ice_ring));
						this.playerAether.accessories.damageItemStackIfWearing(new ItemStack(ItemsAether.ice_pendant));
					}
				}
			}
		}

		if (this.playerAether.wearingAccessory(ItemsAether.iron_bubble))
		{
			this.player.setAir(0);
		}

		if (this.playerAether.wearingAccessory(ItemsAether.agility_cape))
		{
			this.stepUpdate = true;
			this.player.stepHeight = 1.0F;
		}
		else
		{
			if (this.stepUpdate)
			{
				this.player.stepHeight = 0.5F;
				this.stepUpdate = false;
			}
		}

		if (this.playerAether.wearingAccessory(ItemsAether.invisibility_cape))
		{
			this.invisibilityUpdate = true;
			this.player.setInvisible(true);
		}
		else if (!this.playerAether.wearingAccessory(ItemsAether.invisibility_cape) && !this.player.isPotionActive(Potion.getPotionById(14)))
		{
			if (this.invisibilityUpdate)
			{
				this.player.setInvisible(false);
				this.invisibilityUpdate = false;
			}
		}

		if (this.playerAether.wearingAccessory(ItemsAether.regeneration_stone))
		{
			if(this.player.getHealth() < this.player.getMaxHealth() && this.player.getActivePotionEffect(MobEffects.REGENERATION) == null)
            {
				this.player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 0, false, false));
            }
		}

		if (this.playerAether.wearingAccessory(ItemsAether.phoenix_gloves) && this.player.isWet())
		{
			this.playerAether.accessories.damageItemStackIfWearing(new ItemStack(ItemsAether.phoenix_gloves));

			if (this.playerAether.accessories.getStackFromItem(ItemsAether.phoenix_gloves) == ItemStack.EMPTY)
			{
				this.playerAether.accessories.setInventorySlotContents(6, new ItemStack(ItemsAether.obsidian_gloves));
			}
		}

		if (this.playerAether.wearingAccessory(ItemsAether.golden_feather) && !this.player.isElytraFlying())
		{
			if (!this.player.onGround && this.player.motionY < 0.0D && !this.player.isInWater() && !this.player.isSneaking())
			{
				this.player.motionY *= 0.59999999999999998D;
			}

			this.player.fallDistance = -1F;
		}
	}

}