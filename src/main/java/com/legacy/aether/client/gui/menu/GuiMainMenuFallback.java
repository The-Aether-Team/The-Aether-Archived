package com.legacy.aether.client.gui.menu;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;

import com.legacy.aether.client.gui.button.GuiDescButton;
import com.legacy.aether.client.gui.menu.hook.GuiMultiplayerHook;
import com.legacy.aether.client.gui.menu.hook.GuiWorldSelectionHook;

public class GuiMainMenuFallback extends GuiMainMenu
{

    public void initGui()
    {
    	super.initGui();
		this.buttonList.add(new GuiDescButton(7, width - 24, 4, 20, 20, "W", "Toggle World"));
		this.buttonList.add(new GuiDescButton(8, width - 48, 4, 20, 20, "T", "Toggle Theme"));
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
    	super.actionPerformed(button);

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new GuiWorldSelectionHook(this));
        }

        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayerHook(this));
        }

        if (button.id == 7)
        {
        	this.mc.displayGuiScreen(new GuiMainMenu());
        }

        if (button.id == 8)
        {
        	this.mc.displayGuiScreen(new GuiAetherMainMenuFallback());
        }
    }

}