package com.legacy.aether.items.weapons;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import com.legacy.aether.entities.projectile.EntityLightningKnife;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.registry.sounds.SoundsAether;

public class ItemLightningKnife extends Item
{
    public ItemLightningKnife()
    {
        this.setMaxStackSize(16);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	ItemStack heldItem = playerIn.getHeldItem(hand);

        if (!playerIn.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, heldItem) == 0)
        {
        	heldItem.shrink(1);
        }

        worldIn.playSound(playerIn, playerIn.getPosition(), SoundsAether.projectile_shoot, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
        	EntityLightningKnife lightningKnife = new EntityLightningKnife(worldIn, playerIn);
        	lightningKnife.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
        	worldIn.spawnEntity(lightningKnife);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
    }

}