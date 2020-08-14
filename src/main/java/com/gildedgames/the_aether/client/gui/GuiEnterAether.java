package com.gildedgames.the_aether.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiEnterAether extends GuiScreen
{
    public boolean aether;

    public GuiEnterAether(boolean dimension)
    {
        aether = dimension;
    }

    public void initGui()
    {
        this.buttonList.clear();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawBackground(0);

        if (aether)
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("gui.loading.enteraether"), this.width / 2, this.height / 2 - 45, 16777215);
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 25, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRendererObj, I18n.format("gui.loading.exitaether"), this.width / 2, this.height / 2 - 45, 16777215);
            this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 25, 16777215);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
