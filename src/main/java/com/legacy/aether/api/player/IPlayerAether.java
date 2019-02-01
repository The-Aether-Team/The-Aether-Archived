package com.legacy.aether.api.player;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.legacy.aether.api.player.util.IAccessoryInventory;
import com.legacy.aether.api.player.util.IAetherAbility;
import com.legacy.aether.api.player.util.IAetherBoss;

public interface IPlayerAether 
{

	public void onUpdate();

	public void setInPortal();

	public void saveNBTData(NBTTagCompound compound);

	public void loadNBTData(NBTTagCompound compound);

	public void setFocusedBoss(IAetherBoss boss);

	public IAetherBoss getFocusedBoss();

	public void setAccessoryInventory(IAccessoryInventory inventory);

	public IAccessoryInventory getAccessoryInventory();

	public ArrayList<IAetherAbility> getAbilities();

	public EntityPlayer getEntity();

	public void inflictPoison(int ticks);

	public boolean isPoisoned();

	public void inflictCure(int ticks);

	public boolean isCured();
	
	public boolean setHammerCooldown(int cooldown, String hammerName);

	public String getHammerName();

	public int getHammerCooldown();

	public int getHammerMaxCooldown();
	
	public void setJumping(boolean isJumping);

	public boolean isJumping();

	public void updateShardCount(int amount);

	public int getShardsUsed();

	public int getMaxShardCount();

	public boolean isDonator();

	public void shouldPortalSound(boolean playSound);

	public boolean shouldPortalSound();
	
	public boolean inPortalBlock();
	
}