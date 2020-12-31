package com.gildedgames.the_aether.client.overlay;

import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;

@SideOnly(Side.CLIENT)
public class AetherOverlay
{

	private static final ResourceLocation TEXTURE_JUMPS = new ResourceLocation("aether_legacy", "textures/gui/jumps.png");

	private static final ResourceLocation TEXTURE_COOLDOWN_BAR = new ResourceLocation("aether_legacy", "textures/gui/cooldown_bar.png");

   	private static final ResourceLocation TEXTURE_POISON_VIGNETTE = new ResourceLocation("aether_legacy", "textures/blur/poison_vignette.png");

    private static final ResourceLocation TEXTURE_CURE_VIGNETTE = new ResourceLocation("aether_legacy", "textures/blur/cure_vignette.png");
 
    public static void renderPoison(Minecraft mc)
    {
    	IPlayerAether playerAether = AetherAPI.getInstance().get(mc.player);

    	if(((PlayerAether) playerAether).poisonTime > 0)
    	{
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder renderer = tessellator.getBuffer();

    		float alpha = getPoisonAlpha((float)(((PlayerAether)playerAether).poisonTime % 50) / 50);

            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();

            mc.renderEngine.bindTexture(TEXTURE_POISON_VIGNETTE);

    		renderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            renderer.pos(0.0D, (double)height, -90.0D).tex(0.0D, 1.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            renderer.pos((double)width, (double)height, -90.0D).tex(1.0D, 1.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            renderer.pos((double)width, 0.0D, -90.0D).tex(1.0D, 0.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            renderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            tessellator.draw();

            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
    	}
    }

    public static void renderCure(Minecraft mc)
    {
    	IPlayerAether playerAether = AetherAPI.getInstance().get(mc.player);

    	if(((PlayerAether) playerAether).isCured())
    	{
            ScaledResolution scaledresolution = new ScaledResolution(mc);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder renderer = tessellator.getBuffer();

    		float alpha = 0.5F;
            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableAlpha();

            mc.renderEngine.bindTexture(TEXTURE_CURE_VIGNETTE);

    		renderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            renderer.pos(0.0D, (double)height, -90.0D).tex(0.0D, 1.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            renderer.pos((double)width, (double)height, -90.0D).tex(1.0D, 1.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            renderer.pos((double)width, 0.0D, -90.0D).tex(1.0D, 0.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            renderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).color(0.5F, 0.5F, 0.5F, alpha).endVertex();
            tessellator.draw();

            GlStateManager.depthMask(true);
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
    	}
    }

	public static void renderIronBubble(Minecraft mc)
	{
		ScaledResolution scaledresolution = new ScaledResolution(mc);

		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();

		mc.renderEngine.bindTexture(Gui.ICONS);

		int bubbleAmount = AetherAPI.getInstance().get(mc.player).getAccessoryInventory().getAccessoryCount(new ItemStack(ItemsAether.iron_bubble));
		int thirstOffset = Loader.isModLoaded("toughasnails") ? -10 : 0;

		if (mc.playerController.shouldDrawHUD() && mc.player.isInWater() && mc.player.isInsideOfMaterial(Material.WATER))
		{
			for (int i = 0; i < bubbleAmount; ++i)
			{
				drawTexturedModalRect((width / 2 - 8 * i) + 81, height - 49 + thirstOffset, 16, 18, 9, 9);
			}
		}

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
	}

	public static void renderCooldown(Minecraft mc)
	{
		IPlayerAether playerInfo = AetherAPI.getInstance().get(mc.player);

		if (playerInfo.getHammerCooldown() != 0)
		{
			ScaledResolution scaledresolution = new ScaledResolution(mc);

			int cooldownRemaining = (int) ((float) (playerInfo.getHammerCooldown()) / (float) (playerInfo.getHammerMaxCooldown()) * 128F);
			int width = scaledresolution.getScaledWidth();

			mc.fontRenderer.drawStringWithShadow(playerInfo.getHammerName() + " " + I18n.format("item.notch_hammer.cooldown"), (width / 2) - (mc.fontRenderer.getStringWidth(playerInfo.getHammerName() + " Cooldown") / 2), 32, 0xffffffff);

	        GlStateManager.pushMatrix();

	        GlStateManager.enableBlend();
	        GlStateManager.disableDepth();
	        GlStateManager.depthMask(false);
	        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        GlStateManager.disableAlpha();

			mc.renderEngine.bindTexture(TEXTURE_COOLDOWN_BAR);

			drawTexturedModalRect(width / 2 - 64, 42, 0, 8, 128, 8);

			drawTexturedModalRect(width / 2 - 64, 42, 0, 0, cooldownRemaining, 8);

	        GlStateManager.depthMask(true);
	        GlStateManager.enableDepth();
	        GlStateManager.enableAlpha();
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	        GlStateManager.popMatrix();
		}
	}

	public static void renderJumps(Minecraft mc)
	{
		EntityPlayer player = mc.player;

		if (!(player.getRidingEntity() instanceof EntityMoa))
		{
			return;
		}

		ScaledResolution scaledresolution = new ScaledResolution(mc);

		EntityMoa moa = (EntityMoa) (player.getRidingEntity());

		int width = scaledresolution.getScaledWidth();

		GlStateManager.pushMatrix();

		mc.renderEngine.bindTexture(TEXTURE_JUMPS);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		for (int jump = 0; jump < moa.getMaxJumps(); jump++)
		{
			int yPos = 18;
			int xPos = ((width / 2) + (jump * 8)) - (moa.getMaxJumps() * 8) / 2;

			if (jump < moa.getRemainingJumps())
			{
				drawTexturedModalRect(xPos, yPos, 0, 0, 9, 11);
			}
			else
			{
				drawTexturedModalRect(xPos, yPos, 10, 0, 9, 11);
			}
		}

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.popMatrix();
	}

	public static void renderAetherPortal(float timeInPortal, ScaledResolution scaledRes)
    {
        if (timeInPortal < 1.0F)
        {
            timeInPortal = timeInPortal * timeInPortal;
            timeInPortal = timeInPortal * timeInPortal;
            timeInPortal = timeInPortal * 0.8F + 0.2F;
        }

        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, timeInPortal);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(BlocksAether.aether_portal.getDefaultState());
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMinV();
        float f2 = textureatlassprite.getMaxU();
        float f3 = textureatlassprite.getMaxV();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex((double)f, (double)f3).endVertex();
        vertexbuffer.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex((double)f2, (double)f3).endVertex();
        vertexbuffer.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex((double)f2, (double)f1).endVertex();
        vertexbuffer.pos(0.0D, 0.0D, -90.0D).tex((double)f, (double)f1).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

	public static void renderBossHP(Minecraft mc) 
	{
		IPlayerAether player = AetherAPI.getInstance().get(mc.player);

		IAetherBoss boss = player.getFocusedBoss();

		if (player.getFocusedBoss() != null) 
		{
			String bossTitle = boss.getBossTitle();
			ScaledResolution scaledresolution = new ScaledResolution(mc);

	        int healthRemaining = (int) (boss.getBossHealth() / boss.getMaxBossHealth() * 256F);
			int width = scaledresolution.getScaledWidth();

			GlStateManager.pushMatrix();

	        mc.fontRenderer.drawStringWithShadow(bossTitle, width / 2 - (mc.fontRenderer.getStringWidth(bossTitle) / 2), 2, 0xffffffff);

	        mc.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/gui/boss_bar.png"));

	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	        drawTexturedModalRect(width / 2 - 128, 12, 0, 16, 256, 32);

	        drawTexturedModalRect(width/ 2 - 128, 12, 0, 0, healthRemaining, 16);

	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	        GlStateManager.popMatrix();
		}
	}

	public static void drawTexturedModalRect(float x, float y, float u, float v, float width, float height)
	{
		float zLevel = -90.0F;

		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.getInstance();
		BufferBuilder renderer = var9.getBuffer();
		renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		renderer.pos((double) (x + 0), (double) (y + height), (double) zLevel).tex((double) ((float) (u + 0) * var7), (double) ((float) (v + height) * var8)).endVertex();
		renderer.pos((double) (x + width), (double) (y + height), (double) zLevel).tex((double) ((float) (u + width) * var7), (double) ((float) (v + height) * var8)).endVertex();
		renderer.pos((double) (x + width), (double) (y + 0), (double) zLevel).tex((double) ((float) (u + width) * var7), (double) ((float) (v + 0) * var8)).endVertex();
		renderer.pos((double) (x + 0), (double) (y + 0), (double) zLevel).tex((double) ((float) (u + 0) * var7), (double) ((float) (v + 0) * var8)).endVertex();
		var9.draw();
	}

    public static float getPoisonAlpha(float f)
    {
        return (f * f) / 5.0F + 0.4F;
    }

    public static float getCureAlpha(float f)
    {
        return (f * f) / 10.0F + 0.4F;
    }

}