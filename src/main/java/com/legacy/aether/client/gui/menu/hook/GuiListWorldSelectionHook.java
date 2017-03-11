package com.legacy.aether.client.gui.menu.hook;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiListWorldSelectionEntry;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;

public class GuiListWorldSelectionHook extends GuiListWorldSelection
{

    private final List<GuiListWorldSelectionEntryHook> entries = Lists.<GuiListWorldSelectionEntryHook>newArrayList();

	public GuiListWorldSelectionHook(GuiWorldSelectionHook screen) 
	{
		super(screen, Minecraft.getMinecraft(), screen.width, screen.height, 32, screen.height - 64, 36);
		this.refreshList();
	}

	@Override
    public void refreshList()
    {
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        List<WorldSummary> list;

        try
        {
            list = isaveformat.getSaveList();
        }
        catch (AnvilConverterException anvilconverterexception)
        {
            this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
            return;
        }

        Collections.sort(list);

        for (WorldSummary worldsummary : list)
        {
        	if (this.entries != null)
            this.entries.add(new GuiListWorldSelectionEntryHook(this, worldsummary, this.mc.getSaveLoader()));
        }
    }

	@Override
    public GuiListWorldSelectionEntry getListEntry(int index)
    {
        return (GuiListWorldSelectionEntry)this.entries.get(index);
    }

	@Override
    protected int getSize()
    {
        return this.entries.size();
    }

}