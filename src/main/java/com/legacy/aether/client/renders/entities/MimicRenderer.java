package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import java.util.Calendar;

import com.legacy.aether.client.models.entities.MimicModel;
import com.legacy.aether.entities.hostile.EntityMimic;

public class MimicRenderer extends RenderLiving<EntityMimic>
{

	private MimicModel modelbase;

    public MimicRenderer(RenderManager renderManager)
    {
        super(renderManager, new MimicModel(), 0.0F);

        this.modelbase = (MimicModel) this.mainModel;
    }

    @Override
    public void doRender(EntityMimic mimic, double x, double y, double z, float pitch, float yaw)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.rotate(180F - pitch, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1F, -1F, 1.0F);
        this.modelbase.setRotationAngles(0,0F, 0.0F, 0.0F, 0.0F, 0.0F, mimic);
        
        Calendar calendar = Calendar.getInstance();
		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26)
		{
			this.renderManager.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/entities/mimic/mimic_head_christmas.png"));
			this.modelbase.renderHead(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
			this.renderManager.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/entities/mimic/mimic_legs_christmas.png"));
			this.modelbase.renderLegs(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
		}
		else
		{
			this.renderManager.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/entities/mimic/mimic_head.png"));
			this.modelbase.renderHead(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
			this.renderManager.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/entities/mimic/mimic_legs.png"));
			this.modelbase.renderLegs(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
		}
		
		GlStateManager.popMatrix();
    }

	protected ResourceLocation getEntityTexture(EntityMimic entity)
    {
		return null;
    }

}