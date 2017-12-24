package com.legacy.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class PacketSetTime extends AetherPacket<PacketSetTime>
{

	public float timeVariable;

	public int dimensionId;

	public PacketSetTime()
	{
		
	}

	public PacketSetTime(float timeVariable, int dimensionId)
	{
		this.dimensionId = dimensionId;
		this.timeVariable = timeVariable;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.dimensionId = buf.readInt();
		this.timeVariable = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.dimensionId);
		buf.writeFloat(this.timeVariable);
	}

	@Override
	public void handleClient(PacketSetTime message, EntityPlayer player)
	{

	}

	@Override
	public void handleServer(PacketSetTime message, EntityPlayer player) 
	{
		message.setTime(message.timeVariable, message.dimensionId);
	}

    public void setTime(float sliderValue, int dimension)
    {
    	WorldServer world = DimensionManager.getWorld(dimension);
 
    	long shouldTime = (long)(24000L * sliderValue);
    	long worldTime = world.getWorldInfo().getWorldTime();
    	long remainder = worldTime % 24000L;
    	long add = shouldTime > remainder ? shouldTime - remainder : shouldTime + 24000 - remainder;

    	world.getWorldInfo().setWorldTime(worldTime + add);
    }

}