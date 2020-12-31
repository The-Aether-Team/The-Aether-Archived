package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.hostile.swet.EnumSwetType;
import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class SwetRenderer extends RenderLiving {

	private static final ResourceLocation TEXTURE_BLUE = Aether.locate("textures/entities/swet/swet_blue.png");

	private static final ResourceLocation TEXTURE_GOLDEN = Aether.locate("textures/entities/swet/swet_golden.png");

	public SwetRenderer()
	{
		super(new ModelSlime(16), 0.3F);
	}

	protected int shouldRenderPass(EntitySwet entity, int pass, float particleTicks)
	{
		if (entity.isInvisible())
		{
			return 0;
		}
		else if (pass == 0)
		{
			this.setRenderPassModel(new ModelSlime(0));
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return 1;
		}
		else
		{
			if (pass == 1)
			{
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			return -1;
		}
	}

	protected void preRenderCallback(EntitySwet swet, float f)
	{
		float f1 = swet.swetHeight; //height
		float f2 = swet.swetWidth; //width
		float f3 = 1.5F; //scale

		if(swet.riddenByEntity != null)
		{
			f3 = 1.5F + (swet.riddenByEntity.width + swet.riddenByEntity.height) * 0.75F;
		}

		GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		this.preRenderCallback((EntitySwet)p_77041_1_, p_77041_2_);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntitySwet)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	@Override
	public ResourceLocation getEntityTexture(Entity swet)
	{
		return ((EntitySwet) swet).getType() == EnumSwetType.BLUE ? TEXTURE_BLUE : TEXTURE_GOLDEN;
	}
}