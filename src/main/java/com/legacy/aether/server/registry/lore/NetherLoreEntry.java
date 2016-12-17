package com.legacy.aether.server.registry.lore;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.legacy.aether.server.registry.objects.EntryInformation;
import com.legacy.aether.server.registry.objects.LoreEntry;

public class NetherLoreEntry extends LoreEntry
{

	private ArrayList<EntryInformation> information;

	@Override
	public NetherLoreEntry initEntries()
	{
		information = new ArrayList<EntryInformation>();

		//Blocks
		information.add(new EntryInformation(new ItemStack(Blocks.NETHERRACK), "Netherrack", "The main base of", "the Nether which", "can be smelted into", "Nether Bricks.", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.NETHER_BRICK), "Nether Brick", "A block compacted", "of Nether Bricks,", "these are used to", "craft decoration blocks.", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.NETHER_WART), "Nether Wart", "Warts found in Nether", "fortresses and is the basis", "of any potion making", "in progress.", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.NETHER_BRICK_FENCE), "Nether Fence", "Nether Bricks re-formed", "into Fences. Decoration Block.", "Cannot be reverted", "to it's original state", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.NETHER_BRICK_STAIRS), "Nether Brick Stairs", "Nether Bricks","re-formed into Stairs.", "Used for decoration and", "cannot be reverted", "to it's original state.", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.SOUL_SAND), "Sould Sand", "The equivalent of sand", "although it sinks the", "player down making", "the player drag along, slowly.", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.GLOWSTONE), "Glowstone", "A glowing light block", "in the nether.", "Used to make the", "frame of the Aether Portal.", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.QUARTZ_ORE), "Quartz Ore", "An ore containing", "quartz. Drops one", "and can be used to", "make decoration blocks", "", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), "Quartz Block", "A block made from", "quartz. Used to make", "decoration blocks", "as well as being", "able to be chiseled.", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), "Chiseled Quartz Block", "Quartz Blocks", "which was chiseled.", "Used for decoration and", "cannot be reverted to", "it's original state.", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 2), "Pillar Quartz Block", "Quartz Blocks", "which has been re-formed.", "Pillar's can be flipped", "and cannot be reverted", "to it's original state", ""));
		information.add(new EntryInformation(new ItemStack(Blocks.QUARTZ_STAIRS), "Quartz Stairs", "Quartz Blocks","re-formed into Stairs.", "Used for decoration and", "cannot be reverted", "to it's original state.", ""));

		//Items
		information.add(new EntryInformation(new ItemStack(Items.GHAST_TEAR), "Ghast Tear", "A tear-drop of", "a Ghast. Uncommonly", "dropped and used for", "brewing potions of", "regeneration.", ""));
		information.add(new EntryInformation(new ItemStack(Items.BLAZE_ROD), "Blaze Rod", "A rod that", "is dropped from", "Blaze's and is", "essensial to making", "blaze powders.", ""));
		information.add(new EntryInformation(new ItemStack(Items.BLAZE_POWDER), "Blaze Powder", "Created from Blaze", "Rods and is used", "to make potions of", "strength.", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.MAGMA_CREAM), "Magma Cream", "Dropped by Magma Cubes", "and is used to", "make potions of fire", "resistance.", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.NETHER_WART), "Nether Wart", "Found in nether", "fortresses and is one", "of the main ingredients", "of brewing potions.", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.GLOWSTONE_DUST), "Glowstone Dust", "Used to create", "Glowstone blocks and", "in brewing, used to", "amplify potion strength.", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.QUARTZ), "Nether Quartz", "Obtained by mining Quartz", "Ore. Used to make", "Quartz blocks.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.NETHERBRICK), "Nether Brick", "Obtained by smelting", "Netherrack. Used to", "make Nether Brick Blocks.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(Items.GOLD_NUGGET), "Golden Nugget", "Obtained by killing Pigman.", "Used to create gold ingots.", "", "", "", ""));

		return this;
	}

	@Override
	public ArrayList<EntryInformation> EntryInformation()
	{
		return information;
	}

}
