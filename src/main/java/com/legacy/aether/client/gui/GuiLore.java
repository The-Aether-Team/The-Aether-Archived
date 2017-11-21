package com.legacy.aether.client.gui;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.common.containers.ContainerLore;
import com.legacy.aether.common.registry.AetherLore;
import com.legacy.aether.common.registry.lore.AetherLoreEntry;
import com.legacy.aether.common.registry.lore.EndLoreEntry;
import com.legacy.aether.common.registry.lore.NetherLoreEntry;
import com.legacy.aether.common.registry.lore.OverworldLoreEntry;
import com.legacy.aether.common.registry.objects.EntryInformation;
import com.legacy.aether.common.registry.objects.LoreEntry;

public class GuiLore extends GuiContainer
{

    private static final ResourceLocation TEXTURE_LORE = new ResourceLocation("aether_legacy", "textures/gui/lore.png");

    private static final ResourceLocation TEXTURE_LORE_BOOK = new ResourceLocation("aether_legacy", "textures/gui/lore_book.png");

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
        this.fontRendererObj.drawString("Book", 32, -5, 4210752);
        this.fontRendererObj.drawString("of Lore:", 24, 4, 4210752);
        //this.fontRendererObj.drawString("Add Object", 70, 0, 4210752);

        this.fontRendererObj.drawString("Item :", 75, 0, 4210752);

        ItemStack searchedStack = ((ContainerLore)this.inventorySlots).loreSlot.getStackInSlot(0);

        if (searchedStack != null)
        {
			this.drawCenteredString(this.fontRendererObj, searchedStack.getRarity().rarityColor.toString() + searchedStack.getItem().getItemStackDisplayName(searchedStack), 71, 18, 4210752);

			int size = 0;

			for (String lore : this.fontRendererObj.listFormattedStringToWidth(AetherLore.getLoreEntry(searchedStack), 111))
			{
				this.fontRendererObj.drawString(lore, ((size >= 6 ? 184 : 71) - this.fontRendererObj.getStringWidth(lore) / 2), (size >= 6 ? -68 : 28) + (10 * size), 4210752);
				//this.drawCenteredString(this.fontRendererObj, lore, size >= 6 ? 182 : 71, (size >= 6 ? -70 : 28) + (10 * size), 4210752);

				size++;
			}

			//this.fontRendererObj.drawSplitString(str, x, y, wrapWidth, textColor);
        }
        /*ItemStack searchedStack = ((ContainerLore)this.inventorySlots).loreSlot.getStackInSlot(0);

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
        				//GlStateManager.pushMatrix();

        				this.drawCenteredString(this.fontRendererObj, information.base.getRarity().rarityColor.toString() + information.s, 71, 18, 4210752);

        				this.fontRendererObj.drawSplitString(information.s1 + " " + information.s2 + " " + information.s3 + " " + information.s4, 18, 30, 120, 4210752);
                        //this.fontRendererObj.drawStringWithShadow(information.base.getRarity().rarityColor.toString() + information.s, 18, 18, 4210752);
                        //this.fontRendererObj.drawString(information.s1, 134, 28, 4210752);
                        //this.fontRendererObj.drawString(information.s2, 134, 38, 4210752);
                        //this.fontRendererObj.drawString(information.s3, 134, 48, 4210752);
                        //this.fontRendererObj.drawString(information.s4, 134, 58, 4210752);
                        //this.fontRendererObj.drawString(information.s5, 134, 68, 4210752);
                        //this.fontRendererObj.drawString(information.s6, 134, 78, 4210752);

        				//GlStateManager.popMatrix();
        			}
        		}
        	}
       	}*/
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    protected void drawGuiContainerBackgroundLayer(float f, int i1, int i2)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;

        this.mc.renderEngine.bindTexture(TEXTURE_LORE);
        Gui.drawScaledCustomSizeModalRect(j, k - 4, 0, 0, 120, 120, this.xSize, this.ySize + 61, 120, 120);

        this.mc.renderEngine.bindTexture(TEXTURE_LORE_BOOK);
        Gui.drawModalRectWithCustomSizedTexture(j - 1, k - 20, 0, 0, this.xSize + 20, this.ySize - 60, 275, 315);
        Gui.drawModalRectWithCustomSizedTexture(j + 90, k - 5, 0, 225, this.xSize + 20, this.ySize - 177, 500, 500);
        //Gui.drawScaledCustomSizeModalRect(j - 1, k - 20, 200, 200, 100, 100, this.xSize + 20, this.ySize + 120, 100, 100);
    }
}