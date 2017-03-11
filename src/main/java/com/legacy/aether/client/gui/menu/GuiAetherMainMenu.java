package com.legacy.aether.client.gui.menu;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.client.GuiModList;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.legacy.aether.client.gui.button.GuiAetherButton;
import com.legacy.aether.client.gui.button.GuiDescButton;
import com.legacy.aether.client.gui.menu.hook.GuiMultiplayerHook;
import com.legacy.aether.client.gui.menu.hook.GuiWorldSelectionHook;
import com.legacy.aether.server.networking.AetherNetworkingManager;
import com.legacy.aether.server.networking.packets.PacketGameType;

public class GuiAetherMainMenu extends GuiScreen
{

    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    private static final ResourceLocation aetherTitleTextures = new ResourceLocation("aether_legacy", "textures/gui/title/aether.png");

    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
 
    private static final Random RANDOM = new Random();

    private String splashText;

	public Minecraft mc;

	private boolean style;

	public boolean worldLoaded;

	public GuiAetherMainMenu(boolean style)
	{
		this.mc = Minecraft.getMinecraft();
		this.style = style;

        this.splashText = "missingno";
        IResource iresource = null;

        try
        {
            List<String> list = Lists.<String>newArrayList();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(splashTexts);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                s = s.trim();

                if (!s.isEmpty())
                {
                    list.add(s);
                }
            }

            if (!list.isEmpty())
            {
                while (true)
                {
                    this.splashText = (String)list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783)
                    {
                        break;
                    }
                }
            }
        }
        catch (IOException var8)
        {
            ;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }

		AetherNetworkingManager.sendToServer(new PacketGameType(GameType.SPECTATOR));
	}

	@Override
	public void updateScreen() 
	{
		if(this.mc.thePlayer != null)
		{
			this.mc.thePlayer.rotationYaw += 0.2F;
			this.mc.thePlayer.rotationPitch = 0F;
		}
	}

    public void initGui()
    {
    	if (this.mc.thePlayer != null)
    	{
    		AetherNetworkingManager.sendToServer(new PacketGameType(GameType.SURVIVAL));
    	}

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24)
        {
            this.splashText = "Merry X-mas!";
        }
        else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1)
        {
            this.splashText = "Happy new year!";
        }
        else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
        {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

    	this.mc.gameSettings.hideGUI = true;

		int var5 = height / 4 + 20;

		if (!this.style)
		{
			this.buttonList.add(new GuiAetherButton(0, width / 70, var5 + 72, "Options"));
			this.buttonList.add(new GuiAetherButton(1, width / 70, var5, "Singleplayer"));
			this.buttonList.add(new GuiAetherButton(2, width / 70, var5 + 24, "Multiplayer"));
			this.buttonList.add(new GuiAetherButton(3, width / 70, var5 + 48, "Mods"));
			this.buttonList.add(new GuiAetherButton(4, width / 70, var5 + 96, "Quit"));
		}
		else
		{
			this.buttonList.add(new GuiButton(0, width / 70, var5 + 72, "Options"));
			this.buttonList.add(new GuiButton(1, width / 70, var5, "Singleplayer"));
			this.buttonList.add(new GuiButton(2, width / 70, var5 + 24, "Multiplayer"));
			this.buttonList.add(new GuiButton(3, width / 70, var5 + 48, "Mods"));
			this.buttonList.add(new GuiButton(4, width / 70, var5 + 96, "Quit"));
		}

		this.buttonList.add(new GuiDescButton(5, width - 24, 4, 20, 20, "W", "Toggle World"));
		this.buttonList.add(new GuiDescButton(6, width - 48, 4, 20, 20, "T", "Toggle Theme"));
        this.buttonList.add(new GuiButtonLanguage(7, this.width - 96, 4));
		this.buttonList.add(new GuiDescButton(8, width - 72, 4, 20, 20, "Q", "Quick Load"));
    }

	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		byte var6 = 15;
		byte var7 = 15;
        this.mc.getTextureManager().bindTexture(style ? minecraftTitleTextures : aetherTitleTextures);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
		drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
        GlStateManager.translate(!this.style ? 180.0F : 270.0F,!this.style ? 60.0F : 50.0F, 0.0F);
        GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
        f = f * 100.0F / (float)(this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GlStateManager.scale(f, f, f);
        this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
        GlStateManager.popMatrix();
		drawFooter();
	}

	private void drawFooter()
	{
        String version = "Minecraft 1.10.2";
	    String copyright = "Copyright Mojang AB. Do not distribute.";
	    String modTitle = "Aether Pre-1.10.2";
	    String modSubtitle = "Creation of Gilded Games";

		drawString(fontRendererObj, version, width - fontRendererObj.getStringWidth(version) - 5, height - 20, 0xFFFFFF);
		drawString(fontRendererObj, copyright, width - fontRendererObj.getStringWidth(copyright) - 5, height - 10, 0x707070);
		drawString(fontRendererObj, modTitle, 5, height - 20, 0xFFFFFF);
		drawString(fontRendererObj, modSubtitle, 5, height - 10, 0x707070);
	}

    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (button.id == 0)
    	{
    		this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
    	}

    	if (button.id == 1)
    	{
    		this.mc.displayGuiScreen(new GuiWorldSelectionHook(this));
    	}

    	if (button.id == 2)
    	{
    		this.mc.displayGuiScreen(new GuiMultiplayerHook(this));
    	}

    	if (button.id == 3)
    	{
    		this.mc.displayGuiScreen(new GuiModList(this));
    	}

    	if (button.id == 4)
    	{
    		this.mc.shutdown();
    	}

    	if (button.id == 5)
    	{
    		this.mc.displayGuiScreen(new GuiAetherMainMenuFallback());
    	}

    	if (button.id == 6)
    	{
    		this.mc.displayGuiScreen(new GuiAetherMainMenu(this.style ? false : true));
    	}

        if (button.id == 7)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }

        if (button.id == 8)
        {
    		AetherNetworkingManager.sendToServer(new PacketGameType(GameType.SURVIVAL));
    		Minecraft.getMinecraft().gameSettings.hideGUI = false;
        	this.mc.displayGuiScreen(null);
        }
    }

    public void onGuiClosed()
    {
		AetherNetworkingManager.sendToServer(new PacketGameType(GameType.SURVIVAL));
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

}