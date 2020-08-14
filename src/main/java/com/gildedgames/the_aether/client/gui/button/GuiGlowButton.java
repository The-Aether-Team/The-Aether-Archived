package com.gildedgames.the_aether.client.gui.button;

import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiGlowButton extends GuiButton
{
    public GuiGlowButton(int xPos, int yPos)
    {
        super(202, xPos, yPos, 150, 20, I18n.format("gui.button.glow"));
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        PlayerAether player = PlayerAether.get(mc.thePlayer);

        if (player.shouldRenderGlow)
        {
            this.displayString = I18n.format("gui.button.glow") + " " + I18n.format("options.on");
        }
        else
        {
            this.displayString = I18n.format("gui.button.glow") + " " + I18n.format("options.off");
        }

        super.drawButton(mc, mouseX, mouseY);
    }
}