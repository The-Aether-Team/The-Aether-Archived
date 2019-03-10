package com.legacy.aether.api.player;

import com.legacy.aether.api.player.util.IAetherBoss;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerAether
{

	void onUpdate();

	void writeAdditional(NBTTagCompound var1);

	void readAdditional(NBTTagCompound var1);

	EntityPlayer getEntity();

	void setFocusedBoss(IAetherBoss var1);

	IAetherBoss getFocusedBoss();

	void inflictPoison(int var1);

	boolean isPoisoned();

	void inflictCure(int var1);

	boolean isCured();

}