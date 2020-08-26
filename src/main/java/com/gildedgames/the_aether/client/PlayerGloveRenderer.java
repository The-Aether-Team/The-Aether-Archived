package com.gildedgames.the_aether.client;

import com.gildedgames.the_aether.items.accessories.ItemAccessoryDyable;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;

public class PlayerGloveRenderer
{

	private static ModelBiped gloveModel, slimGloveModel, hatGloveModel, slimHatGloveModel;

	private static boolean isSlim = false;

	public static void renderItemFirstPerson(AbstractClientPlayer player, float partialTicks, float interpPitch, EnumHand hand, float swingProgress, ItemStack stack, float equipProgress)
	{
		isSlim = ((AbstractClientPlayer)player).getSkinType().equals("slim") ? true : false;

		if (gloveModel == null && slimGloveModel == null && hatGloveModel == null && slimHatGloveModel == null)
		{
			gloveModel = new ModelBiped(0.01F);
			slimGloveModel = new ModelPlayer(0.01F, true);
            hatGloveModel = new ModelBiped(0.26F);
            slimHatGloveModel = new ModelPlayer(0.26F, true);
		}

        boolean flag = hand == EnumHand.MAIN_HAND;
        EnumHandSide enumhandside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();

    	GlStateManager.pushMatrix();

        if (stack.isEmpty())
        {
            if (flag && !player.isInvisible())
            {
                renderGloveFirstPerson(player, equipProgress, swingProgress, enumhandside);
            }
        }
        else if (stack.getItem() instanceof net.minecraft.item.ItemMap)
        {
            if (flag && player.getHeldItemOffhand().isEmpty())
            {
                renderMapFirstPerson(player, interpPitch, equipProgress, swingProgress);
            }
            else
            {
                renderMapFirstPersonSide(player, equipProgress, enumhandside, swingProgress);
            }
        }

        GlStateManager.popMatrix();
	}

	private static void renderGloves(AbstractClientPlayer player)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);
		ItemStack accessoryStack = playerAether.getAccessoryInventory().getStackInSlot(6);

		if (!accessoryStack.isEmpty() && accessoryStack.getItem() instanceof ItemAccessory && !player.isInvisible())
		{
            GlStateManager.disableCull();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            renderArm(playerAether, EnumHandSide.RIGHT, (ItemAccessory) accessoryStack.getItem());
            renderArm(playerAether, EnumHandSide.LEFT, (ItemAccessory) accessoryStack.getItem());
            GlStateManager.popMatrix();
            GlStateManager.enableCull();
        }
	}

    private static void renderArm(IPlayerAether playerAether, EnumHandSide hand, ItemAccessory gloves)
    {
        if (gloves.getClass() == ItemAccessory.class)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(!isSlim ? gloves.texture : gloves.texture_slim);
        }
        else if (gloves.getClass() == ItemAccessoryDyable.class)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(!isSlim ? ((ItemAccessoryDyable) gloves).texture : ((ItemAccessoryDyable) gloves).texture_slim);
        }

		int colour = gloves.getColorFromItemStack(playerAether.getAccessoryInventory().getStackInSlot(6), 0);
		if (gloves.getClass() == ItemAccessoryDyable.class)
        {
            colour = ((ItemAccessoryDyable) gloves).getColor(playerAether.getAccessoryInventory().getStackInSlot(6));
        }

		float red = ((colour >> 16) & 0xff) / 255F;
		float green = ((colour >> 8) & 0xff) / 255F;
		float blue = (colour & 0xff) / 255F;

        GlStateManager.pushMatrix();
 
		if (gloves != ItemsAether.phoenix_gloves)
		{
			GlStateManager.color(red, green, blue);
		}

        float f = hand == EnumHandSide.RIGHT ? 1.0F : -1.0F;
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f * -41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.3F, -1.1F, 0.45F);

        if (hand == EnumHandSide.RIGHT)
        {
        	renderRightGlove(playerAether, gloves);
        }
        else
        {
        	renderLeftArmGlove(playerAether, gloves);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    private static void renderMapFirstPerson(AbstractClientPlayer player, float interpPitch, float equipProgress, float swingProgress)
    {
    	GlStateManager.pushMatrix();
        float f = MathHelper.sqrt(swingProgress);
        float f1 = -0.2F * MathHelper.sin(swingProgress * (float)Math.PI);
        float f2 = -0.4F * MathHelper.sin(f * (float)Math.PI);
        GlStateManager.translate(0.0F, -f1 / 2.0F, f2);
        float f3 = getMapAngleFromPitch(interpPitch);
        GlStateManager.translate(0.0F, 0.04F + equipProgress * -1.2F + f3 * -0.5F, -0.72F);
        GlStateManager.rotate(f3 * -85.0F, 1.0F, 0.0F, 0.0F);
        renderGloves(player);
        GlStateManager.popMatrix();
    }

    private static void renderMapFirstPersonSide(AbstractClientPlayer player, float equipProgress, EnumHandSide enumhandside, float swingProgress)
    {
        float f = enumhandside == EnumHandSide.RIGHT ? 1.0F : -1.0F;

        if (!player.isInvisible())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(f * 0.125F, -0.125F, 0.0F);
            GlStateManager.rotate(f * 10.0F, 0.0F, 0.0F, 1.0F);
            renderGloveFirstPerson(player, equipProgress, swingProgress, enumhandside);
            GlStateManager.popMatrix();
        }
    }

	private static void renderGloveFirstPerson(AbstractClientPlayer player, float equipProgress, float swingProgress, EnumHandSide enumhandside) 
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);
		ItemStack accessoryStack = playerAether.getAccessoryInventory().getStackInSlot(6);

		if (!accessoryStack.isEmpty() && accessoryStack.getItem() instanceof ItemAccessory)
		{
	        boolean flag = enumhandside != EnumHandSide.LEFT;
	        float f = flag ? 1.0F : -1.0F;
	        float f1 = MathHelper.sqrt(swingProgress);
	        float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
	        float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
	        float f4 = -0.4F * MathHelper.sin(swingProgress * (float)Math.PI);
	        GlStateManager.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);
	        GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
	        float f5 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
	        float f6 = MathHelper.sin(f1 * (float)Math.PI);
	        GlStateManager.rotate(f * f6 * 70.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(f * f5 * -20.0F, 0.0F, 0.0F, 1.0F);

	        GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
	        GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
	        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
	        GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);

	        GlStateManager.disableCull();

	        if (flag)
	        {
	        	renderRightGlove(playerAether, (ItemAccessory) accessoryStack.getItem());
	        }
	        else
	        {
	        	renderLeftArmGlove(playerAether, (ItemAccessory) accessoryStack.getItem());
	        }

	        GlStateManager.enableCull();
		}
	}

	private static void renderRightGlove(IPlayerAether playerAether, ItemAccessory gloves)
    {
        if (gloves.getClass() == ItemAccessory.class)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(!isSlim ? gloves.texture : gloves.texture_slim);
        }
        else if (gloves.getClass() == ItemAccessoryDyable.class)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(!isSlim ? ((ItemAccessoryDyable) gloves).texture : ((ItemAccessoryDyable) gloves).texture_slim);
        }

		int colour = gloves.getColorFromItemStack(playerAether.getAccessoryInventory().getStackInSlot(6), 0);
        if (gloves.getClass() == ItemAccessoryDyable.class)
        {
            colour = ((ItemAccessoryDyable) gloves).getColor(playerAether.getAccessoryInventory().getStackInSlot(6));
        }

		float red = ((colour >> 16) & 0xff) / 255F;
		float green = ((colour >> 8) & 0xff) / 255F;
		float blue = (colour & 0xff) / 255F;

		if (gloves != ItemsAether.phoenix_gloves)
		{
			GlStateManager.color(red, green, blue);
		}

        GlStateManager.enableBlend();
		if (!((PlayerAether) playerAether).gloveSize)
        {
            getModel().swingProgress = 0.0F;
            getModel().isSneak = false;
            getModel().setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerAether.getEntity());
            getModel().bipedRightArm.rotateAngleX = 0.0F;
            getModel().bipedRightArm.render(0.0625F);
        }
		else
        {
            getHatModel().swingProgress = 0.0F;
            getHatModel().isSneak = false;
            getHatModel().setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerAether.getEntity());
            getHatModel().bipedRightArm.rotateAngleX = 0.0F;
            getHatModel().bipedRightArm.render(0.0625F);
            System.out.println(true);
        }
        GlStateManager.disableBlend();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

	private static void renderLeftArmGlove(IPlayerAether playerAether, ItemAccessory gloves)
    {
        if (gloves.getClass() == ItemAccessory.class)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(!isSlim ? gloves.texture : gloves.texture_slim);
        }
        else if (gloves.getClass() == ItemAccessoryDyable.class)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(!isSlim ? ((ItemAccessoryDyable) gloves).texture : ((ItemAccessoryDyable) gloves).texture_slim);
        }

		int colour = gloves.getColorFromItemStack(playerAether.getAccessoryInventory().getStackInSlot(6), 0);
        if (gloves.getClass() == ItemAccessoryDyable.class)
        {
            colour = ((ItemAccessoryDyable) gloves).getColor(playerAether.getAccessoryInventory().getStackInSlot(6));
        }

		float red = ((colour >> 16) & 0xff) / 255F;
		float green = ((colour >> 8) & 0xff) / 255F;
		float blue = (colour & 0xff) / 255F;

		if (gloves != ItemsAether.phoenix_gloves)
		{
			GlStateManager.color(red, green, blue);
		}

        GlStateManager.enableBlend();
        if (!((PlayerAether) playerAether).gloveSize)
        {
            getModel().isSneak = false;
            getModel().swingProgress = 0.0F;
            getModel().setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerAether.getEntity());
            getModel().bipedLeftArm.rotateAngleX = 0.0F;
            getModel().bipedLeftArm.render(0.0625F);
        }
        else
        {
            getHatModel().isSneak = false;
            getHatModel().swingProgress = 0.0F;
            getHatModel().setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerAether.getEntity());
            getHatModel().bipedLeftArm.rotateAngleX = 0.0F;
            getHatModel().bipedLeftArm.render(0.0625F);
            System.out.println(true);
        }
        GlStateManager.disableBlend();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

	private static ModelBiped getModel()
	{
	    return isSlim ? slimGloveModel : gloveModel;
	}

	private static ModelBiped getHatModel()
    {
        return isSlim ? slimHatGloveModel : hatGloveModel;
    }

    private static float getMapAngleFromPitch(float pitch)
    {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = -MathHelper.cos(f * (float)Math.PI) * 0.5F + 0.5F;

        return f;
    }

}