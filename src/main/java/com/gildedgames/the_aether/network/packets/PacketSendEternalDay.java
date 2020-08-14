package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.world.AetherWorldProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldProvider;

public class PacketSendEternalDay extends AetherPacket<PacketSendEternalDay>
{
    private boolean eternalDay;

    public PacketSendEternalDay()
    {

    }

    public PacketSendEternalDay(boolean eternalDay)
    {
        this.eternalDay = eternalDay;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.eternalDay = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.eternalDay);
    }

    @Override
    public void handleClient(PacketSendEternalDay message, EntityPlayer player)
    {
        if (player != null && player.worldObj != null && player.worldObj.provider != null)
        {
            WorldProvider provider = player.worldObj.provider;

            if (provider instanceof AetherWorldProvider)
            {
                AetherWorldProvider providerAether = (AetherWorldProvider) provider;

                providerAether.setIsEternalDay(message.eternalDay);
            }
        }
    }

    @Override
    public void handleServer(PacketSendEternalDay message, EntityPlayer player)
    {

    }
}
