package com.legacy.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.legacy.aether.containers.inventory.InventoryAccessories;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.accessories.ItemAccessory;
import com.legacy.aether.player.PlayerAether;

public class FirstPersonRenderer 
{

	private final TextureManager renderEngine = Minecraft.getMinecraft().getRenderManager().renderEngine;

	private Minecraft mc;

	public PlayerAether player;

	public EntityPlayer thePlayer;

	public ModelPlayer modelBase;

	public ModelBiped modelGlove;

	private float partialTickTime;

	private boolean isSlim;

	public FirstPersonRenderer(Minecraft mc, float tickTime)
	{
		this.mc = mc;
		this.thePlayer = mc.player;
		this.partialTickTime = tickTime;

		this.player = PlayerAether.get(mc.player);

        this.modelGlove = new ModelBiped(1.0F);
        this.isSlim = ((AbstractClientPlayer)this.thePlayer).getSkinType() == "slim" ? true : false;
		this.modelBase = new ModelPlayer(1.0F, this.isSlim);
	}

	public void render()
	{
		GlStateManager.pushMatrix();

		this.hurtCameraEffect(this.partialTickTime);

		this.setupWobbleEffect(this.partialTickTime);

        this.mc.entityRenderer.enableLightmap();
        float f5 = this.thePlayer.getSwingProgress(this.partialTickTime);
        float f6 = MathHelper.sin(f5 * (float)Math.PI);
        float f7 = MathHelper.sin(MathHelper.sqrt(f5) * (float)Math.PI);
        boolean flag = this.thePlayer.getPrimaryHand() == EnumHandSide.RIGHT ? true : false;
        GlStateManager.translate((flag ? -f7 : f7) * 0.3F, MathHelper.sin(MathHelper.sqrt(f5) * (float)Math.PI * 2.0F) * 0.4F, -f6 * 0.4F);
        GlStateManager.translate((flag ? 0.8F : -0.8F) * 0.8F, -0.75F * 0.8F - (0.1F) * 0.6F, -0.9F * 0.8F);
        if (!flag)
        {
            GlStateManager.translate(!this.isSlim ? 0.1 : 0.15, 0, 0);
        	GlStateManager.rotate(90.0F, 0.03F, 0.5F, -0.03F);
        }
        else
        {
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        }
		f5 = this.thePlayer.getSwingProgress(this.partialTickTime);
        f6 = MathHelper.sin(f5 * f5 * (float)Math.PI);
        f7 = MathHelper.sin(MathHelper.sqrt(f5) * (float)Math.PI);
        GlStateManager.rotate((flag ? f7 : -f7) * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((flag ? -f6 : f6) * 20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-1.0F, 3.6F, 3.5F);
        GlStateManager.rotate(120.0F, 0.0F, 0.02F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.02F, 0.02F);
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(5.6F, 0.02F, 0.0F);
        GlStateManager.scale(1F, 1F, 1F);
        this.setLightmap();
        this.armRender(flag);
        this.mc.entityRenderer.disableLightmap();
		GlStateManager.popMatrix();
	}

	private void setupWobbleEffect(float partialTickTime)
	{
        EntityPlayerSP entityplayersp = (EntityPlayerSP)this.thePlayer;
        float f3 = entityplayersp.prevRenderArmPitch + (entityplayersp.renderArmPitch - entityplayersp.prevRenderArmPitch) * partialTickTime;
        float f4 = entityplayersp.prevRenderArmYaw + (entityplayersp.renderArmYaw - entityplayersp.prevRenderArmYaw) * partialTickTime;
        GlStateManager.rotate((this.thePlayer.rotationPitch - f3) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((this.thePlayer.rotationYaw - f4) * 0.1F, 0.0F, 1.0F, 0.0F);
	}

    private void hurtCameraEffect(float p_78482_1_)
    {
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.mc.getRenderViewEntity();
        float f1 = (float)entitylivingbase.hurtTime - p_78482_1_;
        float f2;

        if (entitylivingbase.getHealth() <= 0.0F)
        {
            f2 = (float)entitylivingbase.deathTime + p_78482_1_;
            GlStateManager.rotate(40.0F - 8000.0F / (f2 + 200.0F), 0.0F, 0.0F, 1.0F);
        }

        if (f1 >= 0.0F)
        {
            f1 /= (float)entitylivingbase.maxHurtTime;
            f1 = MathHelper.sin(f1 * f1 * f1 * f1 * (float)Math.PI);
            f2 = entitylivingbase.attackedAtYaw;
            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-f1 * 14.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
        }
    }

    private void setLightmap()
    {
        AbstractClientPlayer abstractclientplayer = this.mc.player;
        int i = this.mc.world.getCombinedLight(new BlockPos(abstractclientplayer.posX, abstractclientplayer.posY + (double)abstractclientplayer.getEyeHeight(), abstractclientplayer.posZ), 0);
        float f = (float)(i & 65535);
        float f1 = (float)(i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

	public void armRender(boolean isLeft)
	{
		InventoryAccessories accessories = this.player.accessories;

		if (accessories == null || !this.shouldRender() || this.renderEngine == null)
		{
			return;
		}

		if (accessories.stacks.get(6) != ItemStack.EMPTY)
		{
			GlStateManager.pushMatrix();
			GlStateManager.enableDepth();
			GlStateManager.translate(-0.0F, 0, -0.05F);

			if (!this.isSlim)
			{
				GlStateManager.translate(0, 0, 0.15F);
			}
			else
			{
				GlStateManager.translate(0, 0, 0.15F);
			}

			this.modelGlove.setRotationAngles(0, 0, 0, 0, 0, 0, this.thePlayer);
            this.modelBase.setRotationAngles(0, 0, 0, 0, 0, 0, this.thePlayer);

            if (this.thePlayer instanceof AbstractClientPlayer)
            {
                this.renderEngine.bindTexture(((AbstractClientPlayer)this.thePlayer).getLocationSkin());
            }

            this.modelBase.bipedRightArm.render(this.isSlim ? 0.0625F : 0.0625F);

			if (accessories.stacks.get(6).getItem() instanceof ItemAccessory)
			{
				ItemAccessory glove = (ItemAccessory) accessories.stacks.get(6).getItem();

				this.renderEngine.bindTexture(glove.texture);

				int colour = glove.getColorFromItemStack(accessories.stacks.get(6), 0);
				float red = ((colour >> 16) & 0xff) / 255F;
				float green = ((colour >> 8) & 0xff) / 255F;
				float blue = (colour & 0xff) / 255F;

				if (glove != ItemsAether.phoenix_gloves)
				{
					GlStateManager.color(red, green, blue);
				}

	            GlStateManager.disableDepth();

	            if (this.isSlim)
	            {
	            	
	            	this.modelGlove.bipedRightArm.offsetX = 0.036F;
	            	this.modelGlove.bipedRightArm.offsetY = isLeft ? 0.11F : 0.12F;
	            	this.modelGlove.bipedRightArm.offsetZ = isLeft ? 0.02F : 0.008F;
	            }

	            this.modelGlove.bipedRightArm.render(this.isSlim ? 0.06F : 0.0625F);

				GlStateManager.enableDepth();
			}

			GlStateManager.color(1F, 1F, 1F);
			GlStateManager.popMatrix();
		}
	}

	public boolean shouldRender()
	{
		if (this.player.thePlayer.inventory.getCurrentItem() == ItemStack.EMPTY && this.player.accessories.stacks.get(6) != ItemStack.EMPTY && this.mc.gameSettings.thirdPersonView == 0 && !((EntityPlayer) this.mc.getRenderViewEntity()).isPlayerSleeping() && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectatorMode() && !FMLClientHandler.instance().hasOptifine())
		{
			return true;
		}

		return false;
	}

}