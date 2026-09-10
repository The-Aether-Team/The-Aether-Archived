package com.gildedgames.the_aether.network.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.gildedgames.the_aether.items.ItemsAether;

import io.netty.buffer.ByteBuf;

public class PacketExtendedAttack extends AetherPacket<PacketExtendedAttack> {

    // The client only ever sends this for Valkyrie tools, whose extended reach
    // is 8 blocks (see AetherClientEvents.handleExtendedReach). Allow a small
    // tolerance for movement between the client's click and the server's check.
    private static final double MAX_REACH = 9.0D;

    int entityID;

    public PacketExtendedAttack() {

    }

    public PacketExtendedAttack(int entityID) {
        this.entityID = entityID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    @Override
    public void handleClient(PacketExtendedAttack message, EntityPlayer player) {

    }

    @Override
    public void handleServer(PacketExtendedAttack message, EntityPlayer player) {
        if (player == null) {
            return;
        }

        // The extended attack is only valid while holding a Valkyrie tool.
        if (player.getHeldItem() == null || !(player.getHeldItem()
            .getItem() == ItemsAether.valkyrie_lance
            || player.getHeldItem()
                .getItem() == ItemsAether.valkyrie_shovel
            || player.getHeldItem()
                .getItem() == ItemsAether.valkyrie_axe
            || player.getHeldItem()
                .getItem() == ItemsAether.valkyrie_pickaxe)) {
            return;
        }

        Entity entityFromID = player.worldObj.getEntityByID(message.entityID);

        if (entityFromID == null || entityFromID == player || entityFromID.isDead) {
            return;
        }

        // Enforce the extended reach server-side: distance from the player's
        // eyes to the entity's center must be within MAX_REACH.
        double dx = entityFromID.posX - player.posX;
        double dy = (entityFromID.boundingBox.minY + entityFromID.height / 2.0D)
            - (player.posY + player.getEyeHeight());
        double dz = entityFromID.posZ - player.posZ;

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (distance > MAX_REACH) {
            return;
        }

        // Line-of-sight check, mirroring the client's canEntityBeSeen gate.
        if (!player.canEntityBeSeen(entityFromID)) {
            return;
        }

        player.attackTargetEntityWithCurrentItem(entityFromID);
    }

}
