package com.legacy.aether.api.player;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.legacy.aether.api.player.util.IAccessoryInventory;
import com.legacy.aether.api.player.util.IAetherAbility;
import com.legacy.aether.api.player.util.IAetherBoss;

public interface IPlayerAether extends IExtendedEntityProperties {

	public void onUpdate();

	public void setInPortal();

	public void setFocusedBoss(IAetherBoss boss);

	public IAetherBoss getFocusedBoss();

	public void setAccessoryInventory(IAccessoryInventory inventory);

	public IAccessoryInventory getAccessoryInventory();

	public ArrayList<IAetherAbility> getAbilities();

	public EntityLivingBase getEntity();

	public boolean setHammerCooldown(int cooldown, String hammerName);

	public String getHammerName();

	public int getHammerCooldown();

	public int getHammerMaxCooldown();

	public void setJumping(boolean isJumping);

	public boolean isJumping();

	public void setMountSneaking(boolean isSneaking);

	public boolean isMountSneaking();

	public void updateShardCount(int amount);

	public int getShardsUsed();

	public int getMaxShardCount();

	public boolean isDonator();

}