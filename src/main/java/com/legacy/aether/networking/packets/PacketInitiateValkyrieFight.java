package com.legacy.aether.networking.packets;

import com.legacy.aether.client.gui.dialogue.entity.GuiValkyrieDialogue;
import com.legacy.aether.items.ItemsAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;

public class PacketInitiateValkyrieFight extends AetherPacket<PacketInitiateValkyrieFight>
{

	public int entityId;

	public PacketInitiateValkyrieFight()
	{

	}

	public PacketInitiateValkyrieFight(int entityId)
	{
		this.entityId = entityId;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);
	}

	@Override
	public void handleClient(PacketInitiateValkyrieFight message, EntityPlayer player)
	{

	}

	@Override
	public void handleServer(PacketInitiateValkyrieFight message, EntityPlayer player)
	{
		int medalsLeft = GuiValkyrieDialogue.MEDALS_NEEDED;

		for (int slotId = 0; slotId < player.inventory.mainInventory.size(); ++slotId)
		{
			ItemStack stack = player.inventory.mainInventory.get(slotId);

			if (stack.getItem() == ItemsAether.victory_medal)
			{
				if (stack.getCount() <= medalsLeft)
				{
					medalsLeft -= stack.getCount();
					player.inventory.setInventorySlotContents(slotId, ItemStack.EMPTY);
				}
				else
				{
					stack.setCount(stack.getCount()-medalsLeft);
					medalsLeft = 0;
				}
			}

			if (medalsLeft <= 0) break;
		}

		Entity entity = player.world.getEntityByID(message.entityId);

		if (entity instanceof EntityValkyrieQueen)
		{
			((EntityValkyrieQueen)entity).setBossReady(true);
			AetherAPI.getInstance().get(player).setFocusedBoss((EntityValkyrieQueen)entity);
		}
	}
}