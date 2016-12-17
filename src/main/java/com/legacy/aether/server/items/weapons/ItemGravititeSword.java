package com.legacy.aether.server.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemGravititeSword extends ItemSword
{

    public ItemGravititeSword()
    {
        super(ToolMaterial.DIAMOND);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

    @Override
    public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
    {
        return material.getItem() == Item.getItemFromBlock(BlocksAether.enchanted_gravitite);
    }

    public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitentity, EntityLivingBase player)
    {
        if (player != null && player instanceof EntityPlayer && (hitentity.hurtTime > 0 || hitentity.deathTime > 0))
        {
            hitentity.addVelocity(0.0D, 1.0D, 0.0D);
            itemstack.damageItem(1, player);
        }

        return true;
    }

}