package com.gildedgames.the_aether.client.gui.trivia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import com.gildedgames.the_aether.Aether;
import net.minecraft.client.Minecraft;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;

public class AetherTrivia {

	private static Random random = new Random();

	public AetherTrivia() {

	}

	public static String getNewTrivia() {

		String localization = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

		if (getEntriesForLocalization(localization) != null)
		{
			return getEntriesForLocalization(localization);
		}
		else if (getEntriesForLocalization("en_US") != null)
		{
			return getEntriesForLocalization("en_US");
		}
		else
		{
			return "missingno";
		}
	}

	public static String getEntriesForLocalization(String localization)
	{
		BufferedReader bufferedreader = null;

		try {
			List<String> list = Lists.newArrayList();
			bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(Aether.locate("texts/" + localization + ".txt")).getInputStream(), StandardCharsets.UTF_8));
			String s;

			while ((s = bufferedreader.readLine()) != null) {
				s = s.trim();

				if (!s.isEmpty()) {
					list.add(s);
				}
			}

			if (!list.isEmpty()) {
				return I18n.format("gui.aether_trivia.pro_tip") + " " + list.get(random.nextInt(list.size()));
			}
		} catch (IOException ignore) { }
		finally {
			if (bufferedreader != null) {
				try {
					bufferedreader.close();
				} catch (IOException ignore) { }
			}
		}

		return null;
	}
}