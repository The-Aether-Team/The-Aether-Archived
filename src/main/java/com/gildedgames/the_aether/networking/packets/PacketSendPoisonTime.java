package com.gildedgames.the_aether.networking.packets;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSendPoisonTime extends AetherPacket<PacketSendPoisonTime>
{
    private int entityID;
    private int time;

    public PacketSendPoisonTime()
    {

    }

    public PacketSendPoisonTime(EntityPlayer thePlayer, int time)
    {
        this.entityID = thePlayer.getEntityId();
        this.time = time;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.time = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeInt(this.time);
    }

    @Override
    public void handleClient(PacketSendPoisonTime message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                ((PlayerAether) AetherAPI.getInstance().get(parent)).poisonTime = message.time;
            }
        }
    }

    @Override
    public void handleServer(PacketSendPoisonTime message, EntityPlayer player)
    {
        
    }
}
