package com.legacy.aether.items.accessories;

import com.legacy.aether.Aether;
import com.legacy.aether.api.accessories.AccessoryType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemAccessoryDyable extends ItemAccessory
{
    public ItemAccessoryDyable(AccessoryType type)
    {
        super(type);
        this.texture = Aether.locate("textures/armor/accessory_leather.png");
        this.texture_slim = Aether.locate("textures/armor/accessory_leather_slim.png");
    }

    public boolean hasColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        return (nbttagcompound != null && nbttagcompound.hasKey("display", 10)) && nbttagcompound.getCompoundTag("display").hasKey("color", 3);
    }

    public int getColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color", 3))
            {
                return nbttagcompound1.getInteger("color");
            }
        }

        return 10511680;
    }

    public void removeColor(ItemStack stack)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            {
                nbttagcompound1.removeTag("color");
            }
        }
    }

    public void setColor(ItemStack stack, int color)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stack.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10))
        {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", color);
    }

    public boolean hasOverlay(ItemStack stack)
    {
        return getColor(stack) != 0x00FFFFFF;
    }
}
