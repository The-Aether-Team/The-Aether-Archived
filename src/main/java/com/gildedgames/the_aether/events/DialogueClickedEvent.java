package com.gildedgames.the_aether.events;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.Event;

public class DialogueClickedEvent extends Event {

	private EntityPlayer player;

	private String dialogueName;

	private int dialogueId;

	public DialogueClickedEvent(EntityPlayer player, String dialogueName, int dialogueId) {
		this.player = player;
		this.dialogueName = dialogueName;
		this.dialogueId = dialogueId;
	}

	public EntityPlayer getPlayer() {
		return this.player;
	}

	public String getDialogueName() {
		return this.dialogueName;
	}

	public int getDialogueId() {
		return this.dialogueId;
	}

}