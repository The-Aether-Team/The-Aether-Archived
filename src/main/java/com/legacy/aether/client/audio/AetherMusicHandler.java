package com.legacy.aether.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.AetherConfig;
import com.legacy.aether.server.registry.sounds.SoundsAether;

public class AetherMusicHandler 
{

	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onMusicControl(PlaySoundEvent event)
	{
		SoundCategory category = event.getSound().getCategory();

		if (category == SoundCategory.MUSIC)
		{
			if (this.mc.thePlayer != null)
			{
				if (!event.getSound().getSoundLocation().getResourceDomain().contains("aether_legacy") && this.mc.thePlayer.dimension == AetherConfig.getAetherDimensionID())
				{
					event.setResultSound(null);

					return;
				}
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