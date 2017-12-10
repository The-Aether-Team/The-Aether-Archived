package com.legacy.aether.client.gui.dialogue.server;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.legacy.aether.client.gui.dialogue.DialogueOption;
import com.legacy.aether.client.gui.dialogue.GuiDialogue;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketDialogueClicked;

public class GuiServerDialogue extends GuiDialogue
{

	private String dialogueName;

	public GuiServerDialogue(String dialogueName, String dialogue, ArrayList<String> dialogueText)
	{
		super(dialogue);

		this.dialogueName = dialogueName;

		ArrayList<DialogueOption> dialogueOptions = Lists.newArrayList();

		for (String dialogueForOption : dialogueText)
		{
			dialogueOptions.add(new DialogueOption(dialogueForOption));
		}

        this.addDialogueOptions(dialogueOptions.toArray(new DialogueOption[] {}));
	}

	@Override
	public void dialogueClicked(DialogueOption dialogue)
	{
		AetherNetworkingManager.sendToServer(new PacketDialogueClicked(this.dialogueName, dialogue.getDialogueId()));
		this.dialogueTreeCompleted();
	}

}