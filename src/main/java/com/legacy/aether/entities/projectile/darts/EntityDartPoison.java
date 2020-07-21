package com.legacy.aether.entities.projectile.darts;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.entities.effects.PotionsAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketSendPoison;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityDartPoison extends EntityDartBase
{

	public EntityLivingBase victim;

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

        if (!world.isRemote)
        {
            living.addPotionEffect(new PotionEffect(PotionsAether.INEBRIATION, 500, 0, false, false));
        }

    	this.isDead = false;
    }

	@Override
	protected ItemStack getArrowStack() 
	{
		return new ItemStack(ItemsAether.dart, 1, 1);
	}

}