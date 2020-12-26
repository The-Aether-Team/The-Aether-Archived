package com.gildedgames.the_aether.player.abilities;

import com.gildedgames.the_aether.api.player.util.IAetherAbility;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AbilityAccessories implements IAetherAbility
{

	private final PlayerAether playerAether;

	private boolean invisibilityUpdate, stepUpdate;

	public AbilityAccessories(PlayerAether player) 
	{
		this.playerAether = player;
	}

	@Override
	public boolean shouldExecute()
	{
		return true;
	}

	@Override
	public void onUpdate() 
	{
		if (this.playerAether.getEntity().ticksExisted % 400 == 0)
		{
			this.playerAether.accessories.damageWornStack(1, new ItemStack(ItemsAether.zanite_ring));
			this.playerAether.accessories.damageWornStack(1, new ItemStack(ItemsAether.zanite_pendant));
		}

		if (!this.playerAether.getEntity().world.isRemote && this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.ice_ring)) || this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.ice_pendant)))
		{
			int i = MathHelper.floor(this.playerAether.getEntity().posX);
			int j = MathHelper.floor(this.playerAether.getEntity().getEntityBoundingBox().minY);
			int k = MathHelper.floor(this.playerAether.getEntity().posZ);

			for (int l = i - 1; l <= i + 1; l++)
			{
				for (int i1 = j - 1; i1 <= j + 1; i1++)
				{
					for (int j1 = k - 1; j1 <= k + 1; j1++)
					{
						IBlockState state = this.playerAether.getEntity().world.getBlockState(new BlockPos.MutableBlockPos().setPos(l, i1, j1));
						Block block = state.getBlock();
						BlockPos pos = new BlockPos(l, i1, j1);

						if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
						{
							if (((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0)
							{ this.playerAether.getEntity().world.setBlockState(pos, Blocks.ICE.getDefaultState()); }
							else { this.playerAether.getEntity().world.setBlockState(pos, Blocks.AIR.getDefaultState()); }
						}
						else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
						{
							if (((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0)
							{ this.playerAether.getEntity().world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState()); }
							else { this.playerAether.getEntity().world.setBlockState(pos, Blocks.AIR.getDefaultState()); }
						}
						else
						{
							continue;
						}

						this.playerAether.accessories.damageWornStack(1, new ItemStack(ItemsAether.ice_ring));
						this.playerAether.accessories.damageWornStack(1, new ItemStack(ItemsAether.ice_pendant));
					}
				}
			}
		}

		if (this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.iron_bubble)))
		{
			this.playerAether.getEntity().setAir(0);
		}

		if (this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.agility_cape)))
		{
			if (!this.playerAether.getEntity().isSneaking())
			{
				this.stepUpdate = true;
				this.playerAether.getEntity().stepHeight = 1.0F;
			}
			else
			{
				if (this.stepUpdate)
				{
					this.playerAether.getEntity().stepHeight = 0.5F;
					this.stepUpdate = false;
				}
			}
		}
		else
		{
			if (this.stepUpdate)
			{
				this.playerAether.getEntity().stepHeight = 0.5F;
				this.stepUpdate = false;
			}
		}

		if (this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape)))
		{
			this.invisibilityUpdate = true;
			this.playerAether.getEntity().setInvisible(true);
		}
		else if (!this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape)) && !this.playerAether.getEntity().isPotionActive(Potion.getPotionById(14)))
		{
			if (this.invisibilityUpdate)
			{
				this.playerAether.getEntity().setInvisible(false);
				this.invisibilityUpdate = false;
			}
		}

		if (this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.regeneration_stone)))
		{
			if(this.playerAether.getEntity().getHealth() < this.playerAether.getEntity().getMaxHealth() && this.playerAether.getEntity().getActivePotionEffect(MobEffects.REGENERATION) == null)
            {
				this.playerAether.getEntity().addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 0, false, false));
            }
		}

		if (this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.phoenix_gloves)) && this.playerAether.getEntity().isWet())
		{
			if (this.playerAether.getEntity().world.getTotalWorldTime() % 5 == 0)
			{
				ItemStack currentPiece = this.playerAether.accessories.getStackInSlot(6);

				this.playerAether.accessories.damageWornStack(1, currentPiece);

				if (this.playerAether.accessories.getStackInSlot(6) == ItemStack.EMPTY)
				{
					ItemStack outcomeStack = new ItemStack(ItemsAether.obsidian_gloves, 1, 0);
					EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(currentPiece), outcomeStack);

					this.playerAether.accessories.setInventorySlotContents(6, outcomeStack);
				}
			}
		}

		if ((this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.golden_feather)) || this.playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.valkyrie_cape))) && !this.playerAether.getEntity().isElytraFlying())
		{
			if (!this.playerAether.getEntity().onGround && this.playerAether.getEntity().motionY < 0.0D && !this.playerAether.getEntity().isInWater() && !this.playerAether.getEntity().isSneaking())
			{
				this.playerAether.getEntity().motionY *= 0.59999999999999998D;
			}

			this.playerAether.getEntity().fallDistance = -1F;
		}
	}

}