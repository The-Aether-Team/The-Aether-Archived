package com.legacy.aether.common.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.projectile.EntityLightningKnife;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.common.registry.sounds.SoundsAether;

public class ItemLightningKnife extends Item
{
    public ItemLightningKnife()
    {
        this.setMaxStackSize(16);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        worldIn.playSound(playerIn, playerIn.getPosition(), SoundsAether.projectile_shoot, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));//world.playSoundAtEntity(entityplayer, "random.drr", 2.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
        	worldIn.spawnEntityInWorld(new EntityLightningKnife(worldIn, playerIn));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

}