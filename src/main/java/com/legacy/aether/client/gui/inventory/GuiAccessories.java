package com.legacy.aether.client.gui.inventory;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.gui.button.GuiButtonPerks;
import com.legacy.aether.server.containers.ContainerAccessories;
import com.legacy.aether.server.player.PlayerAether;
import com.legacy.aether.server.player.perks.AetherRankings;
import com.legacy.aether.universal.AetherCompatibility;

public class GuiAccessories extends GuiInventory
{

	private static final ResourceLocation ACCESSORIES = new ResourceLocation("aether_legacy", "textures/gui/inventory/accessories.png");

	private static final ResourceLocation ACCESSORIES_SHIFTED = new ResourceLocation("aether_legacy", "textures/gui/inventory/accessories_shifted.png");

	PlayerAether playerAether;

	public GuiAccessories(PlayerAether player)
	{
		super(player.thePlayer);
		this.inventorySlots = new ContainerAccessories(player.accessories, player.thePlayer);
		this.playerAether = player;
		this.allowUserInput = true;
	}

	@Override
	public void initGui()
	{
		super.initGui();

		if (AetherRankings.isRankedPlayer(this.playerAether.thePlayer.getUniqueID()) || this.playerAether.isDonator())
		{
			this.buttonList.add(new GuiButtonPerks(this.width / 2 - 108, this.height / 2 - 83));
		}
	}

	@Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
    	super.setWorldAndResolution(mc, width, height);

		for (int size = 0; size < this.buttonList.size(); ++size)
		{
			GuiButton button = this.buttonList.get(size);
			int id = button.id;

			if (id == 13211)
			{
				this.setButtonPosition(button, this.width / 2 + 65, this.height / 2 - 23);
			}

			if (Loader.isModLoaded("Baubles"))
			{
				if (id == 55)
				{
					button.xPosition = (new ScaledResolution(mc).getScaledWidth() / 2) - 39;
				}
			}
		}
    }

	private void setButtonPosition(GuiButton button, int xPosition, int yPosition)
	{
		button.xPosition = xPosition;
		button.yPosition = yPosition;
	}

	@Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (button.id == 24)
    	{
    		this.mc.displayGuiScreen(new GuiAetherPerks());
    	}
    }

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor3d(1.0D, 1.0D, 1.0D);

		this.mc.renderEngine.bindTexture(AetherCompatibility.visualModsLoaded() ? ACCESSORIES_SHIFTED : ACCESSORIES);

		this.drawTexturedModalRect(this.width / 2 - 88, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		drawEntityOnScreen(this.guiLeft + 35, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - (float)mouseX, (float)(this.guiTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
	}

}