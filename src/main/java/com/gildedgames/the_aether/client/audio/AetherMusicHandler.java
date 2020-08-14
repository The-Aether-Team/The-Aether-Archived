package com.gildedgames.the_aether.client.audio;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;

import com.gildedgames.the_aether.client.audio.music.AetherMusicTicker;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherMusicHandler {

	private Minecraft mc = Minecraft.getMinecraft();

	private final AetherMusicTicker musicTicker = new AetherMusicTicker(this.mc);

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception {
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;

		if (phase == TickEvent.Phase.END) {
			if (type.equals(TickEvent.Type.CLIENT)) {
				if (!this.mc.isGamePaused()) {
					if (!musicTicker.playingRecord()) {
						this.musicTicker.update();
					}
				}
			}
		}

		if (!(mc.getSoundHandler().isSoundPlaying(musicTicker.getRecord())))
		{
			musicTicker.trackRecord(null);
		}

		if (AetherConfig.config.get("Misc", "Enables the Aether Menu", false).getBoolean() && Minecraft.getMinecraft().theWorld == null && !(screen instanceof GuiScreenWorking))
		{
			if (!musicTicker.playingMenuMusic())
			{
				musicTicker.playMenuMusic();
			}

			if (musicTicker.playingMinecraftMusic())
			{
				musicTicker.stopMinecraftMusic();
			}
		}
		else
		{
			musicTicker.stopMenuMusic();
		}
	}

	@SubscribeEvent
	public void onMusicControl(PlaySoundEvent17 event) {
		ISound sound = event.result;
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;

		if (sound == null) {
			return;
		}

		SoundCategory category = event.category;

		if (category == SoundCategory.MUSIC) {
			if (this.mc.thePlayer != null && this.mc.thePlayer.dimension == AetherConfig.getAetherDimensionID()) {
				if (!sound.getPositionedSoundLocation().toString().contains("aether_legacy") && (this.musicTicker.playingMusic() || !this.musicTicker.playingMusic())) {
					event.result = null;

					return;
				}
			}
			if (sound.getPositionedSoundLocation().toString().equals("minecraft:music.menu"))
			{
				musicTicker.trackMinecraftMusic(sound);

				if (AetherConfig.config.get("Misc", "Enables the Aether Menu", false).getBoolean() && Minecraft.getMinecraft().theWorld == null && !(screen instanceof GuiScreenWorking))
				{
					event.result = null;
				}
			}
		} else if (category == SoundCategory.RECORDS && !(event.name.contains("note"))) {
			this.musicTicker.trackRecord(event.sound);
			this.mc.getSoundHandler().stopSounds();

			return;
		}
	}

	@SideOnly(Side.CLIENT)
	public static ISound getAchievementSound(int number) {
		ResourceLocation sound = number == 1 ? Aether.locate("achievement_bronze") : number == 2 ? Aether.locate("achievement_silver") : Aether.locate("achievement");

		return PositionedSoundRecord.func_147673_a(sound);
	}

}