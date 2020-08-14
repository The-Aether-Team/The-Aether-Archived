package com.gildedgames.the_aether.api.events.freezables;

import net.minecraft.tileentity.TileEntity;

import com.gildedgames.the_aether.api.freezables.AetherFreezable;

import cpw.mods.fml.common.eventhandler.Event;

public class AetherFreezableEvent extends Event {

	public AetherFreezableEvent() {

	}

	public static class SetTimeEvent extends AetherFreezableEvent {
		private TileEntity tileEntity;

		private AetherFreezable freezable;

		private int original;

		private int newTime;

		public SetTimeEvent(TileEntity tileEntity, AetherFreezable freezable, int original) {
			this.tileEntity = tileEntity;
			this.freezable = freezable;
			this.original = original;

			this.setNewTime(original);
		}

		public TileEntity getTileEntity() {
			return this.tileEntity;
		}

		public AetherFreezable getFreezable() {
			return this.freezable;
		}

		public int getOriginal() {
			return this.original;
		}

		public int getNewTime() {
			return this.newTime;
		}

		public void setNewTime(int newTime) {
			this.newTime = newTime;
		}
	}

	public static class FreezeEvent extends AetherFreezableEvent {
		private TileEntity tileEntity;

		private AetherFreezable freezable;

		public FreezeEvent(TileEntity tileEntity, AetherFreezable freezable) {
			this.tileEntity = tileEntity;
			this.freezable = freezable;
		}

		public TileEntity getTileEntity() {
			return this.tileEntity;
		}

		public AetherFreezable getFreezable() {
			return this.freezable;
		}
	}

}