package com.gildedgames.the_aether.network.packets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;

import io.netty.buffer.ByteBuf;

public class PacketInitiateValkyrieFight extends AetherPacket<PacketInitiateValkyrieFight> {

    public int slotId, entityId;

    public PacketInitiateValkyrieFight() {

    }

    public PacketInitiateValkyrieFight(int slotId, int entityId) {
        this.slotId = slotId;
        this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.slotId = buf.readInt();
        this.entityId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.slotId);
        buf.writeInt(this.entityId);
    }

    @Override
    public void handleClient(PacketInitiateValkyrieFight message, EntityPlayer player) {

    }

    @Override
    public void handleServer(PacketInitiateValkyrieFight message, EntityPlayer player) {
        if (player == null) {
            return;
        }

        // Validate the slot actually holds the required 10 victory medals
        // before consuming anything; the client GUI checks this, but the
        // server must not trust it.
        if (message.slotId < 0 || message.slotId >= player.inventory.mainInventory.length) {
            return;
        }

        ItemStack medalStack = player.inventory.mainInventory[message.slotId];

        if (medalStack == null || medalStack.getItem() != ItemsAether.victory_medal || medalStack.stackSize < 10) {
            return;
        }

        Entity entity = player.worldObj.getEntityByID(message.entityId);

        if (entity instanceof EntityValkyrieQueen) {
            EntityValkyrieQueen queen = (EntityValkyrieQueen) entity;

            // Don't restart an already-started duel or consume medals twice.
            if (queen.isBossReady()) {
                return;
            }

            // The dialogue is only opened by interacting with the Queen, so she
            // must be near the player; reject attempts against distant entities.
            double distance = player.getDistanceSqToEntity(queen);

            if (distance > 64.0D) {
                return;
            }

            // Consume exactly 10 medals (the entry fee) with proper inventory
            // sync; previously the entire stack in the slot was discarded.
            player.inventory.decrStackSize(message.slotId, 10);

            queen.setBossReady(true);
            PlayerAether.get(player)
                .setFocusedBoss(queen);
        }
    }

}
