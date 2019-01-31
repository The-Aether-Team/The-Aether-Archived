package com.legacy.aether.client.renders.entities.layer;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.models.entities.ValkyrieModel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerValkyrieHalo implements LayerRenderer<EntityLivingBase>
{
    private final RenderManager renderManager;
    private final ValkyrieModel valkyrieModel = new ValkyrieModel();

    private static final ResourceLocation TEXTURE_HALO = new ResourceLocation("aether_legacy", "textures/entities/valkyrie/valkyrie.png");

    
    public LayerValkyrieHalo(RenderManager renderManagerIn)
    {
        this.renderManager = renderManagerIn;
    }

	@SuppressWarnings("static-access")
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float prevLimbSwing, float partialTicks, float rotation, float interpolateRotation, float prevRotationPitch, float scale)
    {
    	GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.scale(1.0F, 1.0F, 1.0F);		
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	    GlStateManager.depthMask(true);
	    int i = 61680;
	    int j = i % 65536;
	    int k = i / 65536;
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

	    this.valkyrieModel.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, entity);

		this.renderManager.renderEngine.bindTexture(TEXTURE_HALO);
		
		for(int t = 0; t < this.valkyrieModel.haloParts; t++) 
		{
			this.valkyrieModel.halo[t].render(scale);
		}

		GlStateManager.disableBlend();
		GL11.glDisable(GL11.GL_BLEND);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}