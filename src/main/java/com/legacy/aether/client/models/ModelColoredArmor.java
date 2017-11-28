package com.legacy.aether.client.models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.legacy.aether.items.armor.ItemAetherArmor;

public class ModelColoredArmor extends ModelBiped
{
	private Item item;

	private EntityEquipmentSlot armorType;

	public ModelColoredArmor(float size, Item item, EntityEquipmentSlot armorSlot)
	{
		super(size);
		this.item = item;
		this.armorType = armorSlot;
	}

	@Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
    	GlStateManager.pushMatrix();

    	if (!(entity instanceof EntityArmorStand))
    	{
		    this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
    	}

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;

	        if (player.isSneaking())
	        {
	        	GlStateManager.translate(0, 0.20F, 0);
	        }
		}

		this.isSneak = entity.isSneaking();

    	int color = ((ItemAetherArmor)this.item).getColorFromItemStack(new ItemStack(this.item), 0);
		float red = ((color >> 16) & 0xff) / 255F;
		float green = ((color >> 8) & 0xff) / 255F;
		float blue = (color & 0xff) / 255F;

		GlStateManager.color(red, green, blue);

		if (this.armorType == EntityEquipmentSlot.HEAD)
		{
            this.bipedHeadwear.render(scale);
            this.bipedHead.render(scale);
		}
		else if (this.armorType == EntityEquipmentSlot.CHEST)
		{
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
		}
		else if (this.armorType == EntityEquipmentSlot.LEGS)
		{
			this.bipedBody.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
		}
		else if (this.armorType == EntityEquipmentSlot.FEET)
		{
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
    }
}