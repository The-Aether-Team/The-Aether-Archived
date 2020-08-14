package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.client.gui.dialogue.server.GuiServerDialogue;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketDisplayDialogue extends AetherPacket<PacketDisplayDialogue> {

	public String dialogueName, dialogue;

	public ArrayList<String> dialogueText;

	public PacketDisplayDialogue() {

	}

	public PacketDisplayDialogue(String dialogueName, String dialogue, String... dialogueText) {
		this(dialogueName, dialogue, (ArrayList<String>) null);

		ArrayList<String> array = new ArrayList<String>();

		array.addAll(Arrays.asList(dialogueText));

		this.dialogueText = array;
	}

	public PacketDisplayDialogue(String dialogueName, String dialogue, ArrayList<String> dialogueText) {
		this.dialogueName = dialogueName;
		this.dialogue = dialogue;
		this.dialogueText = dialogueText;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.dialogueName = ByteBufUtils.readUTF8String(buf);
		this.dialogue = ByteBufUtils.readUTF8String(buf);
		this.dialogueText = new ArrayList<String>();

		int size = buf.readInt();

		for (int data = 0; data < size; ++data) {
			this.dialogueText.add(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.dialogue);
		ByteBufUtils.writeUTF8String(buf, this.dialogueName);
		buf.writeInt(this.dialogueText.size());

		for (String dialogueForOptions : this.dialogueText) {
			ByteBufUtils.writeUTF8String(buf, dialogueForOptions);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleClient(PacketDisplayDialogue message, EntityPlayer player) {
		FMLClientHandler.instance().getClient().displayGuiScreen(new GuiServerDialogue(message.dialogueName, message.dialogue, message.dialogueText));
	}

	@Override
	public void handleServer(PacketDisplayDialogue message, EntityPlayer player) {

	}

}