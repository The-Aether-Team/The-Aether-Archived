package com.gildedgames.the_aether.items.weapons;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.gildedgames.the_aether.items.ItemsAether;

public class ItemVampireBlade extends ItemSword
{

    public ItemVampireBlade()
    {
    	super(ToolMaterial.DIAMOND);

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