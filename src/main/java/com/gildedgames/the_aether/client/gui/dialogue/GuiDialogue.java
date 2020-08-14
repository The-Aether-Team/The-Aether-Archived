package com.gildedgames.the_aether.client.gui.dialogue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;

public class GuiDialogue extends GuiScreen {

	private ArrayList<DialogueOption> dialogueOptions = new ArrayList<DialogueOption>();

	private String dialogue;

	public GuiDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	public GuiDialogue(String dialogue, DialogueOption... options) {
		this(dialogue);

		this.addDialogueOptions(options);
	}

	public void addDialogueWithOptions(String dialogue, DialogueOption... options) {
		this.dialogue = dialogue;

		this.dialogueOptions.clear();

		this.addDialogueOptions(options);
		this.positionDialogueOptions(this.getDialogueOptions());
	}

	public void initGui() {
		this.positionDialogueOptions(this.getDialogueOptions());
	}

	private void positionDialogueOptions(ArrayList<DialogueOption> options) {
		int lineNumber = 0;

		for (DialogueOption option : options) {
			option.setDialogueId(lineNumber);
			option.setXPosition((this.width / 2) - (option.getWidth() / 2));
			option.setYPosition((this.height / 2) + this.fontRendererObj.listFormattedStringToWidth(this.dialogue, 300).size() * 12 + 12 * lineNumber);

			lineNumber++;
		}
	}

	public void addDialogueOptions(DialogueOption... options) {
		for (DialogueOption option : options) {
			this.dialogueOptions.add(option);
		}
	}

	public void addDialogueMessage(String dialogueMessage) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(dialogueMessage));
	}

	public void dialogueTreeCompleted() {
		this.mc.displayGuiScreen(null);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		int optionWidth = 0;

		for (String theDialogue : ((List<String>) this.fontRendererObj.listFormattedStringToWidth(this.dialogue, 300))) {
			int stringWidth = this.fontRendererObj.getStringWidth(theDialogue);

			this.drawGradientRect(this.width / 2 - stringWidth / 2 - 2, this.height / 2 + optionWidth * 12 - 2, this.width / 2 + stringWidth / 2 + 2, this.height / 2 + optionWidth * 10 + 10, 0x66000000, 0x66000000);
			this.drawString(this.fontRendererObj, theDialogue, this.width / 2 - stringWidth / 2, this.height / 2 + optionWidth * 10, 0xffffff);
			++optionWidth;
		}

		for (DialogueOption dialogue : this.dialogueOptions) {
			dialogue.renderDialogue(mouseX, mouseY);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			for (DialogueOption dialogue : this.dialogueOptions) {
				if (dialogue.isMouseOver(mouseX, mouseY)) {
					dialogue.playPressSound(this.mc.getSoundHandler());

					try {
						this.dialogueClicked(dialogue);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	public void dialogueClicked(DialogueOption dialogue) throws IOException {

	}

	public ArrayList<DialogueOption> getDialogueOptions() {
		return this.dialogueOptions;
	}

	public String getDialogue() {
		return this.dialogue;
	}

}