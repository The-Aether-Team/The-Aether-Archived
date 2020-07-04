package com.legacy.aether.player.abilities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;

import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.util.IAetherAbility;
import com.legacy.aether.items.ItemsAether;

public class AbilityAccessories implements IAetherAbility {

	private final IPlayerAether player;

	private boolean invisibilityUpdate;

	private boolean stepUpdate;

	public AbilityAccessories(IPlayerAether player) {
		this.player = player;
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}

	@Override
	public void onUpdate() {
		if (this.player.getEntity().ticksExisted % 400 == 0) {
			this.player.getAccessoryInventory().damageWornStack(1, new ItemStack(ItemsAether.zanite_ring));
			this.player.getAccessoryInventory().damageWornStack(1, new ItemStack(ItemsAether.zanite_pendant));
		}

		if (!this.player.getEntity().worldObj.isRemote && (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.ice_ring)) || this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.ice_pendant)))) {
			int i = MathHelper.floor_double(this.player.getEntity().posX);
			int j = MathHelper.floor_double(this.player.getEntity().boundingBox.minY);
			int k = MathHelper.floor_double(this.player.getEntity().posZ);

			for (int x = i - 1; x <= i + 1; x++) {
				for (int y = j - 1; y <= j + 1; y++) {
					for (int z = k - 1; z <= k + 1; z++) {
						Block block = this.player.getEntity().worldObj.getBlock(x, y, z);
						Block setBlock = (block == Blocks.water || block == Blocks.flowing_water) ? Blocks.ice : (block == Blocks.lava || block == Blocks.flowing_lava) ? Blocks.obsidian : null;

						if (setBlock != null) {
							this.player.getEntity().worldObj.setBlock(x, y, z, setBlock);
							this.player.getAccessoryInventory().damageWornStack(1, new ItemStack(ItemsAether.ice_ring));
							this.player.getAccessoryInventory().damageWornStack(1, new ItemStack(ItemsAether.ice_pendant));
						}
					}
				}
			}
		}

		if (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.iron_bubble))) {
			this.player.getEntity().setAir(0);
		}

		if (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.agility_cape))) {
			this.player.getEntity().stepHeight = 1.0F;
			this.stepUpdate = true;
		} else {
			if (this.stepUpdate) {
				this.player.getEntity().stepHeight = 0.5F;
				this.stepUpdate = false;
			}
		}

		if (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape))) {
			this.player.getEntity().setInvisible(true);
			this.invisibilityUpdate = true;
		} else if (!this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape)) && !this.player.getEntity().isPotionActive(Potion.invisibility)) {
			if (this.invisibilityUpdate) {
				this.player.getEntity().setInvisible(false);
				this.invisibilityUpdate = false;
			}
		}

		if (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.regeneration_stone))) {
			if (this.player.getEntity().getHealth() < this.player.getEntity().getMaxHealth() && this.player.getEntity().getActivePotionEffect(Potion.regeneration) == null) {
				this.player.getEntity().addPotionEffect(new PotionEffect(Potion.regeneration.id, 80, 0));
			}
		}

		if (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.phoenix_gloves)) && this.player.getEntity().isWet()) {
			this.player.getAccessoryInventory().damageWornStack(1, new ItemStack(ItemsAether.phoenix_gloves));

			if (this.player.getAccessoryInventory().getStackInSlot(AccessoryType.GLOVES) == null) {
				this.player.getAccessoryInventory().setAccessorySlot(AccessoryType.GLOVES, new ItemStack(ItemsAether.obsidian_gloves));
			}
		}

		if (this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.golden_feather)) || this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.valkyrie_cape))) {
			if (!this.player.getEntity().onGround && this.player.getEntity().motionY < 0.0D && !this.player.getEntity().isInWater() && !this.player.getEntity().isSneaking()) {
				this.player.getEntity().motionY *= 0.59999999999999998D;
			}

			this.player.getEntity().fallDistance = -1F;
		}
	}

}