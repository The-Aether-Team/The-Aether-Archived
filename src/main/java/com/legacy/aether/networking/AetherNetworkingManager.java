package com.legacy.aether.networking;

import com.legacy.aether.networking.packets.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.Aether;

public class AetherNetworkingManager
{

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Aether.modid);

	private static int discriminant;

	public static void preInitialization()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Aether.modid, new AetherGuiHandler());

		INSTANCE.registerMessage(PacketOpenContainer.class, PacketOpenContainer.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketAccessory.class, PacketAccessory.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSendJump.class, PacketSendJump.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketInitiateValkyrieFight.class, PacketInitiateValkyrieFight.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketDisplayDialogue.class, PacketDisplayDialogue.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketDialogueClicked.class, PacketDialogueClicked.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSetTime.class, PacketSetTime.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketSendTime.class, PacketSendTime.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSendEternalDay.class, PacketSendEternalDay.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSendShouldCycle.class, PacketSendShouldCycle.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketCapeChanged.class, PacketCapeChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketCapeChanged.class, PacketCapeChanged.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSendPoison.class, PacketSendPoison.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSendPoisonTime.class, PacketSendPoisonTime.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketExtendedAttack.class, PacketExtendedAttack.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketGlovesChanged.class, PacketGlovesChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketGlovesChanged.class, PacketGlovesChanged.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSendSeenDialogue.class, PacketSendSeenDialogue.class, discriminant++, Side.CLIENT);
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