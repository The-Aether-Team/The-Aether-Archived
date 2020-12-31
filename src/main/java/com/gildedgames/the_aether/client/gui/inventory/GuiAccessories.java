package com.gildedgames.the_aether.client.gui.inventory;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.AetherKeybinds;
import com.gildedgames.the_aether.client.gui.button.GuiAccessoryButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.inventory.ContainerAccessories;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketOpenContainer;
import com.gildedgames.the_aether.player.PlayerAether;

public class GuiAccessories extends GuiContainer {

	private static final ResourceLocation ACCESSORIES = Aether.locate("textures/gui/inventory/accessories.png");

	private PlayerAether playerAether;

	public GuiAccessories(PlayerAether player) {
		super(new ContainerAccessories(player.getAccessoryInventory(), player.getEntity()));

		this.playerAether = player;
		this.allowUserInput = true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui() {
		super.initGui();

		/*
		if (AetherRankings.isRankedPlayer(this.playerAether.getEntity().getUniqueID()) || this.playerAether.isDonator()) {
			this.buttonList.add(new GuiButtonPerks(this.width / 2 - 108, this.height / 2 - 83));
		}
		 */

		//this.buttonList.add(new GuiAccessoryButton(this.guiLeft + 8, this.guiTop + 65));
	}

	@Override
	public void setWorldAndResolution(Minecraft mc, int width, int height) {
		super.setWorldAndResolution(mc, width, height);

		for (int size = 0; size < this.buttonList.size(); ++size) {
			GuiButton button = (GuiButton) this.buttonList.get(size);
			int id = button.id;

			if (id == 13211) {
				this.setButtonPosition(button, this.width / 2 + 65, this.height / 2 - 23);
			}
		}
	}

	private void setButtonPosition(GuiButton button, int xPosition, int yPosition) {
		button.xPosition = xPosition;
		button.yPosition = yPosition;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 24) {
			this.mc.displayGuiScreen(new GuiAetherPerks());
		}

		if (button.id == 18067) {
			this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
			AetherNetwork.sendToServer(new PacketOpenContainer(-1));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 115, 8, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		;
		GL11.glColor3d(1.0D, 1.0D, 1.0D);

		this.mc.renderEngine.bindTexture(ACCESSORIES);

		this.drawTexturedModalRect(this.width / 2 - 88, this.height / 2 - 166 / 2, 0, 0, 176, 166);

		GuiInventory.func_147046_a(this.guiLeft + 35, this.guiTop + 75, 30, (float) (this.guiLeft + 51) - (float) mouseX, (float) (this.guiTop + 75 - 50) - (float) mouseY, this.mc.thePlayer);
	}

	@Override
	public void handleKeyboardInput()
	{
		super.handleKeyboardInput();

		int keyPressed = Keyboard.getEventKey();

		if (keyPressed == AetherKeybinds.keyBindingAccessories.getKeyCode() && Keyboard.getEventKeyState())
		{
			Minecraft.getMinecraft().thePlayer.closeScreen();
		}
	}
}