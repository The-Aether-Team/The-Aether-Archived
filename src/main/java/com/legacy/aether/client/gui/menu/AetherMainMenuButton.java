package com.legacy.aether.client.gui.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AetherMainMenuButton extends GuiButton
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("aether_legacy", "textures/gui/menu/buttons.png");
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
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            p_146112_1_.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.field_146123_n)
            {
                l = 7851212;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + (this.width / 2) - this.textOffset, this.yPosition + (this.height - 8) / 2, l);
        }
    }
}
