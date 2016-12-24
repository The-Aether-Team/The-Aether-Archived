package com.legacy.aether.server.entities.projectile.darts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.networking.AetherNetworkingManager;
import com.legacy.aether.server.networking.packets.PacketSendPoison;
import com.legacy.aether.server.player.PlayerAether;

public class EntityDartPoison extends EntityDartBase
{

    public EntityDartPoison(World worldIn)
    {
        super(worldIn);
    }

    public EntityDartPoison(World world, EntityLivingBase entity) 
    {
		super(world, entity);
	}

    public void entityInit()
    {
        super.entityInit();
        this.setDamage(0);
    }

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
    	super.arrowHit(living);

    	if (living instanceof EntityPlayer)
    	{
            EntityPlayer ent = (EntityPlayer)living;
            PlayerAether player = PlayerAether.get(ent);

            if (!player.isPoisoned())
            {
                if (!this.worldObj.isRemote)
                {
                	PlayerAether.get(ent).afflictPoison();
                	AetherNetworkingManager.sendToAll(new PacketSendPoison(ent));

                    this.setDead();
                }
            }
    	}
    }

	@Override
	protected ItemStack getArrowStack() 
	{
		return new ItemStack(ItemsAether.dart, 1, 1);
	}

}