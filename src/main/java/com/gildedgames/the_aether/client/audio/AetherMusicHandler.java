package com.gildedgames.the_aether.client.audio;

import com.gildedgames.the_aether.client.audio.music.AetherMusicTicker;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.the_aether.AetherConfig;

public class AetherMusicHandler 
{
	private Minecraft mc = Minecraft.getMinecraft();

	private AetherMusicTicker musicTicker = new AetherMusicTicker(mc);

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception
	{
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				if (!mc.isGamePaused())
				{
					if (!musicTicker.playingRecord())
					{
						musicTicker.update();
					}
				}
			}
		}

		if (!(mc.getSoundHandler().isSoundPlaying(musicTicker.getRecord())))
		{
			musicTicker.trackRecord(null);
		}

		if (AetherConfig.visual_options.menu_enabled && Minecraft.getMinecraft().world == null && !(screen instanceof GuiScreenWorking))
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
	public void onMusicControl(PlaySoundEvent event)
	{
		ISound sound = event.getResultSound();
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;

		if (sound == null)
		{
			return;
		}

		SoundCategory category = sound.getCategory();

		if (category == SoundCategory.MUSIC)
		{
			if (this.mc.player != null && this.mc.player.dimension == AetherConfig.dimension.aether_dimension_id)
			{
				if (!sound.getSoundLocation().toString().contains("aether_legacy") && (this.musicTicker.playingMusic() || !this.musicTicker.playingMusic()))
				{
					event.setResultSound(null);

					return;
				}
			}

			if (sound.getSoundLocation().toString().equals("minecraft:music.menu"))
			{
				musicTicker.trackMinecraftMusic(sound);

				if (AetherConfig.visual_options.menu_enabled && Minecraft.getMinecraft().world == null && !(screen instanceof GuiScreenWorking))
				{
					event.setResultSound(null);
				}
			}
		}
		else if (category == SoundCategory.RECORDS && !(event.getName().contains("block.note")))
		{
			this.musicTicker.trackRecord(event.getSound());
			this.mc.getSoundHandler().stopSounds();

			return;
		}
	}

	@SideOnly(Side.CLIENT)
	public static ISound getAchievementSound(int number)
	{
		SoundEvent sound = number == 1 ? SoundsAether.achievement_bronze : number == 2 ? SoundsAether.achievement_silver : SoundsAether.achievement_gen;

		return new PositionedSoundRecord(sound.getSoundName(), SoundCategory.PLAYERS, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0, 0, 0);
	}

}