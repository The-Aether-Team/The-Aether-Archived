package com.gildedgames.the_aether.network.packets;

import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.network.AetherGuiHandler;

import io.netty.buffer.ByteBuf;

public class PacketOpenContainer extends AetherPacket<PacketOpenContainer> {

    public int id;

    public PacketOpenContainer() {

    }

    public PacketOpenContainer(int id) {
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
    }

    @Override
    public void handleClient(PacketOpenContainer message, EntityPlayer player) {}

    @Override
    public void handleServer(PacketOpenContainer message, EntityPlayer player) {
        // Only the accessories GUI and the "close current GUI" signal (-1) are
        // ever legitimately requested by clients. Anything else would either
        // open containers the player has no business opening remotely or crash
        // the server via bad tile entity casts in the GUI handler.
        if (message.id != AetherGuiHandler.accessories && message.id != -1) {
            return;
        }

        if (message.id == -1) {
            player.openContainer.onContainerClosed(player);
            player.openContainer = player.inventoryContainer;
        }

        player.openGui(
            Aether.instance,
            message.id,
            player.worldObj,
            (int) player.posX,
            (int) player.posY,
            (int) player.posZ);
    }

}
