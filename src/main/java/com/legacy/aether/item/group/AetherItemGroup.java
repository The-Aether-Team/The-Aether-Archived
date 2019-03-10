package com.legacy.aether.item.group;

import com.legacy.aether.block.BlocksAether;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AetherItemGroup extends ItemGroup
{

	public static final AetherItemGroup BLOCKS = new AetherItemGroup("blocks")
	{

		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon()
		{
			return new ItemStack(BlocksAether.AETHER_GRASS);
		}
	};

	public AetherItemGroup(String label)
	{
		super("aether_legacy." + label);
	}

}