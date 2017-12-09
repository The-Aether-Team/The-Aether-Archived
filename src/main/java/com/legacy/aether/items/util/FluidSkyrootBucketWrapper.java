package com.legacy.aether.items.util;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import com.legacy.aether.items.ItemsAether;

public class FluidSkyrootBucketWrapper extends FluidBucketWrapper
{

	public FluidSkyrootBucketWrapper(ItemStack container)
	{
		super(container);
	}

    public boolean canFillFluidType(FluidStack fluid)
    {
        if (fluid.getFluid() == FluidRegistry.WATER)
        {
            return true;
        }
 
        return false;
    }

    @Nullable
    public FluidStack getFluid()
    {
        Item item = container.getItem();
        int meta = container.getItemDamage();

        if (item == ItemsAether.skyroot_bucket && meta == 1)
        {
            return new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
        }
        else
        {
            return null;
        }
    }

    protected void setFluid(Fluid fluid) 
    {
        if (fluid == null)
        {
            container.deserializeNBT(new ItemStack(ItemsAether.skyroot_bucket).serializeNBT());
        }
        else if (fluid == FluidRegistry.WATER)
        {
            container.deserializeNBT(new ItemStack(ItemsAether.skyroot_bucket, 1, 1).serializeNBT());
        }
    }

}