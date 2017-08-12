package com.legacy.aether.common.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.legacy.aether.common.entities.util.MoaColor;
import com.legacy.aether.common.registry.AetherRegistry;
import com.legacy.aether.common.tile_entities.TileEntityIncubator;

public class MoaEggEvent extends Event
{

	public MoaEggEvent()
	{

	}

	public static class Register extends MoaEggEvent
	{
		private AetherRegistry registry;

		public Register(AetherRegistry registry)
		{
			super();

			this.registry = registry;
		}

		public MoaColor registerMoaEgg(MoaColor color)
		{
			this.registry.registerMoaColor(color);

			return color;
		}
	}

	public static class Hatch extends MoaEggEvent
	{
		private TileEntityIncubator incubator;

		private MoaColor color;

		public Hatch(TileEntityIncubator incubator, MoaColor color)
		{
			this.color = color;
			this.incubator = incubator;
		}

		public TileEntityIncubator getTileEntity()
		{
			return this.incubator;
		}

		public MoaColor getMoaColor()
		{
			return this.color;
		}
	}
}