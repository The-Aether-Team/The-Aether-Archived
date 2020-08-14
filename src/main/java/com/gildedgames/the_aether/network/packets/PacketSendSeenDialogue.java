package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSendSeenDialogue extends AetherPacket<PacketSendSeenDialogue>
{
    private int entityID;
    private boolean dialogue;

    public PacketSendSeenDialogue()
    {

    }

    public PacketSendSeenDialogue(EntityPlayer thePlayer, boolean dialogue)
    {
        this.entityID = thePlayer.getEntityId();
        this.dialogue = dialogue;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.dialogue = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.dialogue);
    }

    @Override
    public void handleClient(PacketSendSeenDialogue message, EntityPlayer player)
    {
        if (player != null && player.worldObj != null)
        {
            EntityPlayer parent = (EntityPlayer) player.worldObj.getEntityByID(message.entityID);

            if (parent != null)
            {
                ((PlayerAether) AetherAPI.get(parent)).seenSpiritDialog = message.dialogue;
            }
        }
    }

    @Override
    public void handleServer(PacketSendSeenDialogue message, EntityPlayer player)
    {

    }
}