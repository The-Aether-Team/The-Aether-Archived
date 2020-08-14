package com.gildedgames.the_aether.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiDescButton extends GuiButton {

	public String descText;

	public GuiDescButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String descText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.descText = descText;
	}

	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		super.drawButton(mc, mouseX, mouseY);

		FontRenderer fontrenderer = mc.fontRenderer;

		if (this.visible && this.field_146123_n) {
			if (this.displayString == "Q") {
				this.drawCenteredString(fontrenderer, this.descText, this.xPosition + (this.width + 50) / 2, this.yPosition + (this.height + 24) / 2, 0xfffffF);
			} else if (this.displayString == "W") {
				this.drawCenteredString(fontrenderer, this.descText, this.xPosition + (this.width - 50) / 2, this.yPosition + (this.height + 24) / 2, 0xfffffF);
			} else {
				this.drawCenteredString(fontrenderer, this.descText, this.xPosition + this.width / 2, this.yPosition + (this.height + 24) / 2, 0xfffffF);
			}
		}
	}

}