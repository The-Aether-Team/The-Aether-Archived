package com.legacy.aether.items.weapons;

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

import com.legacy.aether.entities.projectile.EntityHammerProjectile;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemNotchHammer extends ItemSword
{

	public ItemNotchHammer()
	{
		super(ToolMaterial.IRON);
        this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, EnumHand hand)
	{
		if (entityplayer.capabilities.isCreativeMode)
		{
			world.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F), false);

			if (!world.isRemote)
			{
				EntityHammerProjectile hammerProjectile = new EntityHammerProjectile(world, entityplayer);
				hammerProjectile.setHeadingFromThrower(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntityInWorld(hammerProjectile);
			}
		}
		else if (PlayerAether.get(entityplayer).setGeneralCooldown(200, itemstack.getDisplayName()))
		{
			itemstack.damageItem(1, entityplayer);

			world.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F), false);

			if (!world.isRemote)
			{
				EntityHammerProjectile hammerProjectile = new EntityHammerProjectile(world, entityplayer);
				hammerProjectile.setHeadingFromThrower(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntityInWorld(hammerProjectile);
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

}