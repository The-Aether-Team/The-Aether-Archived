package com.legacy.aether.events.freezables;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.tile_entities.TileEntityFreezer;

public class AetherFreezableEvent extends Event
{

	public AetherFreezableEvent()
	{
		
	}

	public static class SetTimeEvent extends AetherFreezableEvent
	{
		private TileEntityFreezer tileEntity;

		private AetherFreezable freezable;

		private int original;

		private int newTime;

		public SetTimeEvent(TileEntityFreezer tileEntity, AetherFreezable freezable, int original)
		{
			this.tileEntity = tileEntity;
			this.freezable = freezable;
			this.original = original;

			this.setNewTime(original);
		}

		public TileEntityFreezer getTileEntity()
		{
			return this.tileEntity;
		}

		public AetherFreezable getFreezable()
		{
			return this.freezable;
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

	public static class FreezeEvent extends AetherFreezableEvent
	{
		private TileEntityFreezer tileEntity;

		private AetherFreezable freezable;

		public FreezeEvent(TileEntityFreezer tileEntity, AetherFreezable freezable)
		{
			this.tileEntity = tileEntity;
			this.freezable = freezable;
		}

		public TileEntityFreezer getTileEntity()
		{
			return this.tileEntity;
		}

		public AetherFreezable getFreezable()
		{
			return this.freezable;
		}
	}

}