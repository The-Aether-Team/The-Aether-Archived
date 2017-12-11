package com.legacy.aether.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.gui.button.GuiLoreButton;
import com.legacy.aether.containers.ContainerLore;
import com.legacy.aether.registry.AetherLore;

public class GuiLore extends GuiContainer
{

    private static final ResourceLocation TEXTURE_LORE = new ResourceLocation("aether_legacy", "textures/gui/lore.png");

    private static final ResourceLocation TEXTURE_LORE_BOOK = new ResourceLocation("aether_legacy", "textures/gui/lore_book.png");

	private String stringToLoad;

	private ItemStack currentItem;

	private GuiButton previousPage, nextPage;

	private int pageNumber;

    public GuiLore(InventoryPlayer inventoryplayer)
    {
        super(new ContainerLore(inventoryplayer));

        this.xSize = 256; this.ySize = 195;
    }

    public void initGui()
    {
    	super.initGui();

    	this.previousPage = new GuiLoreButton(19, this.width / 2 - 110, this.height / 2 + 72, 20, 20, "<");
    	this.nextPage = new GuiLoreButton(20, this.width / 2 + 90, this.height / 2 + 72, 20, 20, ">");

    	this.addButton(this.previousPage);
    	this.addButton(this.nextPage);
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (this.previousPage == button)
    	{
    		--this.pageNumber;
    	}
    	else if (this.nextPage == button)
    	{
    		++this.pageNumber;
    	}
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	this.fontRenderer.drawString("Prev.", 16, 160, 4210752);
    	this.fontRenderer.drawString("Next", 218, 160, 4210752);

        this.fontRenderer.drawString("Book", 32, -5, 4210752);
        this.fontRenderer.drawString("of Lore:", 24, 4, 4210752);

        this.fontRenderer.drawString("Item :", 75, 0, 4210752);

        ItemStack searchedStack = ((ContainerLore)this.inventorySlots).loreSlot.getStackInSlot(0);

        if (!searchedStack.isEmpty())
        {
        	if (this.currentItem == null || (searchedStack.getItem() != this.currentItem.getItem() || searchedStack.getMetadata() != this.currentItem.getMetadata()))
        	{
        		this.pageNumber = 0;
        		this.stringToLoad = AetherLore.getLoreEntry(searchedStack);
            	this.currentItem = searchedStack;
        	}

        	int nameSize = 0;

        	for (String name : this.fontRenderer.listFormattedStringToWidth(searchedStack.getItem().getItemStackDisplayName(searchedStack), 109))
        	{
        		this.drawCenteredString(this.fontRenderer, searchedStack.getRarity().rarityColor.toString() + name, 71, 18 + (10 * nameSize), 4210752);

        		++nameSize;
        	}

			int size = 0;

			for (String lore : this.fontRenderer.listFormattedStringToWidth(this.stringToLoad, 109))
			{
				if (size >= 15 * this.pageNumber && size + (nameSize - 1) <= 15 * (this.pageNumber + 1))
				{
					int actualSize = this.pageNumber >= 1 ? size - ((15 - (nameSize - 1)) * this.pageNumber) : size + (nameSize - 1);
	
					this.fontRenderer.drawString(lore, (((actualSize >= 6 ? 184 : 71)) - this.fontRenderer.getStringWidth(lore) / 2), (actualSize >= 6 ? -68 : 28) + (10 * actualSize), 4210752);
				}

				size++;
			}

			this.previousPage.enabled = this.pageNumber != 0;
			this.nextPage.enabled = size > (15 * (this.pageNumber + 1));
        }
        else
        {
        	this.pageNumber = 0;
        	this.stringToLoad = "";
        	this.currentItem = null;
        }
    }

    @Override
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
    }

}