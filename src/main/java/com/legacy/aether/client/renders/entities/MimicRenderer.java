package com.legacy.aether.client.renders.entities;

import java.util.Calendar;

import com.legacy.aether.client.models.entities.MimicModel;
import com.legacy.aether.entities.hostile.EntityMimic;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class MimicRenderer extends RenderLiving<EntityMimic>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/mimic/mimic.png");

    private static final ResourceLocation XMAS_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/mimic/christmas_mimic.png");

    public MimicRenderer(RenderManager renderManager)
    {
        super(renderManager, new MimicModel(), 1.0F);
    }

    @Override
    public void doRender(EntityMimic entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	protected ResourceLocation getEntityTexture(EntityMimic entity)
    {
		Calendar calendar = Calendar.getInstance();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
		{
			return XMAS_TEXTURE;
		}
		else
		{
			return TEXTURE;	
		}
    }

}