package com.gildedgames.the_aether.client.gui.dialogue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class DialogueOption extends Gui {

	private int dialogueId;

	private String dialogueText;

	private int xPosition, yPosition;

	private int height, width;

	private Minecraft mc = Minecraft.getMinecraft();

	public DialogueOption(String dialogueText) {
		this.dialogueText = "[" + dialogueText + "]";
		this.height = 12;
		this.width = this.mc.fontRenderer.getStringWidth(this.dialogueText) + 2;
	}

	public void renderDialogue(int mouseX, int mouseY) {
		this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0x66000000, 0x66000000);
		this.drawString(this.mc.fontRenderer, this.isMouseOver(mouseX, mouseY) ? EnumChatFormatting.YELLOW.toString() + this.getDialogueText() : this.getDialogueText(), this.xPosition + 2, this.yPosition + 2, 0xffffff);
	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
	}

	public void playPressSound(SoundHandler soundHandlerIn) {
		soundHandlerIn.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}

	public void setDialogueText(String dialogueText) {
		this.dialogueText = "[" + dialogueText + "]";
		this.width = this.mc.fontRenderer.getStringWidth(this.dialogueText) + 2;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public void setDialogueId(int id) {
		this.dialogueId = id;
	}

	public int getDialogueId() {
		return this.dialogueId;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public String getDialogueText() {
		return this.dialogueText;
	}

}