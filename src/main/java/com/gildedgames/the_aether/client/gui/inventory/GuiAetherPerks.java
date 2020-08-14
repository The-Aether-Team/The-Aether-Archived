package com.gildedgames.the_aether.client.gui.inventory;

import java.io.IOException;

import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.entities.util.AetherMoaTypes;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.PacketPerkChanged;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.player.perks.util.DonatorMoaSkin;
import com.gildedgames.the_aether.player.perks.util.EnumAetherPerkType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;

import org.lwjgl.input.Keyboard;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;

public class GuiAetherPerks extends GuiScreen 
{

	private boolean enableMoaEditor, enableHaloEditor, enableGlowEditor;

	private IPlayerAether player;

	private EntityMoa moa;

	public GuiButton perkMoa, perkHalo, perkGlow;

	private GuiButton defualtSkin, enableHalo, confirmPreference, enableGlow;

	private GuiTextField moaWingMarking, moaWing, moaBody, moaBodyMarking, moaEye, moaOutside;

	private DonatorMoaSkin moaSkin = new DonatorMoaSkin();

    protected int guiLeft;

    protected int guiTop;

	public GuiAetherPerks()
	{
		super();

		this.mc = Minecraft.getMinecraft();
    	this.player = AetherAPI.getInstance().get(this.mc.player);
    	this.moaSkin = ((PlayerAether)this.player).donatorMoaSkin;

		this.moa = this.player.getEntity().getRidingEntity() instanceof EntityMoa ? (EntityMoa) this.player.getEntity().getRidingEntity() : new EntityMoa(this.mc.world, AetherMoaTypes.blue);
	}

	@Override
    public void updateScreen()
    {
    	this.moaWingMarking.updateCursorCounter();
    	this.moaWing.updateCursorCounter();
    	this.moaBody.updateCursorCounter();
    	this.moaBodyMarking.updateCursorCounter();
    	this.moaEye.updateCursorCounter();
    	this.moaOutside.updateCursorCounter();

    	if (!this.moaWingMarking.getText().isEmpty())
    	{
    		this.moaSkin.setWingMarkingColor(Integer.decode("0x" + this.moaWingMarking.getText()));
    	}

    	if (!this.moaWing.getText().isEmpty())
    	{
    		this.moaSkin.setWingColor(Integer.decode("0x" + this.moaWing.getText()));
    	}

    	if (!this.moaBody.getText().isEmpty())
    	{
    		this.moaSkin.setBodyColor(Integer.decode("0x" + this.moaBody.getText()));
    	}

    	if (!this.moaBodyMarking.getText().isEmpty())
    	{
    		this.moaSkin.setMarkingColor(Integer.decode("0x" + this.moaBodyMarking.getText()));
    	}

    	if (!this.moaEye.getText().isEmpty())
    	{
    		this.moaSkin.setEyeColor(Integer.decode("0x" + this.moaEye.getText()));
    	}

    	if (!this.moaOutside.getText().isEmpty())
    	{
    		this.moaSkin.setOutsideColor(Integer.decode("0x" + this.moaOutside.getText()));
    	}

    	this.moaSkin.shouldUseDefualt(this.defualtSkin.displayString.contains("true"));

    	if (!this.enableHaloEditor)
    	{
    		this.enableHalo.visible = false;
    	}

    	if (!this.enableMoaEditor)
    	{
    		this.defualtSkin.visible = false;
    		this.confirmPreference.visible = false;
    	}
    	
    	if (!this.enableGlowEditor)
    	{
    		this.enableGlow.visible = false;
    	}
    }

	@Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);

        this.guiLeft = (this.width - 176) / 2;
        this.guiTop = (this.height - 166) / 2;

    	this.buttonList.add(this.perkMoa = new GuiButton(1, 4, 17, 100, 20, "Moa Customizer"));
    	this.buttonList.add(this.perkHalo = new GuiButton(5, 110, 17, 100, 20, "Developer Perks"));

    	this.buttonList.add(this.enableHalo = new GuiButton(6, this.width / 2 - 0, this.height - 20, 100, 20, "Enable Halo: "));
    	this.buttonList.add(this.enableGlow = new GuiButton(7, this.width / 2 - 100, this.height - 20, 100, 20, "Enable Glow: "));
    	this.buttonList.add(this.defualtSkin = new GuiButton(2,  this.width / 2 - 100, this.height - 20, 100, 20, "Default skin: " + ((PlayerAether)this.player).donatorMoaSkin.shouldUseDefualt()));
    	this.buttonList.add(this.confirmPreference = new GuiButton(4,  this.width / 2, this.height - 20, 100, 20, "Confirm Skin"));

    	if (!AetherRankings.isRankedPlayer(this.player.getEntity().getUniqueID()))
    	{
    		this.perkHalo.visible = false;
    	}

    	this.enableHalo.displayString = this.enableHalo.displayString + Boolean.toString(((PlayerAether)this.player).shouldRenderHalo ? false : true);
    	this.enableGlow.displayString = this.enableGlow.displayString + Boolean.toString(((PlayerAether)this.player).shouldRenderGlow ? false : true);
    	
        this.moaEye = new GuiTextField(1, this.fontRenderer, (this.width / 2) + 105, 70, 45, 20);
        this.moaEye.setMaxStringLength(6);
        this.moaEye.setText(Integer.toHexString(((PlayerAether)this.player).donatorMoaSkin.getEyeColor()));

        this.moaWingMarking = new GuiTextField(5, this.fontRenderer, (this.width / 2) + 105, this.height - 40, 45, 20);
        this.moaWingMarking.setMaxStringLength(6);
        this.moaWingMarking.setText(Integer.toHexString(((PlayerAether)this.player).donatorMoaSkin.getWingMarkingColor()));

        this.moaWing = new GuiTextField(10, this.fontRenderer, (this.width / 2) + 105, this.height / 2 + 15, 45, 20);
        this.moaWing.setMaxStringLength(6);
        this.moaWing.setText(Integer.toHexString(((PlayerAether)this.player).donatorMoaSkin.getWingColor()));

        this.moaBody = new GuiTextField(14, this.fontRenderer, (this.width / 2) - 155, this.height / 2 + 15, 45, 20);
        this.moaBody.setMaxStringLength(6);
        this.moaBody.setText(Integer.toHexString(((PlayerAether)this.player).donatorMoaSkin.getBodyColor()));

        this.moaBodyMarking = new GuiTextField(18, this.fontRenderer, (this.width / 2) - 155, this.height - 40, 45, 20);
        this.moaBodyMarking.setMaxStringLength(6);
        this.moaBodyMarking.setText(Integer.toHexString(((PlayerAether)this.player).donatorMoaSkin.getMarkingColor()));

        this.moaOutside = new GuiTextField(22, this.fontRenderer, (this.width / 2) - 155, 70, 45, 20);
        this.moaOutside.setMaxStringLength(6);
        this.moaOutside.setText(Integer.toHexString(((PlayerAether)this.player).donatorMoaSkin.getOutsideColor()));
    }

	@Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	super.keyTyped(typedChar, keyCode);

    	if (this.enableMoaEditor)
    	{
    		if (keyCode > 1 && keyCode < 12 || keyCode == 14 || keyCode == 30 || keyCode == 48 || keyCode == 46 || keyCode == 32 || keyCode == 18 || keyCode == 33)
    		{
            	this.moaWingMarking.textboxKeyTyped(typedChar, keyCode);
            	this.moaWing.textboxKeyTyped(typedChar, keyCode);
            	this.moaBody.textboxKeyTyped(typedChar, keyCode);
            	this.moaBodyMarking.textboxKeyTyped(typedChar, keyCode);
            	this.moaEye.textboxKeyTyped(typedChar, keyCode);
            	this.moaOutside.textboxKeyTyped(typedChar, keyCode);
    		}
    	}
    }

	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.enableMoaEditor)
        {
            this.moaWingMarking.mouseClicked(mouseX, mouseY, mouseButton);
            this.moaWing.mouseClicked(mouseX, mouseY, mouseButton);
            this.moaBody.mouseClicked(mouseX, mouseY, mouseButton);
            this.moaBodyMarking.mouseClicked(mouseX, mouseY, mouseButton);
            this.moaEye.mouseClicked(mouseX, mouseY, mouseButton);
            this.moaOutside.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

	@Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
		AetherNetworkingManager.sendToServer(new PacketPerkChanged(this.player.getEntity().getEntityId(), EnumAetherPerkType.Moa, this.moaSkin));
    }

	@Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
		if (button.id == 1)
		{
			boolean shouldEnable = this.enableMoaEditor ? false : true;

			this.enableMoaEditor = shouldEnable;
			this.enableHaloEditor = false;
			this.enableHalo.visible = false;
			this.confirmPreference.visible = true;
			this.defualtSkin.visible = true;
			this.enableGlowEditor = false;
			this.enableGlow.visible = false;
		}
		else if (button.id == 2)
		{
			boolean enableDefualt = ((PlayerAether)this.player).donatorMoaSkin.shouldUseDefualt() ? false : true;

			this.defualtSkin.displayString = "Use Default: " + Boolean.toString(enableDefualt);
			((PlayerAether)this.player).donatorMoaSkin.shouldUseDefualt(enableDefualt);
		}
		else if (button.id == 4)
		{
			AetherNetworkingManager.sendToServer(new PacketPerkChanged(this.player.getEntity().getEntityId(), EnumAetherPerkType.Moa, this.moaSkin));
		}
		else if (button.id == 5)
		{
			boolean shouldEnable = this.enableHaloEditor ? false : true;
			
			boolean shouldEnableGlow = this.enableGlowEditor ? false : true;

			this.enableHaloEditor = shouldEnable;
			this.enableMoaEditor = false;
			this.enableHalo.visible = true;
			
			if (AetherRankings.isDeveloper(this.player.getEntity().getUniqueID()))
	    	{
				this.enableGlowEditor = shouldEnableGlow;
				this.enableGlow.visible = true;	
	    	}
		}
		else if (button.id == 6)
		{
			boolean enableHalo = ((PlayerAether)this.player).shouldRenderHalo ? false : true;

			AetherNetworkingManager.sendToServer(new PacketPerkChanged(this.player.getEntity().getEntityId(), EnumAetherPerkType.Halo, enableHalo));
			this.enableHalo.displayString = "Enable Halo: " + Boolean.toString(enableHalo);
		}
		else if (button.id == 7)
		{
			boolean enableGlow = ((PlayerAether)this.player).shouldRenderGlow ? false : true;

			AetherNetworkingManager.sendToServer(new PacketPerkChanged(this.player.getEntity().getEntityId(), EnumAetherPerkType.Glow, enableGlow));
			this.enableGlow.displayString = "Enable Glow: " + Boolean.toString(enableGlow);
		}
    }

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        this.drawCenteredString(this.fontRenderer, "Donator Options", 45, 4, 16777215);

        if (this.enableMoaEditor)
        {
        	GuiInventory.drawEntityOnScreen(this.width / 2, this.height / 2 + (this.height / 2) - 30, (this.height / 3) - 20, (float)(this.guiLeft + 51) - (float)mouseX, (float)(75 - 50) - (float)mouseY, this.moa);

        	/* Left Side*/

            this.drawString(this.fontRenderer, "Leg/Beak Color", (this.width / 2) - 170, 55, 16777215);

            this.drawString(this.fontRenderer, "Body Color", (this.width / 2) - 160, this.height / 2, 16777215);

            this.drawString(this.fontRenderer, "Body Marking Color", (this.width / 2) - 180, this.height - 55, 16777215);

        	/* Right Side */

            this.drawString(this.fontRenderer, "Eye Color", (this.width / 2) + 104, 55, 16777215);

            this.drawString(this.fontRenderer, "Wing Color", (this.width / 2) + 102, this.height / 2, 16777215);

            this.drawString(this.fontRenderer, "Wing Marking Color", (this.width / 2) + 82, this.height - 55, 16777215);

            this.moaWingMarking.drawTextBox();
            this.moaWing.drawTextBox();
            this.moaBody.drawTextBox();
            this.moaBodyMarking.drawTextBox();
            this.moaEye.drawTextBox();
            this.moaOutside.drawTextBox();
        }

        if (this.enableHaloEditor)
        {
        	GuiInventory.drawEntityOnScreen(this.width / 2, this.height / 2 + (this.height / 2) - 30, (this.height / 3) - 20, (float)(this.guiLeft + 51) - (float)mouseX, (float)(75 - 50) - (float)mouseY, this.player.getEntity());
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

	@Override
    public void drawWorldBackground(int tint)
    {
        this.drawGradientRect(0, 0, this.width, 50, -1072689136, -804253680);

        if (this.mc.world != null)
        {
            this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        }
        else
        {
            this.drawBackground(tint);
        }
    }

}