package com.gildedgames.the_aether.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;

public class EternalDayManager
{
    private final WorldServer world;

    private boolean eternalDay;
    private boolean shouldCycleCatchup;
    private long time;

    public EternalDayManager(WorldServer worldIn, NBTTagCompound compound)
    {
        this.world = worldIn;

        if (compound.hasKey("EternalDay", 99))
        {
            this.eternalDay = compound.getBoolean("EternalDay");
        }
        else
        {
            this.eternalDay = true;
        }

        if (compound.hasKey("ShouldCycleCatchup", 99))
        {
            this.shouldCycleCatchup = compound.getBoolean("ShouldCycleCatchup");
        }
        else
        {
            this.shouldCycleCatchup = true;
        }

        if (compound.hasKey("Time", 99))
        {
            this.time = compound.getLong("Time");
        }
        else
        {
            this.time = 6000;
        }
    }

    public NBTTagCompound getCompound()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.setBoolean("EternalDay", this.eternalDay);
        nbttagcompound.setBoolean("ShouldCycleCatchup", this.shouldCycleCatchup);
        nbttagcompound.setLong("Time", this.time);

        return nbttagcompound;
    }

    public boolean isEternalDay()
    {
        return this.eternalDay;
    }

    public void setEternalDay(boolean set)
    {
        this.eternalDay = set;
    }

    public boolean shouldCycleCatchup()
    {
        return this.shouldCycleCatchup;
    }

    public void setShouldCycleCatchup(boolean set)
    {
        this.shouldCycleCatchup = set;
    }

    public long getTime()
    {
        return this.time;
    }

    public void setTime(long set)
    {
        this.time = set;
    }
}
