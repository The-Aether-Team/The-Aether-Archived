package com.legacy.aether.world.biome;

import com.legacy.aether.block.BlocksAether;
import com.legacy.aether.world.gen.surfacebuilders.HighlandsSurfaceBuilder;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.CompositeSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;

public class HighlandsBiome extends Biome
{

	@ObjectHolder("aether_legacy:highlands")
	public static final HighlandsBiome INSTANCE = null;

	public static final SurfaceBuilderConfig AETHER_GRASS_DIRT_HOLYSTONE_SURFACE = new SurfaceBuilderConfig(BlocksAether.AETHER_GRASS.getDefaultState(), BlocksAether.AETHER_DIRT.getDefaultState(), BlocksAether.HOLYSTONE.getDefaultState());

	public static final ISurfaceBuilder<SurfaceBuilderConfig> AETHER_HIGHLANDS_SURFACE_BUILDER = new HighlandsSurfaceBuilder();

	public HighlandsBiome()
	{
		super((new BiomeBuilder()).surfaceBuilder(new CompositeSurfaceBuilder<>(AETHER_HIGHLANDS_SURFACE_BUILDER, AETHER_GRASS_DIRT_HOLYSTONE_SURFACE)).precipitation(RainType.NONE).category(Category.FOREST).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.0F).waterColor(11139071).waterFogColor(11139071).parent((String) null));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public int getSkyColorByTemp(float currentTemperature)
	{
		return 12632319;
	}

	@Override
	public int getGrassColor(BlockPos pos)
	{
		return 11665355;
	}

	@Override
	public int getFoliageColor(BlockPos pos)
	{
		return 11665355;
	}

}