package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.renders.entities.SwetRenderer;
import com.legacy.aether.server.entities.passive.mountable.EntitySwet;

public class SwetLayer implements LayerRenderer<EntitySwet> 
{

	private RenderManager manager;

	private SwetRenderer render;

	private ModelSlime slime;

	public SwetLayer(RenderManager manager, SwetRenderer render)
	{
		this.manager = manager;
		this.render = render;
		this.slime = new ModelSlime(0);
	}

	@Override
	public void doRenderLayer(EntitySwet swet, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
        this.slime.setModelAttributes(this.render.getMainModel());
        this.slime.setLivingAnimations(swet, p_177141_2_, p_177141_3_, partialTicks);
		GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.manager.renderEngine.bindTexture(this.render.getEntityTexture(swet));
        this.slime.render(swet, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return false;
	}

}
