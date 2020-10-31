package com.gildedgames.the_aether.client.gui.button;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiGloveSizeButton extends GuiButton
{
    public GuiGloveSizeButton(int xPos, int yPos)
    {
        super(202, xPos, yPos, 150, 20, I18n.format("gui.button.glove_size"));
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(mc.player);

        if (player.gloveSize)
        {
            this.displayString = I18n.format("gui.button.glove_size") + " " + I18n.format("glove_size.option.hat");
        }
        else
        {
            this.displayString = I18n.format("gui.button.glove_size") + " " + I18n.format("glove_size.option.skin");
        }

        super.drawButton(mc, mouseX, mouseY, partialTicks);
    }
}