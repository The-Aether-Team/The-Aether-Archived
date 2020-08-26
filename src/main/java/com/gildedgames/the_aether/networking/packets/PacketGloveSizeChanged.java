package com.gildedgames.the_aether.networking.packets;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketGloveSizeChanged extends AetherPacket<PacketGloveSizeChanged> {
    public int entityID;

    public boolean renderGloveSize;

    public PacketGloveSizeChanged()
    {

    }

    public PacketGloveSizeChanged(int entityID, boolean info)
    {
        this.entityID = entityID;

        this.renderGloveSize = info;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();

        this.renderGloveSize = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);

        buf.writeBoolean(this.renderGloveSize);
    }

    @Override
    public void handleClient(PacketGloveSizeChanged message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                IPlayerAether instance = AetherAPI.getInstance().get(parent);

                ((PlayerAether) instance).gloveSize = message.renderGloveSize;
            }
        }
    }

    @Override
    public void handleServer(PacketGloveSizeChanged message, EntityPlayer player)
    {
        if (player != null && player.world != null && !player.world.isRemote)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                IPlayerAether instance = AetherAPI.getInstance().get(parent);

                ((PlayerAether) instance).gloveSize = message.renderGloveSize;
            }
        }
    }
}