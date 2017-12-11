package com.legacy.aether.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiDescButton extends GuiButton
{

	public String descText;

	public GuiDescButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String descText) 
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.descText = descText;
	}

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
    	super.drawButton(mc, mouseX, mouseY, partialTicks);

        FontRenderer fontrenderer = mc.fontRenderer;

    	if (this.visible && this.hovered)
    	{
    		if (this.displayString == "Q")
    		{
        		this.drawCenteredString(fontrenderer, this.descText, this.x + (this.width + 50) / 2, this.y + (this.height + 24) / 2, 0xfffffF);
    		}
    		else if (this.displayString == "W")
    		{
        		this.drawCenteredString(fontrenderer, this.descText, this.x + (this.width - 50) / 2, this.y + (this.height + 24) / 2, 0xfffffF);
    		}
    		else
    		{
        		this.drawCenteredString(fontrenderer, this.descText, this.x + this.width / 2, this.y + (this.height + 24) / 2, 0xfffffF);
    		}
    	}
    }

}