package com.gildedgames.the_aether.world;

import com.gildedgames.the_aether.Aether;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class AetherData extends WorldSavedData
{
    final static String key = Aether.MOD_ID;

    private NBTTagCompound data = new NBTTagCompound();

    private boolean eternalDay;
    private boolean shouldCycleCatchup;
    private long aetherTime;

    public AetherData (String tagName) {
        super(tagName);
    }

    public static AetherData getInstance(World world) {
        MapStorage storage = world.mapStorage;
        AetherData result = (AetherData) storage.loadData(AetherData.class, key);
        if (result == null) {
            result = new AetherData(key);
            storage.setData(key, result);
        }
        return result;
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        data = compound.getCompoundTag(key);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setTag(key, data);
    }

    public NBTTagCompound getData() {
        return data;
    }

    public boolean isEternalDay()
    {
        this.eternalDay = data.getBoolean("EternalDay");
        return this.eternalDay;
    }

    public void setEternalDay(boolean eternalDay)
    {
        this.eternalDay = eternalDay;
        data.setBoolean("EternalDay", this.eternalDay);
        this.markDirty();
    }

    public boolean isShouldCycleCatchup()
    {
        this.shouldCycleCatchup = data.getBoolean("ShouldCycleCatchup");
        return this.shouldCycleCatchup;
    }

    public void setShouldCycleCatchup(boolean shouldCycleCatchup)
    {
        this.shouldCycleCatchup = shouldCycleCatchup;
        data.setBoolean("ShouldCycleCatchup", this.shouldCycleCatchup);
        this.markDirty();
    }

    public long getAetherTime()
    {
        this.aetherTime = data.getLong("Time");
        return this.aetherTime;
    }

    public void setAetherTime(long aetherTime)
    {
        this.aetherTime = aetherTime;
        data.setLong("Time", this.aetherTime);
        this.markDirty();
    }
}
