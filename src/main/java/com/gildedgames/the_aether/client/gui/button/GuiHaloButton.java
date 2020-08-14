package com.gildedgames.the_aether.client.gui.button;

import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiHaloButton extends GuiButton
{
    public GuiHaloButton(int xPos, int yPos)
    {
        super(201, xPos, yPos, 150, 20, I18n.format("gui.button.halo"));
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        PlayerAether player = PlayerAether.get(mc.thePlayer);

        if (player.shouldRenderHalo)
        {
            this.displayString = I18n.format("gui.button.halo") + " " + I18n.format("options.on");
        }
        else
        {
            this.displayString = I18n.format("gui.button.halo") + " " + I18n.format("options.off");
        }

        super.drawButton(mc, mouseX, mouseY);
    }
}