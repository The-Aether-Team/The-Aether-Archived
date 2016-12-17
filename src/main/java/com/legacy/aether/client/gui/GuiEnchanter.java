package com.legacy.aether.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.server.containers.ContainerEnchanter;
import com.legacy.aether.server.tile_entities.TileEntityEnchanter;

@SideOnly(Side.CLIENT)
public class GuiEnchanter extends GuiContainer
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/gui/enchanter.png");

	private TileEntityEnchanter enchanter;

	public GuiEnchanter(InventoryPlayer inventory, TileEntityEnchanter tileEntity)
	{
		super(new ContainerEnchanter(inventory, tileEntity));
		this.enchanter = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String enchanterName = "Enchanter";

		this.fontRendererObj.drawString(enchanterName, this.xSize / 2 - this.fontRendererObj.getStringWidth(enchanterName) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.enchanter.isBurning())
		{
			i1 = this.enchanter.getEnchantmentTimeRemaining(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = this.enchanter.getEnchantmentProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}

}