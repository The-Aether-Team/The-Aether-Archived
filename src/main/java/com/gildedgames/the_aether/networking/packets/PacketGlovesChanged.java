package com.gildedgames.the_aether.networking.packets;

import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketGlovesChanged extends AetherPacket<PacketGlovesChanged>
{
    public int entityID;

    public boolean renderGloves;

    public PacketGlovesChanged()
    {

    }

    public PacketGlovesChanged(int entityID, boolean info)
    {
        this.entityID = entityID;

        this.renderGloves = info;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();

        this.renderGloves = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);

        buf.writeBoolean(this.renderGloves);
    }

    @Override
    public void handleClient(PacketGlovesChanged message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                IPlayerAether instance = AetherAPI.getInstance().get(parent);

                ((PlayerAether) instance).shouldRenderGloves = message.renderGloves;
            }
        }
    }

    @Override
    public void handleServer(PacketGlovesChanged message, EntityPlayer player)
    {
        if (player != null && player.world != null && !player.world.isRemote)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                IPlayerAether instance = AetherAPI.getInstance().get(parent);

                ((PlayerAether) instance).shouldRenderGloves = message.renderGloves;
            }
        }
    }
}
