package com.legacy.aether.client.gui.menu.hook;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.LanServerInfo;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class GuiMultiplayerHook extends GuiMultiplayer
{

    private boolean directConnect;

    private ServerSelectionList list;
 
	public GuiMultiplayerHook(GuiScreen parentScreen)
	{
		super(parentScreen);
	}

	@Override
    public void initGui()
    {
    	super.initGui();

    	this.list = ObfuscationReflectionHelper.getPrivateValue(GuiMultiplayer.class, this, "serverListSelector", "field_146803_h");
    }

	@Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
        	if (button.id == 4)
        	{
        		this.directConnect = true;
        	}
        }

        super.actionPerformed(button);
    }

	@Override
    public void connectToSelected()
    {
        GuiListExtended.IGuiListEntry guilistextended$iguilistentry = this.list.getSelected() < 0 ? null : this.list.getListEntry(this.list.getSelected());

        if (guilistextended$iguilistentry instanceof ServerListEntryNormal)
        {
            this.connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
        }
        else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected)
        {
            LanServerInfo lanserverinfo = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getServerData();
            this.connectToServer(new ServerData(lanserverinfo.getServerMotd(), lanserverinfo.getServerIpPort(), true));
        }
    }

    private void connectToServer(ServerData server)
    {
    	if (this.mc.theWorld != null)
    	{
    		this.mc.theWorld.sendQuittingDisconnectingPacket();
    	}

        net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(this, server);
    }

	@Override
    public void confirmClicked(boolean result, int id)
    {
    	if (this.directConnect)
    	{
    		this.directConnect = false;
    		
            if (result)
            {
            	ServerData selectedServer = ObfuscationReflectionHelper.getPrivateValue(GuiMultiplayer.class, this, "selectedServer", "field_146811_z");
                this.connectToServer(selectedServer);
            }
            else
            {
                this.mc.displayGuiScreen(this);
            }
    	}
    }
}