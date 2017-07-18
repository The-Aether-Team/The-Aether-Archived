package com.legacy.aether.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.legacy.aether.common.Aether;
import com.legacy.aether.common.networking.AetherGuiHandler;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemLoreBook extends Item 
{

	public ItemLoreBook()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	playerIn.openGui(Aether.instance, AetherGuiHandler.lore, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);

    	return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(hand));
    }

}