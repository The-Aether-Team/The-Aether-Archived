package com.legacy.aether.client.gui.menu.hook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiListWorldSelection;
import net.minecraft.client.gui.GuiListWorldSelectionEntry;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;

public class GuiListWorldSelectionEntryHook extends GuiListWorldSelectionEntry
{

	private Minecraft client;

	private WorldSummary worldSummary;

	private GuiWorldSelection worldSelScreen;

	public GuiListWorldSelectionEntryHook(GuiListWorldSelection listWorldSelIn, WorldSummary worldSummary, ISaveFormat p_i46591_3_)
	{
		super(listWorldSelIn, worldSummary, p_i46591_3_);

		this.worldSummary = worldSummary;
		this.client = Minecraft.getMinecraft();
		this.worldSelScreen = listWorldSelIn.getGuiWorldSelection();
	}

	@Override
    public void joinWorld()
    {
        if (this.worldSummary.askToOpenWorld())
        {
            this.client.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback()
            {
                public void confirmClicked(boolean result, int id)
                {
                    if (result)
                    {
                    	GuiListWorldSelectionEntryHook.this.loadWorld();
                    }
                    else
                    {
                    	GuiListWorldSelectionEntryHook.this.client.displayGuiScreen(GuiListWorldSelectionEntryHook.this.worldSelScreen);
                    }
                }
            }, I18n.format("selectWorld.versionQuestion", new Object[0]), I18n.format("selectWorld.versionWarning", new Object[] {this.worldSummary.getVersionName()}), I18n.format("selectWorld.versionJoinButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), 0));
        }
        else
        {
            this.loadWorld();
        }
    }

    private void loadWorld()
    {
        this.client.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));

        if (this.client.getSaveLoader().canLoadWorld(this.worldSummary.getFileName()))
        {
        	if (this.client.theWorld != null)
        	{
                this.client.theWorld.sendQuittingDisconnectingPacket();
        	}

            net.minecraftforge.fml.client.FMLClientHandler.instance().tryLoadExistingWorld(worldSelScreen, this.worldSummary);
        }
    }

}