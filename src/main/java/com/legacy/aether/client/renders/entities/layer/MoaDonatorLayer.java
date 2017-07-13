package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.MoaModel;
import com.legacy.aether.common.entities.passive.mountable.EntityMoa;
import com.legacy.aether.common.player.PlayerAether;
import com.legacy.aether.common.player.perks.util.DonatorMoaSkin;

public class MoaDonatorLayer implements LayerRenderer<EntityMoa>
{

	private static final ResourceLocation TEXTURE_OUTSIDE = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_outside.png");

	private static final ResourceLocation TEXTURE_EYE = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_eye.png");

	private static final ResourceLocation TEXTURE_BODY = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_body.png");

	private static final ResourceLocation TEXTURE_MARKINGS = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_markings.png");

	private static final ResourceLocation TEXTURE_WING = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_wing.png");

	private static final ResourceLocation TEXTURE_WING_MARKINGS = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_wing_markings.png");

	private static final ResourceLocation TEXTURE_UNCHANGED = new ResourceLocation("aether_legacy", "textures/entities/moa/canvas/moa_unchanged.png");

	private RenderManager renderManager;

	private MoaModel model;

	public MoaDonatorLayer(RenderManager renderManager, MoaModel model)
	{
		this.renderManager = renderManager;
		this.model = model;
	}

	@Override
	public void doRenderLayer(EntityMoa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) 
	{
		if (!moa.getPassengers().isEmpty() && moa.getPassengers().get(0) instanceof EntityPlayer)
		{
			PlayerAether player = PlayerAether.get((EntityPlayer) moa.getPassengers().get(0));

			if (player != null)
			{
				DonatorMoaSkin moaSkin = player.donatorMoaSkin;

				if (moaSkin != null && !moaSkin.shouldUseDefualt())
				{
					GlStateManager.pushMatrix();

					this.renderManager.renderEngine.bindTexture(TEXTURE_UNCHANGED);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();

					float red = ((moaSkin.getWingMarkingColor() >> 16) & 0xff) / 255F;
					float green = ((moaSkin.getWingMarkingColor() >> 8) & 0xff) / 255F;
					float blue = (moaSkin.getWingMarkingColor() & 0xff) / 255F;

					this.renderManager.renderEngine.bindTexture(TEXTURE_WING_MARKINGS);

					GlStateManager.color(red, green, blue);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					red = ((moaSkin.getWingColor() >> 16) & 0xff) / 255F;
					green = ((moaSkin.getWingColor() >> 8) & 0xff) / 255F;
					blue = (moaSkin.getWingColor() & 0xff) / 255F;

					this.renderManager.renderEngine.bindTexture(TEXTURE_WING);

					GlStateManager.color(red, green, blue);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					red = ((moaSkin.getMarkingColor() >> 16) & 0xff) / 255F;
					green = ((moaSkin.getMarkingColor() >> 8) & 0xff) / 255F;
					blue = (moaSkin.getMarkingColor() & 0xff) / 255F;

					this.renderManager.renderEngine.bindTexture(TEXTURE_MARKINGS);

					GlStateManager.color(red, green, blue);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					GlStateManager.popMatrix();
					
					GlStateManager.pushMatrix();
					red = ((moaSkin.getBodyColor() >> 16) & 0xff) / 255F;
					green = ((moaSkin.getBodyColor() >> 8) & 0xff) / 255F;
					blue = (moaSkin.getBodyColor() & 0xff) / 255F;

					this.renderManager.renderEngine.bindTexture(TEXTURE_BODY);

					GlStateManager.color(red, green, blue);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					red = ((moaSkin.getEyeColor() >> 16) & 0xff) / 255F;
					green = ((moaSkin.getEyeColor() >> 8) & 0xff) / 255F;
					blue = (moaSkin.getEyeColor() & 0xff) / 255F;

					this.renderManager.renderEngine.bindTexture(TEXTURE_EYE);

					GlStateManager.color(red, green, blue);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					GlStateManager.popMatrix();

					GlStateManager.pushMatrix();
					red = ((moaSkin.getOutsideColor() >> 16) & 0xff) / 255F;
					green = ((moaSkin.getOutsideColor() >> 8) & 0xff) / 255F;
					blue = (moaSkin.getOutsideColor() & 0xff) / 255F;

					this.renderManager.renderEngine.bindTexture(TEXTURE_OUTSIDE);

					GlStateManager.color(red, green, blue);

					this.model.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
					
					GlStateManager.popMatrix();
				}
			}
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

}
