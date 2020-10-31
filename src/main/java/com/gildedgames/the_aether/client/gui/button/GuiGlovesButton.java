package com.gildedgames.the_aether.client.gui.button;

import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.api.AetherAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiGlovesButton extends GuiButton
{
    public GuiGlovesButton(int xPos, int yPos)
    {
        super(202, xPos, yPos, 150, 20, I18n.format("gui.button.gloves"));
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(mc.player);

        if (player.shouldRenderGloves)
        {
            this.displayString = I18n.format("gui.button.gloves") + " " + I18n.format("options.on");
        }
        else
        {
            this.displayString = I18n.format("gui.button.gloves") + " " + I18n.format("options.off");
        }

        super.drawButton(mc, mouseX, mouseY, partialTicks);
    }
}