package com.legacy.aether.common.registry;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.common.enchantments.AetherEnchantment;
import com.legacy.aether.common.enchantments.IEnchantmentHandler;
import com.legacy.aether.common.entities.util.MoaColor;
import com.legacy.aether.common.freezables.AetherFreezable;
import com.legacy.aether.common.freezables.IFreezableHandler;

public class AetherRegistry
{

	private ArrayList<IEnchantmentHandler> enchantmentHandlers = new ArrayList<IEnchantmentHandler>();

	private ArrayList<IFreezableHandler> freezableHandlers = new ArrayList<IFreezableHandler>();

	private ArrayList<MoaColor> moaColors = new ArrayList<MoaColor>();

	public boolean isEnchantment(ItemStack enchantment)
	{
		if (enchantment == null)
		{
			return false;
		}

		for (IEnchantmentHandler handler : enchantmentHandlers)
		{
			if (handler.getEnchantment(enchantment) != null)
			{
				return true;
			}
		}

		return false;
	}

	public boolean isFreezable(ItemStack freezable)
	{
		if (freezable == null)
		{
			return false;
		}

		for (IFreezableHandler handler : freezableHandlers)
		{
			if (handler.getFreezable(freezable) != null)
			{
				return true;
			}
		}

		return false;
	}

	public boolean isEnchantmentFuel(ItemStack fuel)
	{
		if (fuel == null)
		{
			return false;
		}

		for (IEnchantmentHandler handler : enchantmentHandlers)
		{
			if (handler.getEnchantmentFuelTime(fuel) > 0)
			{
				return true;
			}
		}

		return false;
	}

	public boolean isFreezerFuel(ItemStack fuel)
	{
		if (fuel == null)
		{
			return false;
		}

		for (IFreezableHandler handler : freezableHandlers)
		{
			if (handler.getFreezerFuelTime(fuel) > 0)
			{
				return true;
			}
		}

		return false;
	}

	public boolean isEnchantmentResult(ItemStack result, AetherEnchantment enchantment)
	{
		return result != null && enchantment != null ? result.getItem() == enchantment.getResult().getItem() && result.getMetadata() == enchantment.getResult().getMetadata() : false;
	}

	public boolean isFreezableResult(ItemStack result, AetherFreezable freezable)
	{
		return result != null && freezable != null ? result.getItem() == freezable.getResult().getItem() && result.getMetadata() == freezable.getResult().getMetadata() : false;
	}

	public boolean areEnchantmentsEqual(ItemStack first, AetherEnchantment second)
	{
		AetherEnchantment firstEnchantment = this.getEnchantment(first);

		return firstEnchantment != null && second != null ? firstEnchantment.equals(second) : false;
	}

	public boolean areFreezablesEqual(ItemStack first, AetherFreezable second)
	{
		AetherFreezable firstFreezable = this.getFreezable(first);

		return firstFreezable != null && second != null ? firstFreezable.equals(second) : false;
	}

	public MoaColor getMoaColor(int id)
	{
		if (id <= -1 || id >= this.moaColors.size())
		{
			return this.moaColors.get(0);
		}

		return this.moaColors.get(id);
	}

	public MoaColor getRandomColor(World world)
	{
		int i = world.rand.nextInt(this.moaColors.size());

		return this.moaColors.get(i);
	}

	public AetherEnchantment getEnchantment(ItemStack enchantment)
	{
		if (enchantment == null)
		{
			return null;
		}

		for (IEnchantmentHandler handler : enchantmentHandlers)
		{
			return handler.getEnchantment(enchantment);
		}

		return null;
	}

	public AetherFreezable getFreezable(ItemStack freezable)
	{
		if (freezable == null)
		{
			return null;
		}

		for (IFreezableHandler handler : freezableHandlers)
		{
			return handler.getFreezable(freezable);
		}

		return null;
	}

	public int getEnchantmentFuel(ItemStack fuel)
	{
		if (fuel == null)
		{
			return 0;
		}

		for (IEnchantmentHandler handler : enchantmentHandlers)
		{
			return handler.getEnchantmentFuelTime(fuel);
		}

		return 0;
	}

	public int getFreezerFuel(ItemStack fuel)
	{
		if (fuel == null)
		{
			return 0;
		}

		for (IFreezableHandler handler : freezableHandlers)
		{
			return handler.getFreezerFuelTime(fuel);
		}

		return 0;
	}

	public void registerMoaColor(MoaColor color)
	{
		this.moaColors.add(color);
	}

	public void registerEnchantmentHandler(IEnchantmentHandler handler)
	{
		this.enchantmentHandlers.add(handler);
	}

	public void registerFreezableHandler(IFreezableHandler handler)
	{
		this.freezableHandlers.add(handler);
	}

	public IEnchantmentHandler getEnchantmentHandler(int enchantmentHandlerID)
	{
		return this.enchantmentHandlers.get(enchantmentHandlerID);
	}

	public IFreezableHandler getFreezableHandler(int enchantmentHandlerID)
	{
		return this.freezableHandlers.get(enchantmentHandlerID);
	}

	public ArrayList<MoaColor> getMoaColors()
	{
		return this.moaColors;
	}

	public ArrayList<IEnchantmentHandler> getEnchantmentHandlers()
	{
		return this.enchantmentHandlers;
	}

	public ArrayList<IFreezableHandler> getFreezableHandlers()
	{
		return this.freezableHandlers;
	}

	public int getMoaColorSize()
	{
		return this.moaColors.size();
	}

	public int getEnchantmentHandlerSize()
	{
		return this.enchantmentHandlers.size();
	}

	public int getFreezableHandlerSize()
	{
		return this.freezableHandlers.size();
	}

}