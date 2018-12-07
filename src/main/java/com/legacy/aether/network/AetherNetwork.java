package com.legacy.aether.network;

import net.minecraft.entity.player.EntityPlayerMP;

import com.legacy.aether.Aether;
import com.legacy.aether.network.packets.PacketAccessory;
import com.legacy.aether.network.packets.PacketAchievement;
import com.legacy.aether.network.packets.PacketDialogueClicked;
import com.legacy.aether.network.packets.PacketDisplayDialogue;
import com.legacy.aether.network.packets.PacketInitiateValkyrieFight;
import com.legacy.aether.network.packets.PacketOpenContainer;
import com.legacy.aether.network.packets.PacketPerkChanged;
import com.legacy.aether.network.packets.PacketSendPoison;
import com.legacy.aether.network.packets.PacketSendSneaking;
import com.legacy.aether.network.packets.PacketSetTime;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherNetwork 
{

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Aether.MOD_ID);

	private static int discriminant;

	public static void preInitialization()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Aether.MOD_ID, new AetherGuiHandler());

		INSTANCE.registerMessage(PacketOpenContainer.class, PacketOpenContainer.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketAccessory.class, PacketAccessory.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketAchievement.class, PacketAchievement.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSendPoison.class, PacketSendPoison.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketInitiateValkyrieFight.class, PacketInitiateValkyrieFight.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketDisplayDialogue.class, PacketDisplayDialogue.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketDialogueClicked.class, PacketDialogueClicked.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSetTime.class, PacketSetTime.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketSendSneaking.class, PacketSendSneaking.class, discriminant++, Side.SERVER);
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