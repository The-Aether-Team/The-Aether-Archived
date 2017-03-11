package com.legacy.aether.client.gui.menu.hook;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class GuiWorldSelectionHook extends GuiWorldSelection
{

	public GuiWorldSelectionHook(GuiScreen screenIn)
	{
		super(screenIn);
	}

	@Override
    public void initGui()
    {
    	super.initGui();

    	ObfuscationReflectionHelper.setPrivateValue(GuiWorldSelection.class, this, new GuiListWorldSelectionHook(this), "selectionList", "field_184866_u");
    }
}