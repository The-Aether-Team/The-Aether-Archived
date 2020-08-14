package com.gildedgames.the_aether.client.gui.dialogue.server;

import java.util.ArrayList;

import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.PacketDialogueClicked;
import com.google.common.collect.Lists;
import com.gildedgames.the_aether.client.gui.dialogue.DialogueOption;
import com.gildedgames.the_aether.client.gui.dialogue.GuiDialogue;

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