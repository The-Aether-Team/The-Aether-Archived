package com.gildedgames.the_aether.client.gui.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class AetherMainMenuButton extends GuiButton
{
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("aether_legacy", "textures/gui/menu/buttons.png");
    private int textOffset;

    public AetherMainMenuButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, 39, buttonText);
    }

    public AetherMainMenuButton(int buttonId, int x, int y, int widthIn, int heightIn, int textOffset, String buttonText)
    {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.textOffset = textOffset;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 7851212;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.x + (this.width / 2) - this.textOffset, this.y + (this.height - 8) / 2, j);
        }
    }
}
