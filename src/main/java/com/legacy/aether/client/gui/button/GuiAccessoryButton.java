package com.legacy.aether.client.gui.button;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class GuiAccessoryButton extends GuiButton
{

    protected static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("aether_legacy", "textures/gui/inventory//button/cloud.png");

    protected static final ResourceLocation BUTTON_HOVERED_TEXTURE = new ResourceLocation("aether_legacy", "textures/gui/inventory/button/cloud_hover.png");

	public GuiAccessoryButton(ScaledResolution resolution) 
	{
		super(18067, (resolution.getScaledWidth() / 2) - 64, (resolution.getScaledHeight() / 2) - 19, 14, 14, "");

        Collection<PotionEffect> collection = Minecraft.getMinecraft().thePlayer.getActivePotionEffects();

        if (Minecraft.getMinecraft().currentScreen.getClass() == GuiInventory.class && !collection.isEmpty())
        {
        	this.xPosition = this.xPosition + 59;
        }
	}

	public GuiAccessoryButton(ScaledResolution resolution, int x, int y) 
	{
		super(18067, (resolution.getScaledWidth() / 2) - x, (resolution.getScaledHeight() / 2) - y, 14, 14, "");
	}

	@Override  
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

    	if (this.visible)
    	{
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            int i = this.getHoverState(this.hovered);
            mc.getTextureManager().bindTexture(i == 2 ? BUTTON_HOVERED_TEXTURE : BUTTON_TEXTURE);
            GlStateManager.enableBlend();

            drawModalRectWithCustomSizedTexture(-5 + this.xPosition + this.width / 2, this.yPosition, 0, 0, 14, 14, 14, 14);

            GlStateManager.popMatrix();
    	}
    }

}