package com.legacy.aether.items.weapons;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemHolystoneSword extends ItemSword
{

    public ItemHolystoneSword()
    {
        super(ToolMaterial.STONE);
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
    public boolean getIsRepairable(ItemStack repairingItem, ItemStack mateiral)
    {
        return mateiral.getItem() == Item.getItemFromBlock(BlocksAether.holystone);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1)
    {
        if ((new Random()).nextInt(20) == 0 && entityliving1 != null && entityliving1 instanceof EntityPlayer && entityliving.hurtTime > 0 && entityliving.deathTime <= 0)
        {
            if (!entityliving.world.isRemote)
            {
                entityliving.dropItem(ItemsAether.ambrosium_shard, 1);
            }
        }

        itemstack.damageItem(1, entityliving1);
        return true;
    }

}