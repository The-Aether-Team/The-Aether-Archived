package com.legacy.aether.client.gui.inventory;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.gui.button.GuiAccessoryButton;
import com.legacy.aether.client.gui.button.GuiButtonPerks;
import com.legacy.aether.containers.ContainerAccessories;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketOpenContainer;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.perks.AetherRankings;

public class GuiAccessories extends GuiContainer
{

	private static final ResourceLocation ACCESSORIES = new ResourceLocation("aether_legacy", "textures/gui/inventory/accessories.png");

	PlayerAether playerAether;

	public GuiAccessories(PlayerAether player)
	{
		super(new ContainerAccessories(player.accessories, player.thePlayer));

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

		this.buttonList.add(new GuiAccessoryButton(this.guiLeft + 8, this.guiTop + 65));
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

		if (button.id == 18067)
		{
			this.mc.displayGuiScreen(new GuiInventory(mc.player));
			AetherNetworkingManager.sendToServer(new PacketOpenContainer(-1));
		}
    }

	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 115, 8, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor3d(1.0D, 1.0D, 1.0D);

		this.mc.renderEngine.bindTexture(ACCESSORIES);

		this.drawTexturedModalRect(this.width / 2 - 88, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		GuiInventory.drawEntityOnScreen(this.guiLeft + 35, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - (float)mouseX, (float)(this.guiTop + 75 - 50) - (float)mouseY, this.mc.player);
	}

}