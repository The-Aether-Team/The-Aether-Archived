package com.legacy.aether.items.weapons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemVampireBlade extends ItemSword
{

    public ItemVampireBlade()
    {
    	super(ToolMaterial.DIAMOND);

        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		EntityPlayer player = (EntityPlayer)attacker;

		if(player.getHealth() < player.getMaxHealth())
		{
			player.heal(1.0F);
		}

        return super.hitEntity(stack, target, attacker);
    }

}