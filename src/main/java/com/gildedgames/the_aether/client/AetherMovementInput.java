package com.gildedgames.the_aether.client;

import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import com.gildedgames.the_aether.entities.util.EntityMountable;
import com.gildedgames.the_aether.entities.util.EntitySaddleMount;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketSendSneaking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

public class AetherMovementInput extends MovementInputFromOptions {

	private Minecraft mc;

	private GameSettings gameSettings;

	private boolean currentSneak;

	private boolean previousSneak;

	public AetherMovementInput(Minecraft mc, GameSettings gameSettings) {
		super(gameSettings);

		this.mc = mc;
		this.gameSettings = gameSettings;
	}

	@Override
	public void updatePlayerMoveState() {
		super.updatePlayerMoveState();

		this.jump = this.gameSettings.keyBindJump.getIsKeyPressed();

		boolean isSneaking = this.gameSettings.keyBindSneak.getIsKeyPressed();

		this.sneak = isSneaking;

		if (this.mc.thePlayer == null) {
			return;
		}

		if (this.mc.thePlayer.ridingEntity instanceof EntitySaddleMount) {
			if (this.mc.thePlayer.ridingEntity instanceof EntitySaddleMount) {
				this.sneak = false;
				this.currentSneak = isSneaking;

				if (this.previousSneak != this.currentSneak) {
					AetherNetwork.sendToServer(new PacketSendSneaking(this.mc.thePlayer.getEntityId(), this.currentSneak));
					this.previousSneak = this.currentSneak;
				}

				if (((EntityMountable) this.mc.thePlayer.ridingEntity).isOnGround()) {
					this.sneak = isSneaking;
				}
			}
		} else if (this.mc.thePlayer.ridingEntity instanceof EntitySwet && !((EntitySwet) this.mc.thePlayer.ridingEntity).isFriendly()) {
			this.sneak = false;
		}
	}

}