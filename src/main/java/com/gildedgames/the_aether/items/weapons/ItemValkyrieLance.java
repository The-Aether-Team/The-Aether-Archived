package com.gildedgames.the_aether.items.weapons;

import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemValkyrieLance extends ItemSword {

    public ItemValkyrieLance() {
        super(ToolMaterial.EMERALD);

        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.none;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return ItemsAether.aether_loot;
    }

    @Override
    public boolean getIsRepairable(ItemStack repairingItem, ItemStack material) {
        return false;
    }

}
