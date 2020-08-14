package com.gildedgames.the_aether.client.gui;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.inventory.ContainerEnchanter;
import com.gildedgames.the_aether.tileentity.TileEntityEnchanter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEnchanter extends GuiContainer {

	private static final ResourceLocation TEXTURE = Aether.locate("textures/gui/altar.png");

	private TileEntityEnchanter enchanter;

	public GuiEnchanter(InventoryPlayer inventory, TileEntityEnchanter tileEntity) {
		super(new ContainerEnchanter(inventory, tileEntity));
		this.enchanter = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String enchanterName = AetherConfig.legacyAltarName() ?
				I18n.format("container.aether_legacy.enchanter")
				: I18n.format("container.aether_legacy.altar");

		this.fontRendererObj.drawString(enchanterName, this.xSize / 2 - this.fontRendererObj.getStringWidth(enchanterName) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.enchanter.isEnchanting()) {
			i1 = this.enchanter.getEnchantmentTimeRemaining(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = this.enchanter.getEnchantmentProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}

}