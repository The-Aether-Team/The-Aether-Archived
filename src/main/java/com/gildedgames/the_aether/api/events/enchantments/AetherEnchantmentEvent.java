package com.gildedgames.the_aether.api.events.enchantments;

import net.minecraft.tileentity.TileEntity;

import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;

import cpw.mods.fml.common.eventhandler.Event;

public class AetherEnchantmentEvent extends Event {

	public AetherEnchantmentEvent() {

	}

	public static class SetTimeEvent extends AetherEnchantmentEvent {
		private TileEntity tileEntity;

		private AetherEnchantment enchantment;

		private int original;

		private int newTime;

		public SetTimeEvent(TileEntity tileEntity, AetherEnchantment enchantment, int original) {
			this.tileEntity = tileEntity;
			this.enchantment = enchantment;
			this.original = original;

			this.setNewTime(original);
		}

		public TileEntity getTileEntity() {
			return this.tileEntity;
		}

		public AetherEnchantment getEnchantment() {
			return this.enchantment;
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

	public static class EnchantEvent extends AetherEnchantmentEvent {
		private TileEntity tileEntity;

		private AetherEnchantment enchantent;

		public EnchantEvent(TileEntity tileEntity, AetherEnchantment enchantment) {
			this.tileEntity = tileEntity;
			this.enchantent = enchantment;
		}

		public TileEntity getTileEntity() {
			return this.tileEntity;
		}

		public AetherEnchantment getEnchantment() {
			return this.enchantent;
		}
	}

}