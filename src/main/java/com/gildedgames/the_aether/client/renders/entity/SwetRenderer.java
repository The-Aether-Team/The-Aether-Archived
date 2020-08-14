package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.hostile.swet.EnumSwetType;
import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class SwetRenderer extends RenderLiving {

	private static final ResourceLocation TEXTURE_BLUE = Aether.locate("textures/entities/swet/swet_blue.png");

	private static final ResourceLocation TEXTURE_GOLDEN = Aether.locate("textures/entities/swet/swet_golden.png");

	public SwetRenderer() {
		super(new ModelSlime(16), 0.3F);

		this.setRenderPassModel(new ModelSlime(0));
	}

	protected int renderEyeGlow(EntitySwet entity, int pass, float particleTicks) {
		if (entity.isInvisible()) {
			return 0;
		} else if (pass == 0) {
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return 1;
		} else {
			if (pass == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			return -1;
		}
	}

	protected void setupAnimation(EntitySwet swet, float f) {
		float f2 = 1.0F;
		float f1 = 1.0F;
		float f3 = 1.5F;

		if (!swet.onGround && swet.worldObj.isRemote) {
			if (swet.motionY > 0.85D) {
				f1 = 1.425F;
				f2 = 0.575F;
			} else if (swet.motionY < -0.85D) {
				f1 = 0.575F;
				f2 = 1.425F;
			} else {
				float f4 = (float) swet.motionY * 0.5F;
				f1 += f4;
				f2 -= f4;
			}
		}

		if (swet.riddenByEntity != null) {
			f3 = 1.5F + (swet.riddenByEntity.width + swet.riddenByEntity.height) * 2.0F;
		}

		GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int pass, float particleTicks) {
		return this.renderEyeGlow((EntitySwet) entity, pass, particleTicks);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase swet, float f) {
		this.setupAnimation((EntitySwet) swet, f);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity swet) {
		return ((EntitySwet) swet).getSwetType() == EnumSwetType.BLUE ? TEXTURE_BLUE : TEXTURE_GOLDEN;
	}
}