package com.gildedgames.the_aether.networking.packets;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityIceyBall;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketIceParticles extends AetherPacket<PacketIceParticles>
{
    private int entityID;

    public PacketIceParticles()
    {

    }

    public PacketIceParticles(EntityIceyBall iceyBall)
    {
        this.entityID = iceyBall.getEntityId();
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
    public void handleClient(PacketIceParticles message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityIceyBall parent = (EntityIceyBall) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                Aether.proxy.spawnSplode(parent.world, parent.posX, parent.posY, parent.posZ);
            }
        }
    }

    @Override
    public void handleServer(PacketIceParticles message, EntityPlayer player)
    {

    }
}
