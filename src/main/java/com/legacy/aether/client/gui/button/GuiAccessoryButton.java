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

    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("aether_legacy", "textures/gui/inventory/icon.png");

	public GuiAccessoryButton(ScaledResolution resolution) 
	{
		super(18067, (resolution.getScaledWidth() / 2) - 65, (resolution.getScaledHeight() / 2) - 20, 14, 14, "");

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
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();

            if (i == 2)
            {
            	GlStateManager.color(0.5F, 0.5F, 0.5F);
            }

            drawModalRectWithCustomSizedTexture(-5 + this.xPosition + this.width / 2, this.yPosition, 0, 0, 14, 14, 14, 14);

            GlStateManager.popMatrix();
    	}
    }

}