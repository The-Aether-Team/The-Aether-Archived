package com.legacy.aether.client.renders.items.definitions;

import com.legacy.aether.Aether;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class CandyCaneSwordDefinition implements ItemMeshDefinition
{
    public ModelResourceLocation candy_cane_sword, green_candy_cane_sword;

    public CandyCaneSwordDefinition()
    {
        this.candy_cane_sword = new ModelResourceLocation(Aether.modAddress() + "candy_cane_sword", "inventory");
        this.green_candy_cane_sword = new ModelResourceLocation(Aether.modAddress() + "green_candy_cane_sword", "inventory");
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack)
    {
        if (stack.getDisplayName().toLowerCase().equals("green candy cane sword"))
        {
            return this.green_candy_cane_sword;
        }
        else
        {
            return this.candy_cane_sword;
        }
    }

}