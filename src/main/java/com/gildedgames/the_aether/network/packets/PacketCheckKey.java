package com.gildedgames.the_aether.network.packets;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.the_aether.registry.AetherLore;

import io.netty.buffer.ByteBuf;

public class PacketCheckKey extends AetherPacket<PacketCheckKey> {

    private boolean bool;

    public PacketCheckKey() {

    }

    public PacketCheckKey(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.bool = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.bool);
    }

    @Override
    public void handleClient(PacketCheckKey message, EntityPlayer player) {

    }

    @Override
    public void handleServer(PacketCheckKey message, EntityPlayer player) {
        AetherLore.hasKey = message.bool;
    }
}
