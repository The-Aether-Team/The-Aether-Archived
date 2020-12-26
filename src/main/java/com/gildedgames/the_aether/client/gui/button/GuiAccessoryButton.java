package com.gildedgames.the_aether.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiAccessoryButton extends GuiButton
{

    protected static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("aether_legacy", "textures/gui/inventory/button/cloud.png");

    protected static final ResourceLocation BUTTON_HOVERED_TEXTURE = new ResourceLocation("aether_legacy", "textures/gui/inventory/button/cloud_hover.png");

	public GuiAccessoryButton(int x, int y) 
	{
		super(18067, x, y, 12, 12, "");
	}

	public GuiAccessoryButton setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;

		return this;
	}

	@Override  
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

    	if (this.visible)
    	{
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            int i = this.getHoverState(this.hovered);
            mc.getTextureManager().bindTexture(i == 2 ? BUTTON_HOVERED_TEXTURE : BUTTON_TEXTURE);
            GlStateManager.enableBlend();

            drawModalRectWithCustomSizedTexture(this.x - 1, this.y, 0, 0, 14, 14, 14, 14);

			GlStateManager.disableBlend();
            GlStateManager.popMatrix();
    	}
    }

}