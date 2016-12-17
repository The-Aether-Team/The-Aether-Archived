package com.legacy.aether.client.audio;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.Aether;
import com.legacy.aether.server.AetherConfig;
import com.legacy.aether.server.registry.sounds.SoundsAether;

public class AetherMusicHandler 
{

	private Minecraft mc = Minecraft.getMinecraft();

	private Random random;

	public AetherMusicHandler()
	{
		this.random = new Random();
	}

	@SubscribeEvent
	public void onMusicControl(PlaySoundEvent event)
	{
		if (event.getSound().getCategory() == SoundCategory.MUSIC)
		{
			if (this.mc.thePlayer != null)
			{
				if (event.getSound().getSoundLocation() == this.mc.getAmbientMusicType().getMusicLocation().getSoundName() && this.mc.thePlayer.dimension == AetherConfig.getAetherDimensionID())
				{
					if (!this.isAetherSongPlaying(event.getSound()))
					{
						int songNum = this.random.nextInt(4);

						event.setResultSound(getSound(Aether.locate("music.aether" + (songNum + 1))));

		        		return;
					}

					event.setResultSound(null);

					return;
				}
			}
		}
		else if (event.getSound().getCategory() == SoundCategory.RECORDS)
		{
			this.mc.getSoundHandler().stopSounds();

			return;
		}
	}

	private boolean isAetherSongPlaying(ISound sound)
	{
		boolean aether1Playing = this.mc.getSoundHandler().isSoundPlaying(getSound(new ResourceLocation("aether_legacy:music.aether1")));
		boolean aether2Playing = this.mc.getSoundHandler().isSoundPlaying(getSound(new ResourceLocation("aether_legacy:music.aether2")));
		boolean aether3Playing = this.mc.getSoundHandler().isSoundPlaying(getSound(new ResourceLocation("aether_legacy:music.aether3")));
		boolean aether4Playing = this.mc.getSoundHandler().isSoundPlaying(getSound(new ResourceLocation("aether_legacy:music.aether4")));

		return aether1Playing || aether2Playing || aether3Playing || aether4Playing;
	}

	public void playSound(SoundEvent event)
	{
		ISound sound = new PositionedSoundRecord(event.getSoundName(), SoundCategory.PLAYERS, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0, 0, 0);

		this.mc.getSoundHandler().playSound(sound);
	}

	@SideOnly(Side.CLIENT)
	public static ISound getAchievementSound(int number)
	{
		SoundEvent sound = number == 1 ? SoundsAether.achievement_bronze : number == 2 ? SoundsAether.achievement_silver : SoundsAether.achievement_gen;

		return getSound(sound.getSoundName());
	}

	public static ISound getSound(ResourceLocation location)
	{
		return new PositionedSoundRecord(location, SoundCategory.MUSIC, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0, 0, 0);
	}

}