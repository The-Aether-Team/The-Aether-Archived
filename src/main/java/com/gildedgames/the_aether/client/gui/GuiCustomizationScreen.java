package com.gildedgames.the_aether.client.gui;

import com.gildedgames.the_aether.client.gui.button.GuiGlowButton;
import com.gildedgames.the_aether.client.gui.button.GuiHaloButton;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketPerkChanged;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.player.perks.util.EnumAetherPerkType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(Side.CLIENT)
public class GuiCustomizationScreen extends GuiScreen
{
    private final GuiScreen parentScreen;
    private String title;

    public GuiCustomizationScreen(GuiScreen parentScreenIn)
    {
        this.parentScreen = parentScreenIn;
    }

    @Override
    public void initGui()
    {
        int i = 0;
        this.title = I18n.format("gui.options.perk_customization.title");

        this.buttonList.add(new GuiHaloButton(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1)));
        ++i;

        if (AetherRankings.isDeveloper(Minecraft.getMinecraft().thePlayer.getUniqueID()))
        {
            this.buttonList.add(new GuiGlowButton(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1)));
            ++i;
        }

        if (i % 2 == 1)
        {
            ++i;
        }

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            if (button.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (button.id == 201)
            {
                PlayerAether player = PlayerAether.get(mc.thePlayer);

                boolean enableHalo = !player.shouldRenderHalo;

                player.shouldRenderHalo = enableHalo;
                AetherNetwork.sendToServer(new PacketPerkChanged(player.getEntity().getEntityId(), EnumAetherPerkType.Halo, player.shouldRenderHalo));
            }
            else if (button.id == 202)
            {
                PlayerAether player = PlayerAether.get(mc.thePlayer);

                boolean enableGlow = !player.shouldRenderGlow;

                player.shouldRenderGlow = enableGlow;
                AetherNetwork.sendToServer(new PacketPerkChanged(player.getEntity().getEntityId(), EnumAetherPerkType.Glow, player.shouldRenderGlow));
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
