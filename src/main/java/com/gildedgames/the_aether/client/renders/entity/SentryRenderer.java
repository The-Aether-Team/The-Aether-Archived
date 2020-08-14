package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.hostile.EntitySentry;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class SentryRenderer extends RenderLiving {

	private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/sentry/sentry.png");

	private static final ResourceLocation TEXTURE_LIT = Aether.locate("textures/entities/sentry/sentry_lit.png");

	private static final ResourceLocation TEXTURE_EYE = Aether.locate("textures/entities/sentry/eye.png");

	public SentryRenderer() {
		super(new ModelSlime(0), 0.3F);

		this.setRenderPassModel(this.mainModel);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		float f1 = 1.75F;
		GL11.glScalef(f1, f1, f1);
	}

	protected int renderEyeGlow(EntitySentry entity, int pass, float particleTicks) {
		if (pass == 0 && entity.isAwake()) {
			this.bindTexture(TEXTURE_EYE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

			if (entity.isInvisible()) {
				GL11.glDepthMask(false);
			} else {
				GL11.glDepthMask(true);
			}

			char c0 = 61680;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			return 1;
		}

		return -1;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int pass, float particleTicks) {
		return this.renderEyeGlow((EntitySentry) entity, pass, particleTicks);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return !((EntitySentry) entity).isAwake() ? TEXTURE : TEXTURE_LIT;
	}

}