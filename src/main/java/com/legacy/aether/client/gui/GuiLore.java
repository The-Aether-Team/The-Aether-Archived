package com.legacy.aether.client.gui;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.server.containers.ContainerLore;
import com.legacy.aether.server.registry.lore.AetherLoreEntry;
import com.legacy.aether.server.registry.lore.EndLoreEntry;
import com.legacy.aether.server.registry.lore.NetherLoreEntry;
import com.legacy.aether.server.registry.lore.OverworldLoreEntry;
import com.legacy.aether.server.registry.objects.EntryInformation;
import com.legacy.aether.server.registry.objects.LoreEntry;

public class GuiLore extends GuiContainer
{

    private static final ResourceLocation TEXTURE_LORE = new ResourceLocation("aether_legacy", "textures/gui/lore.png");

	private HashMap<String, LoreEntry> lore_pages = new HashMap<String, LoreEntry>();

    public GuiLore(InventoryPlayer inventoryplayer)
    {
        super(new ContainerLore(inventoryplayer));

        this.xSize = 256; this.ySize = 195;
    }

    public void initGui()
    {
    	super.initGui();
		registerSection("Overworld Lore", new OverworldLoreEntry().initEntries()); //199 Complete
		registerSection("Nether Lore", new NetherLoreEntry().initEntries()); // 22 Complete
		registerSection("End Lore", new EndLoreEntry().initEntries()); //11 Complete
		registerSection("Aether Lore", new AetherLoreEntry().initEntries()); //157 Complete
    }

	public void registerSection(String name, LoreEntry lore)
	{
		lore_pages.put(name, lore);
	}

	public LoreEntry getSection(String name)
	{
		return lore_pages.get(name);
	}

    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString("Book of Lore:", 37, 18, 4210752);
        this.fontRendererObj.drawString("Add Object", 37, 28, 4210752);

        this.fontRendererObj.drawString("Item : ", 46, 72, 4210752);
        ItemStack searchedStack = ((ContainerLore)this.inventorySlots).loreSlot.getStackInSlot(0);

        if (searchedStack != null)
        {
        	Iterator<LoreEntry> entries = this.lore_pages.values().iterator();
        	
        	while(entries.hasNext())
        	{
        		Iterator<EntryInformation> entry_contents = entries.next().EntryInformation().iterator();
        		
        		while (entry_contents.hasNext())
        		{
        			EntryInformation information = entry_contents.next();
    				Item loreItem = information.base.getItem();
        			
        			if (loreItem != null && (information.base.getItemDamage() == searchedStack.getItemDamage() || information.base.isItemStackDamageable()) && loreItem == searchedStack.getItem())
        			{
        				GlStateManager.pushMatrix();
        				GlStateManager.scale(0.8F, 1.2F, 0.0F);

                        this.fontRendererObj.drawString(information.s, 164, 14, 4210752);
                        this.fontRendererObj.drawString(information.s1, 164, 28, 4210752);
                        this.fontRendererObj.drawString(information.s2, 164, 38, 4210752);
                        this.fontRendererObj.drawString(information.s3, 164, 48, 4210752);
                        this.fontRendererObj.drawString(information.s4, 164, 58, 4210752);
                        this.fontRendererObj.drawString(information.s5, 164, 68, 4210752);
                        this.fontRendererObj.drawString(information.s6, 164, 78, 4210752);

        				GlStateManager.popMatrix();
        			}
        		}
        	}
        }
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i1, int i2)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(TEXTURE_LORE);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}