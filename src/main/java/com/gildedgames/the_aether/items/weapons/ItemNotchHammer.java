package com.gildedgames.the_aether.items.weapons;

import com.gildedgames.the_aether.entities.projectile.EntityHammerProjectile;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.items.ItemsAether;

public class ItemNotchHammer extends ItemSword
{

	public ItemNotchHammer()
	{
		super(ToolMaterial.IRON);
        this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand)
	{
		ItemStack itemstack = entityplayer.getHeldItem(hand);

		if (entityplayer.capabilities.isCreativeMode)
		{
			world.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F), false);

			if (!world.isRemote)
			{
				EntityHammerProjectile hammerProjectile = new EntityHammerProjectile(world, entityplayer);
				hammerProjectile.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntity(hammerProjectile);
			}
		}
		else if (AetherAPI.getInstance().get(entityplayer).setHammerCooldown(200, itemstack.getDisplayName()))
		{
			itemstack.damageItem(1, entityplayer);

			world.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F), false);

			if (!world.isRemote)
			{
				EntityHammerProjectile hammerProjectile = new EntityHammerProjectile(world, entityplayer);
				hammerProjectile.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntity(hammerProjectile);
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

}