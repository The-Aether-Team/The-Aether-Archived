package com.legacy.aether.items.weapons;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemValkyrieLance extends ItemSword {

	public ItemValkyrieLance() {
		super(ToolMaterial.EMERALD);

		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.none;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (!(entityLiving instanceof EntityPlayer)) {
			return false;
		}

		EntityPlayer player = (EntityPlayer) entityLiving;

		Vec3 playerVision = player.getLookVec();
		AxisAlignedBB reachDistance = player.boundingBox.expand(10.0D, 10.0D, 10.0D);

		List<Entity> locatedEntities = player.worldObj.getEntitiesWithinAABB(Entity.class, reachDistance);

		Entity found = null;
		double foundLen = 0.0D;

		for (Object o : locatedEntities) {
			if (o == player) {
				continue;
			}

			Entity ent = (Entity) o;

			if (!ent.canBeCollidedWith()) {
				continue;
			}

			Vec3 vec = Vec3.createVectorHelper(ent.posX - player.posX, ent.boundingBox.minY + ent.height / 2f - player.posY - player.getEyeHeight(), ent.posZ - player.posZ);
			double len = vec.lengthVector();

			if (len > 8.0F) {
				continue;
			}

			vec = vec.normalize();
			double dot = playerVision.dotProduct(vec);

			if (dot < 1.0 - 0.125 / len || !player.canEntityBeSeen(ent)) {
				continue;
			}

			if (foundLen == 0.0 || len < foundLen) {
				found = ent;
				foundLen = len;
			}
		}

		if (found != null && player.ridingEntity != found) {
			stack.damageItem(1, player);

			player.attackTargetEntityWithCurrentItem(found);
		}

		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack repairingItem, ItemStack material) {
		return false;
	}

}