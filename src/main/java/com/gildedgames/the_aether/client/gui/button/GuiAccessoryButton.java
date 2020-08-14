package com.gildedgames.the_aether.client.gui.button;

import com.gildedgames.the_aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiAccessoryButton extends GuiButton {

	protected static final ResourceLocation BUTTON_TEXTURE = Aether.locate("textures/gui/inventory/button/cloud.png");

	protected static final ResourceLocation BUTTON_HOVERED_TEXTURE = Aether.locate("textures/gui/inventory/button/cloud_hover.png");

	public GuiAccessoryButton(int x, int y) {
		super(18067, x, y, 12, 12, "");
	}

	public GuiAccessoryButton setPosition(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;

		return this;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

		if (this.visible) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPushMatrix();
			int i = this.getHoverState(this.field_146123_n);
			mc.getTextureManager().bindTexture(i == 2 ? BUTTON_HOVERED_TEXTURE : BUTTON_TEXTURE);
			GL11.glEnable(GL11.GL_BLEND);

			func_146110_a(this.xPosition - 1, this.yPosition, 0, 0, 14, 14, 14, 14);

			GL11.glPopMatrix();
		}
	}

}