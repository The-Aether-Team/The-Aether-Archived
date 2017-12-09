package com.legacy.aether.registry.lore;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.legacy.aether.registry.objects.EntryInformation;
import com.legacy.aether.registry.objects.LoreEntry;

public class EndLoreEntry extends LoreEntry
{

	private ArrayList<EntryInformation> information;

	@Override
	public EndLoreEntry initEntries() 
	{
		information = new ArrayList<EntryInformation>();
		information.add(new EntryInformation(new ItemStack(Blocks.END_STONE), "End Stone", "Stone made of an", "unkown material. What", "does this serve?", "Only time will tell.", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.END_ROD), "End Rod", "A rod found in the End.", "Used as a light source as", "well as for decoration.", "Crafted with using", "a Blaze rod and", "a Popped chorus fruit."));
		information.add(new EntryInformation(new ItemStack(Blocks.PURPUR_BLOCK), "Purpur Block", "Decoration found in the End.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.PURPUR_PILLAR), "Purpur Pilar", "Decoration found in the End.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.PURPUR_STAIRS), "Purpur Stairs", "Decoration block made", "using Purpur blocks.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.PURPUR_SLAB), "Purpur Slab", "Decoration block made", "using Purpur blocks.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.END_BRICKS), "End Bricks", "Decoration block made", "by compacting End stone.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.CHORUS_PLANT), "Chorus Plant", "Plant found in the End.", "Drops Chorus Fruit", "once harvested.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.CHORUS_FLOWER), "Chorus Flower", "Grown on End Stone,", "used to grow Chorus", "Plants.", "", "", ""));

		information.add(new EntryInformation(new ItemStack(Items.CHORUS_FRUIT), "Chorus Fruit", "Dropped from", "Chorus Plants. Smelting it", "creates Popped Chorus", "Fruit.", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.CHORUS_FRUIT_POPPED), "Popped Chorus Fruit", "The result of", "smelting Chorus Fruit.", "Used to create", "Purpur Blocks.", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.SHULKER_SHELL), "Shulker Shell", "Can be crafted into", "a Shulker box.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.ELYTRA), "Elytra", "Wings used to allow", "the player real-time", "flight.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.END_CRYSTAL), "End Crystal", "Can be used to", "resurrect the Ender", "Dragon.", "", "", ""));

		return this;
	}

	@Override
	public ArrayList<EntryInformation> EntryInformation()
	{
		return information;
	}

}