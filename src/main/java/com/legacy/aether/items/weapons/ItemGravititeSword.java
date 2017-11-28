package com.legacy.aether.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

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
        if ((hitentity.hurtTime > 0 || hitentity.deathTime > 0))
        {
            hitentity.addVelocity(0.0D, 1.0D, 0.0D);
            itemstack.damageItem(1, player);
        }

        if (hitentity instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)hitentity).connection.sendPacket(new SPacketEntityVelocity(hitentity));
        }

        return super.hitEntity(itemstack, hitentity, player);
    }

}