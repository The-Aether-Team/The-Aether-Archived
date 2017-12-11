package com.legacy.aether.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.containers.ContainerIncubator;
import com.legacy.aether.tile_entities.TileEntityIncubator;

public class GuiIncubator extends GuiContainer
{

	private TileEntityIncubator incubatorInventory;

	private static final ResourceLocation TEXTURE_INCUBATOR = new ResourceLocation("aether_legacy", "textures/gui/incubator.png");

	public GuiIncubator(EntityPlayer player, InventoryPlayer inventoryplayer, IInventory tileentityIncubator)
	{
		super(new ContainerIncubator(player, inventoryplayer, tileentityIncubator));
		this.incubatorInventory = (TileEntityIncubator) tileentityIncubator;
	}

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString("Incubator", 60, 6, 0x404040);

		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int ia, int ib)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(TEXTURE_INCUBATOR);
		int j = (this.width - this.xSize) / 2;
		int k = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
		if (this.incubatorInventory.isBurning())
		{
			int l = this.incubatorInventory.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(j + 74, (k + 47) - l, 176, 12 - l, 14, l + 2);
		}
		int i1 = this.incubatorInventory.getCookProgressScaled(54);
		this.drawTexturedModalRect(j + 103, k + 70 - i1, 179, 70 - i1, 10, i1);
	}

}