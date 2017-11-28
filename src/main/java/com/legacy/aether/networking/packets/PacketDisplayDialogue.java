package com.legacy.aether.networking.packets;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketDisplayDialogue extends AetherPacket<PacketDisplayDialogue>
{

	public String dialogueName, dialogue;

	public ArrayList<String> dialogueText;

	public PacketDisplayDialogue()
	{
		
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
	public void handleClient(PacketDisplayDialogue message, EntityPlayer player) 
	{
		FMLClientHandler.instance().getClient().displayGuiScreen(new com.legacy.aether.client.gui.dialogue.server.GuiServerDialogue(message.dialogueName, message.dialogue, message.dialogueText));
	}

	@Override
	public void handleServer(PacketDisplayDialogue message, EntityPlayer player)
	{

	}

}