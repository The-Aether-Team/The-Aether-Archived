package com.gildedgames.the_aether.client.gui;

import com.gildedgames.the_aether.Aether;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.inventory.ContainerIncubator;
import com.gildedgames.the_aether.tileentity.TileEntityIncubator;

public class GuiIncubator extends GuiContainer {

	private TileEntityIncubator incubatorInventory;

	private static final ResourceLocation TEXTURE_INCUBATOR = Aether.locate("textures/gui/incubator.png");

	public GuiIncubator(EntityPlayer player, InventoryPlayer inventoryplayer, TileEntityIncubator tileentityIncubator) {
		super(new ContainerIncubator(player, inventoryplayer, tileentityIncubator));

		this.incubatorInventory = (TileEntityIncubator) tileentityIncubator;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String incubatorName = this.incubatorInventory.getInventoryName();

		this.fontRendererObj.drawString(incubatorName, this.xSize / 2 - this.fontRendererObj.getStringWidth(incubatorName) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int ia, int ib) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.renderEngine.bindTexture(TEXTURE_INCUBATOR);

		int j = (this.width - this.xSize) / 2;
		int k = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);

		if (this.incubatorInventory.isIncubating()) {
			int l = this.incubatorInventory.getPowerTimeRemainingScaled(12);

			this.drawTexturedModalRect(j + 74, (k + 47) - l, 176, 12 - l, 14, l + 2);
		}

		int i1 = this.incubatorInventory.getProgressScaled(54);

		this.drawTexturedModalRect(j + 103, k + 70 - i1, 179, 70 - i1, 10, i1);
	}

}