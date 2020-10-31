package com.gildedgames.the_aether.networking.packets;

import java.util.ArrayList;
import java.util.Arrays;

import com.gildedgames.the_aether.client.gui.dialogue.server.GuiServerDialogue;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketDisplayDialogue extends AetherPacket<PacketDisplayDialogue>
{

	public String dialogueName, dialogue;

	public ArrayList<String> dialogueText;

	public PacketDisplayDialogue()
	{
		
	}

	public PacketDisplayDialogue(String dialogueName, String dialogue, String... dialogueText)
	{
		this(dialogueName, dialogue, (ArrayList<String>) null);

		ArrayList<String> array = new ArrayList<String>();

		array.addAll(Arrays.asList(dialogueText));

		this.dialogueText = array;
	}

	public PacketDisplayDialogue(String dialogueName, String dialogue, ArrayList<String> dialogueText)
	{
		this.dialogueName = dialogueName;
		this.dialogue = dialogue;
		this.dialogueText = dialogueText;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dialogueName = ByteBufUtils.readUTF8String(buf);
		this.dialogue = ByteBufUtils.readUTF8String(buf);
		this.dialogueText = new ArrayList<String>();

		int size = buf.readInt();

		for (int data = 0; data < size; ++data)
		{
			this.dialogueText.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.dialogue);
		ByteBufUtils.writeUTF8String(buf, this.dialogueName);
		buf.writeInt(this.dialogueText.size());

		for (String dialogueForOptions : this.dialogueText)
		{
			ByteBufUtils.writeUTF8String(buf, dialogueForOptions);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClient(PacketDisplayDialogue message, EntityPlayer player) 
	{
		FMLClientHandler.instance().getClient().displayGuiScreen(new GuiServerDialogue(message.dialogueName, message.dialogue, message.dialogueText));
	}

	@Override
	public void handleServer(PacketDisplayDialogue message, EntityPlayer player)
	{

	}

}