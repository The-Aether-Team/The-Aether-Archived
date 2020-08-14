package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.world.AetherWorldProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;

public class PacketSendTime extends AetherPacket<PacketSendTime>
{
    private long time;

    public PacketSendTime()
    {

    }

    public PacketSendTime(long time)
    {
        this.time = time;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.time = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.time);
    }

    @Override
    public void handleClient(PacketSendTime message, EntityPlayer player)
    {
        if (player != null && player.worldObj != null && player.worldObj.provider != null)
        {
            WorldProvider provider = player.worldObj.provider;

            if (provider instanceof AetherWorldProvider)
            {
                AetherWorldProvider providerAether = (AetherWorldProvider) provider;

                providerAether.setAetherTime(message.time);
            }
        }
    }

    @Override
    public void handleServer(PacketSendTime message, EntityPlayer player)
    {

    }
}