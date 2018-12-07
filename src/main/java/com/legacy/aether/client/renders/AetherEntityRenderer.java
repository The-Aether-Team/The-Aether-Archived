package com.legacy.aether.client.renders;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import com.legacy.aether.items.tools.ItemValkyrieTool;

import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class AetherEntityRenderer extends EntityRenderer
{

    private Entity pointedEntity;

	private final Minecraft mc;

	private final EntityRenderer previous;

	public AetherEntityRenderer(Minecraft mc, EntityRenderer previous, IResourceManager manager) 
	{
		super(mc, manager);

		this.mc = mc;
		this.previous = previous;

		ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, this, new AetherItemRenderer(mc), 7);
	}

	@Override
    public void getMouseOver(float p_78473_1_)
    {
        if (this.mc.renderViewEntity != null)
        {
            if (this.mc.theWorld != null)
            {
            	ItemStack stack = this.mc.thePlayer.getCurrentEquippedItem();

            	if (stack == null || !(stack.getItem() instanceof ItemValkyrieTool))
            	{
            		this.previous.getMouseOver(p_78473_1_);
            		return;
            	}

                this.mc.pointedEntity = null;
                double d0 = 10.0D;
                this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d0, p_78473_1_);
                double d1 = d0;
                Vec3 vec3 = this.mc.renderViewEntity.getPosition(p_78473_1_);

                if (this.mc.playerController.extendedReach())
                {
                    d0 = 6.0D;
                    d1 = 6.0D;
                }
                else
                {
                    if (d0 > 3.0D)
                    {
                        d1 = 3.0D;
                    }

                    d0 = d1;
                }

                if (this.mc.objectMouseOver != null)
                {
                    d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = this.mc.renderViewEntity.getLook(p_78473_1_);
                Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                this.pointedEntity = null;
                Vec3 vec33 = null;
                float f1 = 1.0F;
                List<?> list = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
                double d2 = d1;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity = (Entity)list.get(i);

                    if (entity.canBeCollidedWith())
                    {
                        float f2 = entity.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.isVecInside(vec3))
                        {
                            if (0.0D < d2 || d2 == 0.0D)
                            {
                                this.pointedEntity = entity;
                                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                                d2 = 0.0D;
                            }
                        }
                        else if (movingobjectposition != null)
                        {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D)
                            {
                                if (entity == this.mc.renderViewEntity.ridingEntity && !entity.canRiderInteract())
                                {
                                    if (d2 == 0.0D)
                                    {
                                        this.pointedEntity = entity;
                                        vec33 = movingobjectposition.hitVec;
                                    }
                                }
                                else
                                {
                                    this.pointedEntity = entity;
                                    vec33 = movingobjectposition.hitVec;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null))
                {
                    this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);

                    if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame)
                    {
                        this.mc.pointedEntity = this.pointedEntity;
                    }
                }
            }
        }
    }

}