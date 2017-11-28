package com.legacy.aether.networking;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.Aether;
import com.legacy.aether.networking.packets.PacketAccessory;
import com.legacy.aether.networking.packets.PacketAchievement;
import com.legacy.aether.networking.packets.PacketDialogueClicked;
import com.legacy.aether.networking.packets.PacketDisplayDialogue;
import com.legacy.aether.networking.packets.PacketGameType;
import com.legacy.aether.networking.packets.PacketInitiateValkyrieFight;
import com.legacy.aether.networking.packets.PacketOpenContainer;
import com.legacy.aether.networking.packets.PacketPerkChanged;
import com.legacy.aether.networking.packets.PacketSendPoison;

public class AetherNetworkingManager
{

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Aether.modid);

	private static int discriminant;

	public static void preInitialization()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Aether.modid, new AetherGuiHandler());

		INSTANCE.registerMessage(PacketOpenContainer.class, PacketOpenContainer.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketAccessory.class, PacketAccessory.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSendPoison.class, PacketSendPoison.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketGameType.class, PacketGameType.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketInitiateValkyrieFight.class, PacketInitiateValkyrieFight.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketDisplayDialogue.class, PacketDisplayDialogue.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketDialogueClicked.class, PacketDialogueClicked.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketAchievement.class, PacketAchievement.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.CLIENT);
	}

	public static void sendToAll(IMessage message)
	{
		INSTANCE.sendToAll(message);
	}

	@SideOnly(Side.CLIENT)
	public static void sendToServer(IMessage message)
	{
		INSTANCE.sendToServer(message);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player)
	{
		INSTANCE.sendTo(message, player);
	}
}