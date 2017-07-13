package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import com.legacy.aether.client.models.attachments.ModelAetherWings;
import com.legacy.aether.client.models.attachments.ModelHalo;
import com.legacy.aether.common.containers.inventory.InventoryAccessories;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.accessories.ItemAccessory;
import com.legacy.aether.common.player.PlayerAether;
import com.legacy.aether.common.player.perks.AetherRankings;

public class AccessoriesLayer implements LayerRenderer<AbstractClientPlayer>
{

	private final RenderManager manager = Minecraft.getMinecraft().getRenderManager();

	private final ResourceLocation TEXTURE_VALKYRIE = new ResourceLocation("aether_legacy", "textures/entities/valkyrie/valkyrie.png");

	private final ResourceLocation TEXTURE_HALO = new ResourceLocation("aether_legacy", "textures/other/halo.png");

	private boolean slimFit;

	private ModelHalo modelHalo;

	public ModelBiped modelMisc;

	public ModelPlayer modelPlayer;

	private ModelAetherWings modelWings;

	public AccessoriesLayer(boolean slimFit, ModelPlayer modelPlayer)
	{
		this.modelPlayer = modelPlayer;
		this.slimFit = slimFit;
		this.modelWings = new ModelAetherWings(1.0F);
		this.modelMisc = new ModelBiped(1.0F);
		this.modelHalo = new ModelHalo();
	}

	@Override
	public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float prevLimbSwing, float partialTicks, float rotation, float interpolateRotation, float prevRotationPitch, float scale)
	{
		PlayerAether playerAether = PlayerAether.get(player);
		InventoryAccessories accessories = playerAether.accessories;

		if (accessories == null)
		{
			return;
		}

		GlStateManager.pushMatrix();

		this.modelMisc.setModelAttributes(this.modelPlayer);
		this.modelWings.setModelAttributes(this.modelPlayer);

		this.modelMisc.setLivingAnimations(player, prevLimbSwing, rotation, partialTicks);
		this.modelWings.setLivingAnimations(player, prevLimbSwing, rotation, partialTicks);

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

		if (accessories.stacks.get(0) != ItemStack.EMPTY)
		{
			ItemAccessory pendant = ((ItemAccessory) (accessories.stacks.get(0).getItem()));
			manager.renderEngine.bindTexture(pendant.texture);
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

		if (accessories.stacks.get(1) != ItemStack.EMPTY && accessories.stacks.get(1).getItem() != ItemsAether.invisibility_cape)
		{
			ItemAccessory cape = ((ItemAccessory) (accessories.stacks.get(1).getItem()));

	        if (player.hasPlayerInfo() && !player.isInvisible())
	        {
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.pushMatrix();
	            GlStateManager.translate(0.0F, 0.0F, 0.125F);
	            double d0 = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double)partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks);
	            double d1 = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double)partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks);
	            double d2 = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double)partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks);
	            float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
	            double d3 = (double)MathHelper.sin(f * (float)Math.PI / 180.0F);
	            double d4 = (double)(-MathHelper.cos(f * (float)Math.PI / 180.0F));
	            float f1 = (float)d1 * 10.0F;
	            f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
	            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
	            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;

	            if (f2 < 0.0F)
	            {
	                f2 = 0.0F;
	            }

	            float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
	            f1 = f1 + MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

	            if (player.isSneaking())
	            {
	                f1 += 25.0F;
	            }

	            GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
	            GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
	            GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
	            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

				int colour = cape.getColorFromItemStack(accessories.stacks.get(1), 0);

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

				if (player.getUniqueID().toString() == "47ec3a3b-3f41-49b6-b5a0-c39abb7b51ef")
				{
					manager.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/armor/accessory_swuff.png"));
				}
				else
				{
					manager.renderEngine.bindTexture(cape.texture);
				}

	            this.modelPlayer.renderCape(scale);
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.popMatrix();
	        }
		}

		if (accessories.stacks.get(6) != ItemStack.EMPTY)
		{
			ItemAccessory gloves = (ItemAccessory) accessories.stacks.get(6).getItem();
			this.manager.renderEngine.bindTexture(gloves.texture);

			int colour = gloves.getColorFromItemStack(accessories.stacks.get(6), 0);

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

		if (accessories.stacks.get(2) != ItemStack.EMPTY)
		{
			ItemAccessory shield = (ItemAccessory) accessories.stacks.get(2).getItem();

			this.manager.renderEngine.bindTexture(shield.texture);

			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.scale(1.1, 1.1, 1.1);
			if (player.hurtTime > 0)
			{
				GlStateManager.color(1.0F, 0.5F, 0.5F);
			}

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

		if (playerAether.isWearingValkyrieSet())
		{
			manager.renderEngine.bindTexture(TEXTURE_VALKYRIE);

			this.modelWings.setWingSinage(playerAether.wingSinage);
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

		if (AetherRankings.isRankedPlayer(player.getUniqueID()) && PlayerAether.get(player).shouldRenderHalo)
		{
			GlStateManager.pushMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			float var4 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) + partialTicks - (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) + partialTicks);
			float var5 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;

			GlStateManager.rotate(var4, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(var5, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0.0F, -0.8F, 0.0F);

			this.manager.renderEngine.bindTexture(TEXTURE_HALO);

			this.modelHalo.halo1.rotateAngleX = 6.25F;
			this.modelHalo.halo2.rotateAngleX = 6.25F;
			this.modelHalo.halo3.rotateAngleX = 6.25F;
			this.modelHalo.halo4.rotateAngleX = 6.25F;

			this.modelHalo.renderHalo(this.modelPlayer.bipedHead, scale);

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
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