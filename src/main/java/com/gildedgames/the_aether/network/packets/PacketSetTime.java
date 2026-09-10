package com.gildedgames.the_aether.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.world.AetherData;

import cpw.mods.fml.common.FMLCommonHandler;
import io.netty.buffer.ByteBuf;

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
        if (player == null) {
            return;
        }

        MinecraftServer server = FMLCommonHandler.instance()
            .getMinecraftServerInstance();

        if (server == null) {
            return;
        }

        // Only the Aether dimension may be changed, and only by players in it.
        if (message.dimensionId != AetherConfig.getAetherDimensionID()
            || player.dimension != AetherConfig.getAetherDimensionID()) {
            return;
        }

        // Time control must be unlocked by defeating the Sun Spirit (gold dungeon
        // boss): eternal day active and the cycle catch-up completed.
        if (AetherConfig.eternalDayDisabled()) {
            return;
        }

        AetherData data = AetherData.getInstance(player.worldObj);

        if (!data.isEternalDay() || !data.isShouldCycleCatchup()) {
            return;
        }

        // Same permission rules as the Sun Altar block: ops, or everyone when the
        // multiplayer config allows it (dedicated servers only restrict otherwise).
        boolean permitted = !server.isDedicatedServer() || server.getConfigurationManager()
            .func_152596_g(player.getGameProfile()) || AetherConfig.sunAltarMultiplayer();

        if (!permitted) {
            return;
        }

        this.setTime(message.timeVariable, message.dimensionId);
    }

    public void setTime(float sliderValue, int dimension) {
        MinecraftServer server = FMLCommonHandler.instance()
            .getMinecraftServerInstance();

        WorldServer aetherServer = server.worldServerForDimension(AetherConfig.getAetherDimensionID());

        if (aetherServer == null) {
            return;
        }

        long shouldTime = (long) (24000L * sliderValue);
        long worldTime = aetherServer.getWorldInfo()
            .getWorldTime();
        long remainder = worldTime % 24000L;
        long add = shouldTime > remainder ? shouldTime - remainder : shouldTime + 24000 - remainder;

        aetherServer.getWorldInfo()
            .setWorldTime(worldTime + add);
    }

}
