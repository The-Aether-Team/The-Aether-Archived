package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.ValkyrieQueenRenderer;
import com.legacy.aether.common.entities.bosses.valkyrie_queen.EntityValkyrieQueen;

public class ValkyrieQueenFactory implements IRenderFactory<EntityValkyrieQueen>
{

	@Override
	public Render<EntityValkyrieQueen> createRenderFor(RenderManager manager)
	{
		return new ValkyrieQueenRenderer(manager);
	}

}
