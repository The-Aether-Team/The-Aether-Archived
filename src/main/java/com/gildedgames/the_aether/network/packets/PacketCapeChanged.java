package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketCapeChanged extends AetherPacket<PacketCapeChanged>
{
    public int entityID;

    public boolean renderCape;

    public PacketCapeChanged() {

    }

    public PacketCapeChanged(int entityID, boolean info) {
        this.entityID = entityID;
        this.renderCape = info;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
        this.renderCape = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.renderCape);
    }

    @Override
    public void handleClient(PacketCapeChanged message, EntityPlayer player) {
        if (player != null && player.worldObj != null) {
            EntityPlayer parent = (EntityPlayer) player.worldObj.getEntityByID(message.entityID);

            if (parent != null) {
                PlayerAether instance = PlayerAether.get(parent);

                instance.shouldRenderCape = message.renderCape;
            }
        }
    }

    @Override
    public void handleServer(PacketCapeChanged message, EntityPlayer player) {
        if (player != null && player.worldObj != null && !player.worldObj.isRemote) {
            EntityPlayer parent = (EntityPlayer) player.worldObj.getEntityByID(message.entityID);

            if (parent != null) {
                PlayerAether instance = PlayerAether.get(parent);

                instance.shouldRenderCape = message.renderCape;
            }
        }
    }
}
