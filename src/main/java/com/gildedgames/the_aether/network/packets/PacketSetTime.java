package com.gildedgames.the_aether.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;

public class PacketSetTime extends AetherPacket<PacketSetTime> {

	public float timeVariable;

	public int dimensionId;

	public PacketSetTime() {

	}

	public PacketSetTime(float timeVariable, int dimensionId) {
		this.dimensionId = dimensionId;
		this.timeVariable = timeVariable;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.dimensionId = buf.readInt();
		this.timeVariable = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.dimensionId);
		buf.writeFloat(this.timeVariable);
	}

	@Override
	public void handleClient(PacketSetTime message, EntityPlayer player) {

	}

	@Override
	public void handleServer(PacketSetTime message, EntityPlayer player) {
		message.setTime(message.timeVariable, message.dimensionId);
	}

	public void setTime(float sliderValue, int dimension) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		for (int i = 0; i < server.worldServers.length; ++i) {
			long shouldTime = (long) (24000L * sliderValue);
			long worldTime = server.worldServers[i].getWorldInfo().getWorldTime();
			long remainder = worldTime % 24000L;
			long add = shouldTime > remainder ? shouldTime - remainder : shouldTime + 24000 - remainder;

			server.worldServers[i].setWorldTime(worldTime + add);
		}
	}

}