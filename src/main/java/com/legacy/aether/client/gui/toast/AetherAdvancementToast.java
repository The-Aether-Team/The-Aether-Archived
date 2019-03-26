package com.legacy.aether.client.gui.toast;

import java.util.List;

import com.legacy.aether.client.audio.AetherMusicHandler;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class AetherAdvancementToast extends AdvancementToast
{

	private final Advancement advancement;
    private boolean hasPlayedSound = false;

	public AetherAdvancementToast(Advancement advancementIn, int advancementTypeIn) 
	{
		super(advancementIn);

		this.advancement = advancementIn;
	}

	@Override
    public IToast.Visibility draw(GuiToast toastGui, long delta)
    {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        DisplayInfo displayinfo = this.advancement.getDisplay();
        toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);

        if (displayinfo != null)
        {
            List<String> list = toastGui.getMinecraft().fontRenderer.listFormattedStringToWidth(displayinfo.getTitle().getFormattedText(), 125);
            int i = displayinfo.getFrame() == FrameType.CHALLENGE ? 16746751 : 16776960;

            if (list.size() == 1)
            {
                toastGui.getMinecraft().fontRenderer.drawString(I18n.format("advancements.toast." + displayinfo.getFrame().getName()), 30, 7, i | -16777216);
                toastGui.getMinecraft().fontRenderer.drawString(displayinfo.getTitle().getFormattedText(), 30, 18, -1);
            }
            else
            {
                if (delta < 1500L)
                {
                    int k = MathHelper.floor(MathHelper.clamp((float)(1500L - delta) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                    toastGui.getMinecraft().fontRenderer.drawString(I18n.format("advancements.toast." + displayinfo.getFrame().getName()), 30, 11, i | k);
                }
                else
                {
                    int i1 = MathHelper.floor(MathHelper.clamp((float)(delta - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                    int l = 16 - list.size() * toastGui.getMinecraft().fontRenderer.FONT_HEIGHT / 2;

                    for (String s : list)
                    {
                        toastGui.getMinecraft().fontRenderer.drawString(s, 30, l, 16777215 | i1);
                        l += toastGui.getMinecraft().fontRenderer.FONT_HEIGHT;
                    }
                }
            }

            if (!this.hasPlayedSound && delta > 0L)
            {
                this.hasPlayedSound = true;
                int type = this.advancement.getId().getPath().equals("like_a_bossaru") ? 1 : this.advancement.getId().getPath().equals("dethroned") ? 2 : 0;
                toastGui.getMinecraft().getSoundHandler().playSound(AetherMusicHandler.getAchievementSound(type));
            }

            RenderHelper.enableGUIStandardItemLighting();
            toastGui.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)null, displayinfo.getIcon(), 8, 8);

            return delta >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        }

        return IToast.Visibility.HIDE;
    }

}