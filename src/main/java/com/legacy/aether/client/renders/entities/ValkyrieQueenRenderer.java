package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.ValkyrieModel;
import com.legacy.aether.server.entities.bosses.valkyrie_queen.EntityValkyrieQueen;

public class ValkyrieQueenRenderer extends RenderLiving<EntityValkyrieQueen> 
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/bosses/valkyrie_queen/valkyrie_queen.png");

	public ValkyrieQueenRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ValkyrieModel(), 0.3F);
	}

    protected void preRenderCallback(EntityValkyrieQueen valkyrie, float partialTickTime)
    {
        ((ValkyrieModel)this.getMainModel()).sinage = valkyrie.sinage;
        ((ValkyrieModel)this.getMainModel()).gonRound = valkyrie.onGround;
        ((ValkyrieModel)this.getMainModel()).halow = true;
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityValkyrieQueen entity) 
	{
		return TEXTURE;
	}

}
