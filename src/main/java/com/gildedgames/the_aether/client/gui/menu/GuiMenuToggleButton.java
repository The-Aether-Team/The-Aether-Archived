package com.gildedgames.the_aether.client.gui.menu;

import com.gildedgames.the_aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiMenuToggleButton extends GuiButton
{
    public GuiMenuToggleButton(int xPos, int yPos)
    {
        super(50, xPos, yPos, 20, 20, "T");
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        super.drawButton(mc, mouseX, mouseY);

        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            int j = 16777215;

            if (this.field_146123_n)
            {
                if (AetherConfig.config.get("Misc", "Enables the Aether Menu", false).getBoolean())
                {
                    this.drawCenteredString(fontrenderer, I18n.format("gui.aether_menu.normal_theme"), (this.xPosition + this.width) - 34, (this.height / 2) + 18, j);
                }
                else
                {
                    this.drawCenteredString(fontrenderer, I18n.format("gui.aether_menu.aether_theme"), (this.xPosition + this.width) - 34, (this.height / 2) + 18, j);
                }
            }
        }
    }

    public GuiMenuToggleButton setPosition(int x, int y)
    {
        this.xPosition = x;
        this.yPosition = y;

        return this;
    }

    public void mouseReleased(int mouseX, int mouseY)
    {
        AetherConfig.config.get("Misc", "Enables the Aether Menu", false).set(!AetherConfig.config.get("Misc", "Enables the Aether Menu", false).getBoolean());
        AetherConfig.config.save();

        if (AetherConfig.config.get("Misc", "Enables the Aether Menu", false).getBoolean())
        {
            Minecraft.getMinecraft().displayGuiScreen(new AetherMainMenu());
        }
    }
}
