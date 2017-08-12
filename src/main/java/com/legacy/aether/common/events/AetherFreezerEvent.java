package com.legacy.aether.common.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.legacy.aether.common.freezables.AetherFreezable;
import com.legacy.aether.common.freezables.IFreezableHandler;
import com.legacy.aether.common.registry.AetherRegistry;
import com.legacy.aether.common.tile_entities.TileEntityFreezer;

public class AetherFreezerEvent extends Event
{

	public AetherFreezerEvent()
	{
		
	}

	public static class Register extends AetherFreezerEvent
	{
		private AetherRegistry registry;

		public Register(AetherRegistry registry)
		{
			super();

			this.registry = registry;
		}

		public void registerFreezableHandler(IFreezableHandler handler)
		{
			this.registry.registerFreezableHandler(handler);
		}
	}

	public static class SetTime extends AetherFreezerEvent
	{
		private TileEntityFreezer tileEntity;

		private int original;

		private int newTime;

		public SetTime(TileEntityFreezer tileEntity, int original)
		{
			this.tileEntity = tileEntity;
			this.original = original;

			this.setNewTime(original);
		}

		public TileEntityFreezer getTileEntity()
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

	public static class Freeze extends AetherFreezerEvent
	{
		private TileEntityFreezer tileEntity;

		private AetherFreezable enchantent;

		public Freeze(TileEntityFreezer tileEntity, AetherFreezable enchantment)
		{
			this.tileEntity = tileEntity;
			this.enchantent = enchantment;
		}

		public TileEntityFreezer getTileEntity()
		{
			return this.tileEntity;
		}

		public AetherFreezable getFreezable()
		{
			return this.enchantent;
		}
	}

}