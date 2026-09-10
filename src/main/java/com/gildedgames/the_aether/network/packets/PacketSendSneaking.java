package com.gildedgames.the_aether.network.packets;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.the_aether.player.PlayerAether;

import io.netty.buffer.ByteBuf;

public class PacketSendSneaking extends AetherPacket<PacketSendSneaking> {

    private int entityId;

    private boolean isSneaking;

    public PacketSendSneaking() {

    }

    public PacketSendSneaking(int entityId, boolean isSneaking) {
        this.entityId = entityId;
        this.isSneaking = isSneaking;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.isSneaking = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.isSneaking);
    }

    @Override
    public void handleClient(PacketSendSneaking message, EntityPlayer player) {

    }

    @Override
    public void handleServer(PacketSendSneaking message, EntityPlayer player) {
        if (player != null) {
            // The client only ever reports its own sneaking state; reject any
            // attempt to modify another player's mount state.
            if (message.entityId != player.getEntityId()) {
                return;
            }

            PlayerAether.get(player)
                .setMountSneaking(message.isSneaking);
        }
    }

}
