package com.legacy.aether.client.gui.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class AetherNotificationModUpdateScreen extends GuiScreen
{

    private static final ResourceLocation VERSION_CHECK_ICONS = new ResourceLocation(ForgeVersion.MOD_ID, "textures/gui/version_check_icons.png");

    private final GuiButton modButton;
    private ForgeVersion.Status showNotification = null;
    private boolean hasCheckedForUpdates = false;
    private static int xPos, yPos;

    public AetherNotificationModUpdateScreen(GuiButton modButton)
    {
        this.modButton = modButton;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        if (!hasCheckedForUpdates)
        {
            if (modButton != null)
            {
                for (ModContainer mod : Loader.instance().getModList())
                {
                    ForgeVersion.Status status = ForgeVersion.getResult(mod).status;
                    if (status == ForgeVersion.Status.OUTDATED || status == ForgeVersion.Status.BETA_OUTDATED)
                    {
                        // TODO: Needs better visualization, maybe stacked icons
                        // drawn in a terrace-like pattern?
                        showNotification = ForgeVersion.Status.OUTDATED;
                    }
                }
            }
            hasCheckedForUpdates = true;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (showNotification == null || !showNotification.shouldDraw() || ForgeModContainer.disableVersionCheck)
        {
            return;
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(VERSION_CHECK_ICONS);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.pushMatrix();

        int x = xPos;
        int y = yPos;
        int w = modButton.width;
        int h = modButton.height;

        drawModalRectWithCustomSizedTexture(x + w - (h / 2 + 4), y + (h / 2 - 4), showNotification.getSheetOffset() * 8, (showNotification.isAnimated() && ((System.currentTimeMillis() / 800 & 1) == 1)) ? 8 : 0, 8, 8, 64, 16);
        GlStateManager.popMatrix();
    }

    public static AetherNotificationModUpdateScreen init(GuiMainMenu guiMainMenu, GuiButton modButton, int x, int y)
    {
        AetherNotificationModUpdateScreen notificationModUpdateScreen = new AetherNotificationModUpdateScreen(modButton);
        notificationModUpdateScreen.setGuiSize(guiMainMenu.width, guiMainMenu.height);
        notificationModUpdateScreen.initGui();
        xPos = x;
        yPos = y;
        return notificationModUpdateScreen;
    }

    public boolean shouldRender()
    {
        return showNotification != null && showNotification.shouldDraw() && !ForgeModContainer.disableVersionCheck;
    }
}