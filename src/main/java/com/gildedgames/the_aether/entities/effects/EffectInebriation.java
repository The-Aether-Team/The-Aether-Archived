package com.gildedgames.the_aether.entities.effects;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumSkyrootBucketType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class EffectInebriation extends PotionEffect
{
    private List<ItemStack> curativeItems;

    public EffectInebriation(int potionID, int duration, int amplifier)
    {
        this(potionID, duration, amplifier, false);
    }

    public EffectInebriation(int potionID, int duration, int amplifier, boolean isAmbient)
    {
        super(potionID, duration, amplifier, isAmbient);
        this.curativeItems = new ArrayList<>();
        this.curativeItems.add(new ItemStack(ItemsAether.skyroot_bucket, EnumSkyrootBucketType.Remedy.meta));
        this.curativeItems.add(new ItemStack(ItemsAether.white_apple));
    }

    @Override
    public List<ItemStack> getCurativeItems()
    {
        return this.curativeItems;
    }

    @Override
    public boolean isCurativeItem(ItemStack stack)
    {
        boolean found = false;
        for (ItemStack curativeItem : this.curativeItems)
        {
            if (curativeItem.isItemEqual(stack))
            {
                found = true;
            }
        }

        return found;
    }

    @Override
    public void setCurativeItems(List<ItemStack> curativeItems)
    {
        this.curativeItems = curativeItems;
    }
}
