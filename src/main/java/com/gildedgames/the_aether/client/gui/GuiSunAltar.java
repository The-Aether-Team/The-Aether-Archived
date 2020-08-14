package com.gildedgames.the_aether.client.gui;

import com.gildedgames.the_aether.Aether;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.gildedgames.the_aether.client.gui.button.GuiSunAltarSlider;

public class GuiSunAltar extends GuiScreen {

	private static final ResourceLocation TEXTURE = Aether.locate("textures/gui/sun_altar.png");

	private World world;

	public GuiSunAltar() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();

		this.world = this.mc.theWorld;

		this.buttonList.add(new GuiSunAltarSlider(this.world, this.width / 2 - 75, this.height / 2, "Select Time"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(TEXTURE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int xSize = 175;
		int ySize = 78;
		int j = (this.width - xSize) / 2;
		int k = (this.height - ySize) / 2;

		this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
		this.fontRendererObj.drawString("Sun Altar", (this.width - this.fontRendererObj.getStringWidth("Sun Altar")) / 2, k + 20, 0x404040);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void keyTyped(char p_73869_1_, int p_73869_2_)
	{
		if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
		{
			this.mc.thePlayer.closeScreen();
		}
	}
}