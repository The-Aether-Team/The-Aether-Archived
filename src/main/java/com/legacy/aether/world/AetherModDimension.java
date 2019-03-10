package com.legacy.aether.world;

import com.legacy.aether.Aether;
import com.legacy.aether.world.dimension.AetherDimension;
import java.util.function.Function;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.registries.ObjectHolder;

public class AetherModDimension extends ModDimension
{

	@ObjectHolder("aether_legacy:aether")
	public static final AetherModDimension INSTANCE = null;

	public final AetherDaylightManager daylightManager = new AetherDaylightManager();

	public static DimensionType getDimensionType()
	{
		return DimensionType.byName(Aether.locate("aether"));
	}

	@Override
	public void write(PacketBuffer buffer, boolean network)
	{
		this.daylightManager.write(buffer);
	}

	@Override
	public void read(PacketBuffer buffer, boolean network)
	{
		this.daylightManager.read(buffer);
	}

	@Override
	public Function<DimensionType, ? extends Dimension> getFactory()
	{
		return AetherDimension::new;
	}

}