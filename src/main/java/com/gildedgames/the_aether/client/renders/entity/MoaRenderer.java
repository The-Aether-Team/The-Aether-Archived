package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.MoaModel;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.entities.util.AetherMoaTypes;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.player.perks.util.DonatorMoaSkin;

public class MoaRenderer extends RenderLiving {

	private static final ResourceLocation TEXTURE_OUTSIDE = Aether.locate("textures/entities/moa/canvas/moa_outside.png");

	private static final ResourceLocation TEXTURE_EYE = Aether.locate("textures/entities/moa/canvas/moa_eye.png");

	private static final ResourceLocation TEXTURE_BODY = Aether.locate("textures/entities/moa/canvas/moa_body.png");

	private static final ResourceLocation TEXTURE_MARKINGS = Aether.locate("textures/entities/moa/canvas/moa_markings.png");

	private static final ResourceLocation TEXTURE_WING = Aether.locate("textures/entities/moa/canvas/moa_wing.png");

	private static final ResourceLocation TEXTURE_WING_MARKINGS = Aether.locate("textures/entities/moa/canvas/moa_wing_markings.png");

	private static final ResourceLocation TEXTURE_UNCHANGED = Aether.locate("textures/entities/moa/canvas/moa_unchanged.png");

	private static final ResourceLocation SADDLE = Aether.locate("textures/entities/moa/moa_saddle.png");

	private static final ResourceLocation BLACK_SADDLE = Aether.locate("textures/entities/moa/black_moa_saddle.png");

	private static final ResourceLocation MOS = new ResourceLocation("aether_legacy", "textures/entities/moa/mos.png");

	private static final ResourceLocation RAPTOR = new ResourceLocation("aether_legacy", "textures/entities/moa/raptor.png");

	public MoaRenderer() {
		super(new MoaModel(0.0F), 1.0F);

		this.setRenderPassModel(new MoaModel(0.5F));
	}

	@Override
	protected void renderModel(EntityLivingBase entity, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
		EntityMoa moa = (EntityMoa) entity;

		if (!entity.isInvisible()) {
			if (!(moa.riddenByEntity instanceof EntityPlayer)) {
				this.bindTexture(this.getEntityTexture(entity));
				this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
			} else {
				PlayerAether playerAether = PlayerAether.get((EntityPlayer) moa.riddenByEntity);

				if (playerAether.donatorMoaSkin != null && !playerAether.donatorMoaSkin.shouldUseDefualt()) {
					DonatorMoaSkin skin = playerAether.donatorMoaSkin;

					GL11.glColor3f(1.0F, 1.0F, 1.0F);

					this.bindTexture(TEXTURE_UNCHANGED);
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					this.bindTexture(TEXTURE_WING_MARKINGS);
					GL11.glColor3f(r(skin.getWingMarkingColor()), g(skin.getWingMarkingColor()), b(skin.getWingMarkingColor()));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					this.bindTexture(TEXTURE_WING);
					GL11.glColor3f(r(skin.getWingColor()), g(skin.getWingColor()), b(skin.getWingColor()));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					this.bindTexture(TEXTURE_MARKINGS);
					GL11.glColor3f(r(skin.getMarkingColor()), g(skin.getMarkingColor()), b(skin.getMarkingColor()));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					this.bindTexture(TEXTURE_BODY);
					GL11.glColor3f(r(skin.getBodyColor()), g(skin.getBodyColor()), b(skin.getBodyColor()));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					this.bindTexture(TEXTURE_EYE);
					GL11.glColor3f(r(skin.getEyeColor()), g(skin.getEyeColor()), b(skin.getEyeColor()));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					this.bindTexture(TEXTURE_OUTSIDE);
					GL11.glColor3f(r(skin.getOutsideColor()), g(skin.getOutsideColor()), b(skin.getOutsideColor()));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

					GL11.glColor3f(1.0F, 1.0F, 1.0F);
				} else {
					this.bindTexture(this.getEntityTexture(entity));
					this.mainModel.render(moa, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
				}
			}
		} else {
			this.mainModel.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, entity);
		}
	}

	protected int renderLayer(EntityMoa entity, int pass, float particleTicks) {
		if (pass == 0 && entity.isSaddled()) {
			GL11.glTranslatef(0.0F, 0.02F, 0.0F);
			this.bindTexture(entity.getMoaType() == AetherMoaTypes.black ? BLACK_SADDLE : SADDLE);

			return 1;
		}

		return -1;
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int pass, float particleTicks) {
		return this.renderLayer((EntityMoa) entity, pass, particleTicks);
	}

	protected float getWingRotation(EntityMoa moa, float f) {
		float f1 = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation) * f;
		float f2 = moa.prevDestPos + (moa.destPos - moa.prevDestPos) * f;

		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase entityliving, float f) {
		return this.getWingRotation((EntityMoa) entityliving, f);
	}

	protected void scaleMoa(EntityMoa entityMoa) {
		float moaScale = entityMoa.isChild() ? 1.0f : 1.8f;

		GL11.glScalef(moaScale, moaScale, moaScale);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		/*
		 * Dear person who finds this,
		 * have fun :)
		 * GL11.glRotatef((entityliving.ticksExisted * entityliving.ticksExisted) * (f * f), 0.0F, 1.0F, 0.0F);
		 */
		this.scaleMoa((EntityMoa) entityliving);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		EntityMoa moa = (EntityMoa) entity;

		if (moa.riddenByEntity instanceof EntityPlayer) {
			PlayerAether player = PlayerAether.get((EntityPlayer) moa.riddenByEntity);

			if (player != null && !player.donatorMoaSkin.shouldUseDefualt()) {
				return null;
			}
		}

		if (moa.hasCustomNameTag() && "Mos".equals(moa.getCustomNameTag()) && (moa.getMoaType() == AetherMoaTypes.orange))
		{
			return MOS;
		}
		else if (moa.hasCustomNameTag() && "Raptor__".equals(moa.getCustomNameTag()) && (moa.getMoaType() == AetherMoaTypes.blue))
		{
			return RAPTOR;
		}
		else
		{
			return moa.getMoaType().getTexture(moa.isSaddled(), moa.riddenByEntity != null);
		}
	}

	private static float r(int r) {
		return ((r >> 16) & 0xff) / 255F;
	}

	private static float g(int g) {
		return ((g >> 8) & 0xff) / 255F;
	}

	private static float b(int b) {
		return (b & 0xff) / 255F;
	}

}