package com.gildedgames.the_aether.items.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import com.gildedgames.the_aether.items.ItemsAether;

public class FluidSkyrootBucketWrapper implements IFluidHandlerItem, ICapabilityProvider
{

	private ItemStack container;

	public FluidSkyrootBucketWrapper(ItemStack container)
	{
		this.container = container;
	}

	@Override
	public ItemStack getContainer()
	{
		return container;
	}

	public boolean canFillFluidType(FluidStack fluidStack)
	{
		String fluidName = fluidStack.getFluid().getName();

		return fluidStack.getFluid() == FluidRegistry.WATER || fluidName.equals("inebriation") || fluidName.equals("remedy") || fluidName.equals("milk");
	}

	public FluidStack getFluid()
	{
		if (this.container.getItem() == ItemsAether.skyroot_bucket && this.container.getItemDamage() == EnumSkyrootBucketType.Water.getMeta())
		{
			return new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
		}
		else if (this.container.getItem() == ItemsAether.skyroot_bucket && this.container.getItemDamage() == EnumSkyrootBucketType.Poison.getMeta())
		{
			return FluidRegistry.getFluidStack("inebriation", Fluid.BUCKET_VOLUME);
		}
		else if (this.container.getItem() == ItemsAether.skyroot_bucket && this.container.getItemDamage() == EnumSkyrootBucketType.Remedy.getMeta())
		{
			return FluidRegistry.getFluidStack("remedy", Fluid.BUCKET_VOLUME);
		}
		else if (this.container.getItem() == ItemsAether.skyroot_bucket && this.container.getItemDamage() == EnumSkyrootBucketType.Milk.getMeta())
		{
			return FluidRegistry.getFluidStack("milk", Fluid.BUCKET_VOLUME);
		}

		return null;
	}

	protected void setFluid(FluidStack fluidStack)
	{
		if (fluidStack == null)
		{
			this.container = new ItemStack(ItemsAether.skyroot_bucket);

			return;
		}

		String fluidName = fluidStack.getFluid().getName();

		if (fluidStack.getFluid() == FluidRegistry.WATER)
		{
			this.container = new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Water.getMeta());
		}
		else if (fluidName.equals("inebriation"))
		{
			this.container = new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.getMeta());
		}
		else if (fluidName.equals("remedy"))
		{
			this.container = new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Remedy.getMeta());
		}
		else if (fluidName.equals("milk"))
		{
			this.container = new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Milk.getMeta());
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return new FluidTankProperties[] { new FluidTankProperties(this.getFluid(), Fluid.BUCKET_VOLUME) };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (this.container.getCount() != 1 || resource == null || resource.amount < Fluid.BUCKET_VOLUME || this.getFluid() != null || !this.canFillFluidType(resource))
		{
			return 0;
		}

		if (doFill)
		{
			this.setFluid(resource);
		}

		return Fluid.BUCKET_VOLUME;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if (this.container.getCount() != 1 || resource == null || resource.amount < Fluid.BUCKET_VOLUME)
		{
			return null;
		}

		FluidStack fluidStack = this.getFluid();

		if (fluidStack != null && fluidStack.isFluidEqual(resource))
		{
			if (doDrain)
			{
				this.setFluid(null);
			}

			return fluidStack;
		}

		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if (this.container.getCount() != 1 || maxDrain < Fluid.BUCKET_VOLUME)
		{
			return null;
		}

		FluidStack fluidStack = this.getFluid();

		if (fluidStack != null)
		{
			if (doDrain)
			{
				this.setFluid(null);
			}

			return fluidStack;
		}

		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
		{
			return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
		}

		return null;
	}

}