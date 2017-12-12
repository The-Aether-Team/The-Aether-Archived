package com.legacy.aether.items.weapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.NonNullList;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemGravititeSword extends ItemSword
{

    public ItemGravititeSword()
    {
        super(ToolMaterial.DIAMOND);
    }

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
    	if (tab == AetherCreativeTabs.weapons)
    	{
            items.add(new ItemStack(this));
    	}
    }

    @Override
    public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
    {
        return material.getItem() == Item.getItemFromBlock(BlocksAether.enchanted_gravitite);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitentity, EntityLivingBase player)
    {
        if ((hitentity.hurtTime > 0 || hitentity.deathTime > 0))
        {
            hitentity.addVelocity(0.0D, 1.0D, 0.0D);
        }

        if (hitentity instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)hitentity).connection.sendPacket(new SPacketEntityVelocity(hitentity));
        }

        return super.hitEntity(itemstack, hitentity, player);
    }

}