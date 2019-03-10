package com.legacy.aether.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class AetherTranslation
{

	public AetherTranslation()
	{
	}

	public static String getTranslatedKey(String key, Object... args)
	{
		return getKeyComponent(key, args).getFormattedText();
	}

	public static ITextComponent getKeyComponent(String key, Object... args)
	{
		return new TextComponentTranslation(key, args);
	}

}