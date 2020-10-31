package com.gildedgames.the_aether.client.renders.entities.layer;

import c4.colytra.util.ColytraUtil;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.client.models.attachments.ModelAetherWings;
import com.gildedgames.the_aether.client.models.attachments.ModelPlayerHalo;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;
import com.gildedgames.the_aether.items.accessories.ItemAccessoryDyable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;

public class AccessoriesLayer implements LayerRenderer<AbstractClientPlayer>
{

	private final RenderManager manager = Minecraft.getMinecraft().getRenderManager();

	private final ResourceLocation TEXTURE_VALKYRIE = new ResourceLocation("aether_legacy", "textures/entities/valkyrie/valkyrie.png");

	private final ResourceLocation TEXTURE_HALO = new ResourceLocation("aether_legacy", "textures/other/halo.png");

	private boolean slimFit;

	private ModelPlayerHalo modelHalo;

	public ModelBiped modelMisc;

	public ModelPlayer modelPlayer;

	public ModelPlayer modelGlow;
	
	private ModelAetherWings modelWings;

	public AccessoriesLayer(ModelPlayer modelPlayer)
	{
		this.modelPlayer = modelPlayer;
		this.modelWings = new ModelAetherWings(0.0F);
		this.modelMisc = new ModelBiped(1.0F);
		this.modelHalo = new ModelPlayerHalo();
		this.modelGlow = new ModelPlayer(0.7F, slimFit);
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float prevLimbSwing, float partialTicks, float rotation, float interpolateRotation, float prevRotationPitch, float scale)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);
		IAccessoryInventory accessories = playerAether.getAccessoryInventory();

		if (accessories == null)
		{
			return;
		}

		this.slimFit = player.getSkinType().equals("slim");

		GlStateManager.pushMatrix();

		this.modelMisc.setModelAttributes(this.modelPlayer);
		this.modelWings.setModelAttributes(this.modelPlayer);
		this.modelGlow.setModelAttributes(this.modelPlayer);

		this.modelMisc.setLivingAnimations(player, prevLimbSwing, rotation, partialTicks);
		this.modelGlow.setLivingAnimations(player, prevLimbSwing, rotation, partialTicks);
		this.modelWings.setLivingAnimations(player, prevLimbSwing, rotation, partialTicks);
		
		this.modelGlow.setRotationAngles(limbSwing, prevLimbSwing, partialTicks, interpolateRotation, prevRotationPitch, scale, player);

        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);

        if (this.slimFit)
        {
        	GlStateManager.translate(0.0D, 0.024D, 0.0D);
        }

        GlStateManager.enableAlpha();

        GlStateManager.translate(0, player.isSneaking() ? 0.25D : 0, 0);

		this.modelMisc.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);
		this.modelWings.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);
		this.modelHalo.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);

		this.modelPlayer.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);

		if (accessories.getStackInSlot(0).getItem() instanceof ItemAccessory)
		{
			ItemAccessory pendant = ((ItemAccessory) (accessories.getStackInSlot(0).getItem()));

			this.manager.renderEngine.bindTexture(pendant.texture);

			int colour = pendant.getColorFromItemStack(new ItemStack(pendant, 1, 0), 1);
			float red = ((colour >> 16) & 0xff) / 255F;
			float green = ((colour >> 8) & 0xff) / 255F;
			float blue = (colour & 0xff) / 255F;

			if (player.hurtTime > 0)
			{
				GlStateManager.color(1.0F, 0.5F, 0.5F);
			}
			else
			{
				GlStateManager.color(red, green, blue);
			}

			this.modelMisc.bipedBody.render(scale);

			GlStateManager.color(1.0F, 1.0F, 1.0F);
		}

		if (accessories.getStackInSlot(1).getItem() instanceof ItemAccessory && accessories.getStackInSlot(1).getItem() != ItemsAether.invisibility_cape)
		{
			ItemAccessory cape = ((ItemAccessory) (accessories.getStackInSlot(1).getItem()));
	        if (player.hasPlayerInfo() && !player.isInvisible())
	        {
				ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

				if (((PlayerAether) playerAether).shouldRenderCape)
				{
					if (itemstack.getItem() != Items.ELYTRA)
					{
						boolean flag = true;
						if (Loader.isModLoaded("colytra"))
						{
							ItemStack colytra = ColytraUtil.wornElytra(player);
							flag = colytra.isEmpty();
						}

						if (flag)
						{
							GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
							this.manager.renderEngine.bindTexture(cape.texture);
							GlStateManager.pushMatrix();
							int colour = cape.getColorFromItemStack(accessories.getStackInSlot(1), 0);
							float red = ((colour >> 16) & 0xff) / 255F;
							float green = ((colour >> 8) & 0xff) / 255F;
							float blue = (colour & 0xff) / 255F;
							if (player.hurtTime > 0)
							{
								GlStateManager.color(1.0F, 0.5F, 0.5F);
							}
							else
							{
								GlStateManager.color(red, green, blue);
							}
							GlStateManager.scale(1.0625F, 1.0625F, 1.0625F);
							GlStateManager.translate(0, player.isSneaking() ? -0.25D : 0, 0);
							GlStateManager.translate(0.0F, 0.0F, 0.125F);
							double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double)partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks);
							double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double)partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks);
							double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double)partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks);
							float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
							double d3 = (double)MathHelper.sin(f * 0.017453292F);
							double d4 = (double)(-MathHelper.cos(f * 0.017453292F));
							float f1 = (float)d1 * 10.0F;
							f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
							float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
							float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
							if (f2 < 0.0F) {
								f2 = 0.0F;
							}

							float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
							f1 += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
							if (player.isSneaking()) {
								f1 += 25.0F;
							}

							GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
							GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
							GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
							GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
							this.modelPlayer.renderCape(0.0625F);
							GlStateManager.color(1.0F, 1.0F, 1.0F);
							GlStateManager.popMatrix();
						}
					}
				}
	        }
		}

		if (accessories.getStackInSlot(6).getItem().getClass() == ItemAccessory.class && ((PlayerAether) playerAether).shouldRenderGloves)
		{
			ItemAccessory gloves = (ItemAccessory) accessories.getStackInSlot(6).getItem();
			this.manager.renderEngine.bindTexture(gloves.texture);

			int colour = gloves.getColorFromItemStack(accessories.getStackInSlot(6), 0);

			float red = ((colour >> 16) & 0xff) / 255F;
			float green = ((colour >> 8) & 0xff) / 255F;
			float blue = (colour & 0xff) / 255F;

			if (player.hurtTime > 0)
			{
				GlStateManager.color(1.0F, 0.5F, 0.5F);
			}
			else
			{
				if (gloves != ItemsAether.phoenix_gloves)
				{
					GlStateManager.color(red, green, blue);
				}
			}

			this.modelMisc.bipedLeftArm.render(scale);
			this.modelMisc.bipedRightArm.render(scale);

			GlStateManager.color(1.0F, 1.0F, 1.0F);
		}
		else if (accessories.getStackInSlot(6).getItem().getClass() == ItemAccessoryDyable.class && ((PlayerAether) playerAether).shouldRenderGloves)
		{
			ItemAccessoryDyable gloves = (ItemAccessoryDyable) accessories.getStackInSlot(6).getItem();
			this.manager.renderEngine.bindTexture(gloves.texture);

			int colour = gloves.getColor(accessories.getStackInSlot(6));

			float red = ((colour >> 16) & 0xff) / 255F;
			float green = ((colour >> 8) & 0xff) / 255F;
			float blue = (colour & 0xff) / 255F;

			if (player.hurtTime > 0)
			{
				GlStateManager.color(1.0F, 0.5F, 0.5F);
			}
			else
			{
				GlStateManager.color(red, green, blue);
			}

			this.modelMisc.bipedLeftArm.render(scale);
			this.modelMisc.bipedRightArm.render(scale);

			GlStateManager.color(1.0F, 1.0F, 1.0F);
		}

		if (accessories.getStackInSlot(2).getItem() instanceof ItemAccessory)
		{
			ItemAccessory shield = (ItemAccessory) accessories.getStackInSlot(2).getItem();

			if (player.motionX == 0.0 && (player.motionY == -0.0784000015258789 || player.motionY == 0.0) && player.motionZ == 0.0 && shield.hasInactiveTexture())
			{
				this.manager.renderEngine.bindTexture(shield.texture);
			}
			else
			{
				this.manager.renderEngine.bindTexture(shield.texture_inactive);
			}

			GlStateManager.enableBlend();
			GlStateManager.pushMatrix();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.scale(1.1, 1.1, 1.1);
			if (player.hurtTime > 0)
			{
				GlStateManager.color(1.0F, 0.5F, 0.5F);
			}
			GlStateManager.depthMask(true);
			this.modelMisc.bipedHead.render(scale);
			this.modelMisc.bipedBody.render(scale);
			this.modelMisc.bipedLeftArm.render(scale);
			this.modelMisc.bipedRightArm.render(scale);
			this.modelMisc.bipedLeftLeg.render(scale);
			this.modelMisc.bipedRightLeg.render(scale);

			GlStateManager.color(1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}

		if (playerAether.getAccessoryInventory().isWearingValkyrieSet())
		{
			GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.manager.renderEngine.bindTexture(TEXTURE_VALKYRIE);
            GlStateManager.disableBlend();
			this.modelWings.setWingSinage(((PlayerAether)playerAether).wingSinage);
			this.modelWings.wingLeft.render(scale);
			this.modelWings.wingRight.render(scale);

			if (player.hurtResistantTime > 0)
			{
				GlStateManager.color(1.0F, 0.5F, 0.5F);
			}
			else
			{
				GlStateManager.color(1.0F, 1.0F, 1.0F);
			}
		}

		if (AetherRankings.isRankedPlayer(player.getUniqueID()) && ((PlayerAether)playerAether).shouldRenderHalo && !player.isInvisible()) //TODO
		{
			GlStateManager.pushMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.scale(1.1F, 1.1F, 1.1F);

		    GlStateManager.depthMask(true);
		    int i = 61680;
		    int j = 240;
		    int k = 240;
		    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		    Minecraft.getMinecraft().entityRenderer.setupFogColor(false);

			this.manager.renderEngine.bindTexture(TEXTURE_HALO);

			this.modelHalo.halo1.render(scale);
			this.modelHalo.halo2.render(scale);
			this.modelHalo.halo3.render(scale);
			this.modelHalo.halo4.render(scale);
			
			this.modelHalo.halo1.rotateAngleX = this.modelMisc.bipedHead.rotateAngleX;
			this.modelHalo.halo1.rotateAngleY = this.modelMisc.bipedHead.rotateAngleY;
			this.modelHalo.halo2.rotateAngleX = this.modelMisc.bipedHead.rotateAngleX;
			this.modelHalo.halo2.rotateAngleY = this.modelMisc.bipedHead.rotateAngleY;
			this.modelHalo.halo3.rotateAngleX = this.modelMisc.bipedHead.rotateAngleX;
			this.modelHalo.halo3.rotateAngleY = this.modelMisc.bipedHead.rotateAngleY;
			this.modelHalo.halo4.rotateAngleX = this.modelMisc.bipedHead.rotateAngleX;
	  	  	this.modelHalo.halo4.rotateAngleY = this.modelMisc.bipedHead.rotateAngleY;

	        i = player.getBrightnessForRender();
	        j = i % 65536;
	        k = i / 65536;
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
			GlStateManager.disableBlend();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}

		if ((player.getUniqueID().toString().equals("cf51ef47-04a8-439a-aa41-47d871b0b837") || AetherRankings.isDeveloper(player.getUniqueID()) && ((PlayerAether)playerAether).shouldRenderGlow) && !player.isInvisible())
		{
			this.manager.renderEngine.bindTexture(player.getLocationSkin());
			GlStateManager.enableBlend();
			GlStateManager.pushMatrix();
			
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        GlStateManager.enableNormalize();
	        GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

	        this.modelGlow.render(player, limbSwing, prevLimbSwing, partialTicks, interpolateRotation, prevRotationPitch, scale);

	        GlStateManager.disableBlend();
	        GlStateManager.disableNormalize();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
			//glow
		}
		
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.disableBlend();
		GlStateManager.disableRescaleNormal();

		GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return true;
	}
}