package com.legacy.aether.common.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.legacy.aether.common.enchantments.AetherEnchantment;
import com.legacy.aether.common.enchantments.IEnchantmentHandler;
import com.legacy.aether.common.registry.AetherRegistry;
import com.legacy.aether.common.tile_entities.TileEntityEnchanter;

public class AetherEnchantmentEvent extends Event
{

	public AetherEnchantmentEvent()
	{
		
	}

	public static class Register extends AetherEnchantmentEvent
	{
		private AetherRegistry registry;

		public Register(AetherRegistry registry)
		{
			super();

			this.registry = registry;
		}

		public void registerEnchantmentHandler(IEnchantmentHandler handler)
		{
			this.registry.registerEnchantmentHandler(handler);
		}
	}

	public static class SetTime extends AetherEnchantmentEvent
	{
		private TileEntityEnchanter tileEntity;

		private int original;

		private int newTime;

		public SetTime(TileEntityEnchanter tileEntity, int original)
		{
			this.tileEntity = tileEntity;
			this.original = original;

			this.setNewTime(original);
		}

		public TileEntityEnchanter getTileEntity()
		{
			return this.tileEntity;
		}

		public int getOriginal()
		{
			return this.original;
		}

		public int getNewTime()
		{
			return this.newTime;
		}

		public void setNewTime(int newTime)
		{
			this.newTime = newTime;
		}
	}

	public static class Enchant extends AetherEnchantmentEvent
	{
		private TileEntityEnchanter tileEntity;

		private AetherEnchantment enchantent;

		public Enchant(TileEntityEnchanter tileEntity, AetherEnchantment enchantment)
		{
			this.tileEntity = tileEntity;
			this.enchantent = enchantment;
		}

		public TileEntityEnchanter getTileEntity()
		{
			return this.tileEntity;
		}

		public AetherEnchantment getEnchantment()
		{
			return this.enchantent;
		}
	}

}