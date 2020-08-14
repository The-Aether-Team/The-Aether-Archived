package com.gildedgames.the_aether.entities.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityCloudSmokeFX extends EntityFX {

	float smokeParticleScale;

	public double riseRate;

	public EntityCloudSmokeFX(World world, double x, double y, double z, double initialMotionX, double initialMotionY, double intialMotionZ, float size, float red, float blue, float green, double riseRate) {
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);

		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += initialMotionX;
		this.motionY += initialMotionY;
		this.motionZ += intialMotionZ;
		this.particleRed = red;
		this.particleBlue = blue;
		this.particleGreen = green;
		this.particleScale *= 0.75F;
		this.particleScale *= size;
		this.smokeParticleScale = this.particleScale;
		this.particleMaxAge = (int) (8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
		this.particleMaxAge *= size / 4;
		this.riseRate = riseRate;
	}

	@Override
	public void renderParticle(Tessellator renderer, float f, float f1, float f2, float f3, float f4, float f5) {
		super.renderParticle(renderer, f, f1, f2, f3, f4, f5);

		float f6 = (((float) this.particleAge + f) / (float) this.particleMaxAge) * 32F;
		if (f6 < 0.0F) {
			f6 = 0.0F;
		}
		if (f6 > 1.0F) {
			f6 = 1.0F;
		}
		this.particleScale = this.smokeParticleScale * f6;
		float f61 = (float) this.particleTextureIndexX / 16.0F;
		float f7 = f61 + 0.0624375F;
		float f8 = (float) this.particleTextureIndexY / 16.0F;
		float f9 = f8 + 0.0624375F;
		float f10 = 0.1F * this.particleScale;

		if (this.particleIcon != null) {
			f61 = this.particleIcon.getMinU();
			f7 = this.particleIcon.getMaxU();
			f8 = this.particleIcon.getMinV();
			f9 = this.particleIcon.getMaxV();
		}

		float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
		float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
		float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
		renderer.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		renderer.addVertexWithUV((double) (f11 - f1 * f10 - f4 * f10), (double) (f12 - f2 * f10), (double) (f13 - f3 * f10 - f5 * f10), (double) f7, (double) f9);
		renderer.addVertexWithUV((double) (f11 - f1 * f10 + f4 * f10), (double) (f12 + f2 * f10), (double) (f13 - f3 * f10 + f5 * f10), (double) f7, (double) f8);
		renderer.addVertexWithUV((double) (f11 + f1 * f10 + f4 * f10), (double) (f12 + f2 * f10), (double) (f13 + f3 * f10 + f5 * f10), (double) f61, (double) f8);
		renderer.addVertexWithUV((double) (f11 + f1 * f10 - f4 * f10), (double) (f12 - f2 * f10), (double) (f13 + f3 * f10 - f5 * f10), (double) f61, (double) f9);
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		}

		this.setParticleTextureIndex(7 - (this.particleAge * 8) / this.particleMaxAge);
		this.motionY += this.riseRate;

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (this.posY == this.prevPosY) {
			this.motionX *= 1.1000000000000001D;
			this.motionZ *= 1.1000000000000001D;
		}

		this.motionX *= 0.95999997854232788D;
		this.motionY *= this.riseRate;
		this.motionZ *= 0.95999997854232788D;

		if (this.isCollided) {
			this.motionX *= 0.69999998807907104D;
			this.motionZ *= 0.69999998807907104D;
		}
	}

}