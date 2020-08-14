package com.gildedgames.the_aether.network;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.network.packets.*;
import com.gildedgames.the_aether.network.packets.*;
import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherNetwork {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Aether.MOD_ID);

	private static int discriminant;

	public static void preInitialization() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Aether.MOD_ID, new AetherGuiHandler());

		INSTANCE.registerMessage(PacketOpenContainer.class, PacketOpenContainer.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketAccessory.class, PacketAccessory.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketAchievement.class, PacketAchievement.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSendPoison.class, PacketSendPoison.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSendPoisonTime.class, PacketSendPoisonTime.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketInitiateValkyrieFight.class, PacketInitiateValkyrieFight.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketDisplayDialogue.class, PacketDisplayDialogue.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketDialogueClicked.class, PacketDialogueClicked.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketPerkChanged.class, PacketPerkChanged.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketSetTime.class, PacketSetTime.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketSendSneaking.class, PacketSendSneaking.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketSendEternalDay.class, PacketSendEternalDay.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSendShouldCycle.class, PacketSendShouldCycle.class, discriminant++, Side.CLIENT);
		INSTANCE.registerMessage(PacketSendTime.class, PacketSendTime.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketCapeChanged.class, PacketCapeChanged.class, discriminant++, Side.SERVER);
		INSTANCE.registerMessage(PacketCapeChanged.class, PacketCapeChanged.class, discriminant++, Side.CLIENT);

		INSTANCE.registerMessage(PacketExtendedAttack.class, PacketExtendedAttack.class, discriminant++, Side.SERVER);

		INSTANCE.registerMessage(PacketSendSeenDialogue.class, PacketSendSeenDialogue.class, discriminant++, Side.CLIENT);
	}

	public static void sendToAll(IMessage message) {
		INSTANCE.sendToAll(message);
	}

	@SideOnly(Side.CLIENT)
	public static void sendToServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}

	public static void sendTo(IMessage message, EntityPlayerMP player) {
		INSTANCE.sendTo(message, player);
	}

}