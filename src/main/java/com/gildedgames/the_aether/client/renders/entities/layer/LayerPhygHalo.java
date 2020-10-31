package com.gildedgames.the_aether.client.renders.entities.layer;

import com.gildedgames.the_aether.entities.passive.mountable.EntityPhyg;
import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.client.models.attachments.ModelPlayerHalo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPhygHalo implements LayerRenderer<EntityPhyg>
{
	private final ResourceLocation TEXTURE_HALO = new ResourceLocation("aether_legacy", "textures/other/halo.png");

	private RenderManager renderManager;
    
    private final ModelPig pigModel = new ModelPig(0.0F);

    private ModelPlayerHalo modelHalo;
    
    public LayerPhygHalo(RenderManager manager)
    {
    	this.renderManager = manager;
    	this.modelHalo = new ModelPlayerHalo();
    }

	public void doRenderLayer(EntityPhyg phyg, float limbSwing, float prevLimbSwing, float partialTicks, float rotation, float interpolateRotation, float prevRotationPitch, float scale)
    {
        if (phyg.getCustomNameTag().equals("KingPhygieBoo"))
        {
        	GlStateManager.pushMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.scale(1.0F, 1.0F, 1.0F);

			//GlStateManager.translate(0.0F, 0.2F, -0.25F);
			
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		    GlStateManager.depthMask(true);
		    int i = 61680;
		    int j = i % 65536;
		    int k = i / 65536;
		    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		    Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

		    this.pigModel.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, phyg);

		    this.modelHalo.setModelAttributes(this.pigModel);
			this.renderManager.renderEngine.bindTexture(TEXTURE_HALO);

			this.modelHalo.halo1.rotationPointX = this.pigModel.head.rotationPointX;
			this.modelHalo.halo1.rotationPointY = this.pigModel.head.rotationPointY + 3;
			this.modelHalo.halo1.rotationPointZ = this.pigModel.head.rotationPointZ - 4F;
			
			this.modelHalo.halo2.rotationPointX = this.pigModel.head.rotationPointX;
			this.modelHalo.halo2.rotationPointY = this.pigModel.head.rotationPointY + 3;
			this.modelHalo.halo2.rotationPointZ = this.pigModel.head.rotationPointZ - 4F;
			
			this.modelHalo.halo3.rotationPointX = this.pigModel.head.rotationPointX;
			this.modelHalo.halo3.rotationPointY = this.pigModel.head.rotationPointY + 3;
			this.modelHalo.halo3.rotationPointZ = this.pigModel.head.rotationPointZ - 4F;
			
			this.modelHalo.halo4.rotationPointX = this.pigModel.head.rotationPointX;
			this.modelHalo.halo4.rotationPointY = this.pigModel.head.rotationPointY + 3;
			this.modelHalo.halo4.rotationPointZ = this.pigModel.head.rotationPointZ - 4F;
			
			this.modelHalo.halo1.rotateAngleX = this.pigModel.head.rotateAngleX;
			this.modelHalo.halo1.rotateAngleY = this.pigModel.head.rotateAngleY;
			this.modelHalo.halo1.rotateAngleZ = this.pigModel.head.rotateAngleZ;
			
			this.modelHalo.halo2.rotateAngleX = this.pigModel.head.rotateAngleX;
			this.modelHalo.halo2.rotateAngleY = this.pigModel.head.rotateAngleY;
			this.modelHalo.halo2.rotateAngleZ = this.pigModel.head.rotateAngleZ;
			
			this.modelHalo.halo3.rotateAngleX = this.pigModel.head.rotateAngleX;
			this.modelHalo.halo3.rotateAngleY = this.pigModel.head.rotateAngleY;
			this.modelHalo.halo3.rotateAngleZ = this.pigModel.head.rotateAngleZ;
			
			this.modelHalo.halo4.rotateAngleX = this.pigModel.head.rotateAngleX;
	  	  	this.modelHalo.halo4.rotateAngleY = this.pigModel.head.rotateAngleY;
	  	  	this.modelHalo.halo4.rotateAngleZ = this.pigModel.head.rotateAngleZ;
	  	  	
			this.modelHalo.halo1.render(scale);
			this.modelHalo.halo2.render(scale);
			this.modelHalo.halo3.render(scale);
			this.modelHalo.halo4.render(scale);

			GlStateManager.disableBlend();
			GL11.glDisable(GL11.GL_BLEND);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}