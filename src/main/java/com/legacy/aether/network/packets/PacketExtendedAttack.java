package com.legacy.aether.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Objects;

public class PacketExtendedAttack extends AetherPacket<PacketExtendedAttack>
{
    int entityID;

    public PacketExtendedAttack()
    {

    }

    public PacketExtendedAttack(int entityID)
    {
        this.entityID = entityID;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
    }

    @Override
    public void handleClient(PacketExtendedAttack message, EntityPlayer player)
    {

    }

    @Override
    public void handleServer(PacketExtendedAttack message, EntityPlayer player)
    {
        player.attackTargetEntityWithCurrentItem(Objects.requireNonNull(player.worldObj.getEntityByID(message.entityID)));
    }
}
