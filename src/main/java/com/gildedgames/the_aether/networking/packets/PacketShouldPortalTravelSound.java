package com.gildedgames.the_aether.networking.packets;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketShouldPortalTravelSound extends AetherPacket<PacketShouldPortalTravelSound>
{
    private int entityID;
    private boolean bool;

    public PacketShouldPortalTravelSound()
    {

    }

    public PacketShouldPortalTravelSound(EntityPlayer thePlayer, boolean bool)
    {
        this.entityID = thePlayer.getEntityId();
        this.bool = bool;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.bool = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.bool);
    }

    @Override
    public void handleClient(PacketShouldPortalTravelSound message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                PlayerAether playerAether = (PlayerAether) AetherAPI.getInstance().get(parent);

                playerAether.shouldPortalTravelSound(message.bool);
            }
        }
    }

    @Override
    public void handleServer(PacketShouldPortalTravelSound message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                PlayerAether playerAether = (PlayerAether) AetherAPI.getInstance().get(parent);

                playerAether.shouldPortalTravelSound(message.bool);
            }
        }
    }
}
