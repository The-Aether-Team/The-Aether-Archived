package com.legacy.aether.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.client.audio.music.AetherMusicTicker;
import com.legacy.aether.common.registry.sounds.SoundsAether;

public class AetherMusicHandler 
{

	private Minecraft mc = Minecraft.getMinecraft();

	private AetherMusicTicker musicTicker = new AetherMusicTicker(mc);

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception
	{
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				if (!mc.isGamePaused())
				{
					musicTicker.update();
				}
			}
		}
	}

	@SubscribeEvent
	public void onMusicControl(PlaySoundEvent event)
	{
		ISound sound = event.getSound();
		SoundCategory category = sound.getCategory();

		if (category == SoundCategory.MUSIC)
		{
			if (!sound.getSoundLocation().toString().contains("aether_legacy"))
			{
				event.setResultSound(null);

				return;
			}
		}
		else if (category == SoundCategory.RECORDS)
		{
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