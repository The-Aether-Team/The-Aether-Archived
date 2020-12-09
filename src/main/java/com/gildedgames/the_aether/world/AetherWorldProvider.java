package com.gildedgames.the_aether.world;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketSendTime;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherWorldProvider extends WorldProvider {

	private float[] colorsSunriseSunset = new float[4];

	private boolean eternalDay;
	private boolean shouldCycleCatchup;
	private long aetherTime = 6000;

	public AetherWorldProvider() {
		super();
	}

	@Override
	protected void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerAether();
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		if (!AetherConfig.eternalDayDisabled())
		{
			if (!this.worldObj.isRemote)
			{
				AetherData data = AetherData.getInstance(this.worldObj);

				if (data.isEternalDay())
				{
					if (!data.isShouldCycleCatchup())
					{
						if (data.getAetherTime() != (worldTime % 24000L) && data.getAetherTime() != (worldTime + 1 % 24000L) && data.getAetherTime() != (worldTime - 1 % 24000L))
						{
							data.setAetherTime(Math.floorMod(data.getAetherTime() - 1, 24000L));
						}
						else
						{
							data.setShouldCycleCatchup(true);
						}
					}
					else
					{
						data.setAetherTime(worldTime);
					}

					this.aetherTime = data.getAetherTime();
					AetherNetwork.sendToAll(new PacketSendTime(this.aetherTime));
					data.setAetherTime(this.aetherTime);
				}
				else
				{
					data.setAetherTime(6000);
				}
			}
		}

		int i = (int)(AetherConfig.eternalDayDisabled() ? worldTime : this.aetherTime % 24000L);

		float f = ((float)i + partialTicks) / 24000.0F - 0.25F;

		if (f < 0.0F)
		{
			++f;
		}

		if (f > 1.0F)
		{
			--f;
		}

		float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
		f = f + (f1 - f) / 3.0F;
		return f;
	}

	public void setIsEternalDay(boolean set)
	{
		this.eternalDay = set;
	}

	public boolean getIsEternalDay()
	{
		return this.eternalDay;
	}

	public void setShouldCycleCatchup(boolean set)
	{
		this.shouldCycleCatchup = set;
	}

	public boolean getShouldCycleCatchup()
	{
		return this.shouldCycleCatchup;
	}

	public void setAetherTime(long time)
	{
		this.aetherTime = time;
	}

	public long getAetherTime()
	{
		return this.aetherTime;
	}

	@Override
	public float[] calcSunriseSunsetColors(float f, float f1) {
		float f2 = 0.4F;
		float f3 = MathHelper.cos(f * 3.141593F * 2.0F) - 0.0F;
		float f4 = -0F;

		if (f3 >= f4 - f2 && f3 <= f4 + f2) {
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = f5 * 0.3F + 0.1F;
			this.colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f5 * f5 * 0.7F + 0.2F;
			this.colorsSunriseSunset[3] = f6;
			return this.colorsSunriseSunset;
		} else {
			return null;
		}
	}

	@Override
	public int getRespawnDimension(EntityPlayerMP player)
	{
		return PlayerAether.get(player).getBedLocation() == null ? 0 : AetherConfig.getAetherDimensionID();
	}

	@Override
	public boolean canCoordinateBeSpawn(int i, int j) {
		return false;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderAether(this.worldObj, this.worldObj.getSeed());
	}

	public boolean canDoLightning(Chunk chunk) {
		return false;
	}

	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public Vec3 getFogColor(float f, float f1) {
		int i = 0x9393BC;

		float f2 = MathHelper.cos(f * 3.141593F * 2.0F) * 2.0F + 0.5F;
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}
		if (f2 > 1.0F) {
			f2 = 1.0F;
		}
		float f3 = (i >> 16 & 0xff) / 255F;
		float f4 = (i >> 8 & 0xff) / 255F;
		float f5 = (i & 0xff) / 255F;
		f3 *= f2 * 0.94F + 0.06F;
		f4 *= f2 * 0.94F + 0.06F;
		f5 *= f2 * 0.91F + 0.09F;

		return Vec3.createVectorHelper(f3, f4, f5);
	}

	@Override
	public String getSaveFolder() {
		return "Dim-Aether";
	}

	@Override
	public double getVoidFogYFactor() {
		return 100;
	}

	@Override
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	@Override
	public boolean isSkyColored() {
		return false;
	}

	@Override
	public double getHorizon() {
		return 0.0;
	}

	@Override
	public float getCloudHeight() {
		return -5F;
	}

	@Override
	public String getDimensionName() {
		return "the_aether";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean getWorldHasVoidParticles() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public net.minecraftforge.client.IRenderHandler getWeatherRenderer() {
		return new IRenderHandler() {
			@Override
			public void render(float partialTicks, net.minecraft.client.multiplayer.WorldClient world, net.minecraft.client.Minecraft mc) {

			}
		};
	}
}