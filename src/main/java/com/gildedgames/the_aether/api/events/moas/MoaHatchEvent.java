package com.gildedgames.the_aether.api.events.moas;

import com.gildedgames.the_aether.api.moa.AetherMoaType;
import net.minecraft.tileentity.TileEntity;

public class MoaHatchEvent extends MoaEvent {

	private TileEntity incubator;

	public MoaHatchEvent(AetherMoaType moaType, TileEntity incubator) {
		super(moaType);

		this.incubator = incubator;
	}

	public TileEntity getTileEntity() {
		return this.incubator;
	}

}