package com.legacy.aether.world.dimension;

import com.legacy.aether.world.AetherModDimension;
import com.legacy.aether.world.biome.HighlandsBiome;
import com.legacy.aether.world.gen.ChunkGeneratorAether;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherDimension extends Dimension
{

	private final float[] colorsSunriseSunset = new float[4];

	private final DimensionType type;

	public AetherDimension(DimensionType type)
	{
		this.type = type;
	}

	@Override
	protected void init()
	{
		this.hasSkyLight = true;
	}

	@Override
	public IChunkGenerator<?> createChunkGenerator()
	{
		return new ChunkGeneratorAether(this.world, BiomeProviderType.FIXED.create(((SingleBiomeProviderSettings) BiomeProviderType.FIXED.createSettings()).setBiome(HighlandsBiome.INSTANCE)));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
	{
		float f3 = MathHelper.cos(celestialAngle * 3.141593F * 2.0F) - 0.0F;

		if (f3 >= -0.4F && f3 <= 0.4F)
		{
			float f5 = f3 / 0.4F * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
			this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[3] = f6;
			return this.colorsSunriseSunset;
		}

		return null;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		if (!((AetherModDimension) this.getType().getModType()).daylightManager.isSunSpiritDefeated())
		{
			return 0.0F;
		}
		else
		{
			int i = (int) (worldTime % 24000L);
			float f = ((float) i + partialTicks) / 24000.0F - 0.25F;

			if (f < 0.0F)
			{
				++f;
			}

			if (f > 1.0F)
			{
				--f;
			}

			float f1 = 1.0F - (float) ((Math.cos((double) f * 3.141592653589793D) + 1.0D) / 2.0D);

			f += (f1 - f) / 3.0F;

			return f;
		}
	}

	@Override
	public BlockPos findSpawn(ChunkPos pos, boolean checkValid)
	{
		return null;
	}

	@Override
	public BlockPos findSpawn(int x, int z, boolean checkValid)
	{
		return null;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks)
	{
		float multiplier = MathHelper.cos(celestialAngle * 6.2831855F) * 2.0F + 0.5F;

		multiplier = MathHelper.clamp(multiplier, 0.0F, 1.0F);

		float r = 0.6019608F;
		float g = 0.6019608F;
		float b = 0.627451F;

		r *= multiplier * 0.94F + 0.06F;
		g *= multiplier * 0.94F + 0.06F;
		b *= multiplier * 0.91F + 0.09F;

		return new Vec3d((double) r, (double) g, (double) b);
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return false;
	}

	@Override
	public DimensionType getType()
	{
		return this.type;
	}

}