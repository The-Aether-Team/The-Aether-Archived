package com.legacy.aether.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

import com.legacy.aether.client.gui.GuiEnchanter;
import com.legacy.aether.client.gui.GuiFreezer;
import com.legacy.aether.client.gui.GuiIncubator;
import com.legacy.aether.client.gui.GuiLore;
import com.legacy.aether.client.gui.GuiTreasureChest;
import com.legacy.aether.client.gui.inventory.GuiAccessories;
import com.legacy.aether.inventory.ContainerAccessories;
import com.legacy.aether.inventory.ContainerEnchanter;
import com.legacy.aether.inventory.ContainerFreezer;
import com.legacy.aether.inventory.ContainerIncubator;
import com.legacy.aether.inventory.ContainerLore;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.tileentity.TileEntityEnchanter;
import com.legacy.aether.tileentity.TileEntityFreezer;
import com.legacy.aether.tileentity.TileEntityIncubator;
import com.legacy.aether.tileentity.TileEntityTreasureChest;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherGuiHandler implements IGuiHandler {

	public static final int accessories = 1, enchanter = 2, freezer = 3, incubator = 4, treasure_chest = 5, lore = 6;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == accessories) {
			return new ContainerAccessories(PlayerAether.get(player).getAccessoryInventory(), player);
		} else if (ID == enchanter) {
			return new ContainerEnchanter(player.inventory, (TileEntityEnchanter) world.getTileEntity(x, y, z));
		} else if (ID == freezer) {
			return new ContainerFreezer(player.inventory, (TileEntityFreezer) world.getTileEntity(x, y, z));
		} else if (ID == incubator) {
			return new ContainerIncubator(player, player.inventory, (TileEntityIncubator) world.getTileEntity(x, y, z));
		} else if (ID == treasure_chest) {
			return new ContainerChest(player.inventory, (IInventory) world.getTileEntity(x, y, z));
		} else if (ID == lore) {
			return new ContainerLore(player.inventory);
		}

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == accessories) {
			return new GuiAccessories(PlayerAether.get(player));
		} else if (ID == enchanter) {
			return new GuiEnchanter(player.inventory, (TileEntityEnchanter) world.getTileEntity(x, y, z));
		} else if (ID == freezer) {
			return new GuiFreezer(player.inventory, (TileEntityFreezer) world.getTileEntity(x, y, z));
		} else if (ID == incubator) {
			return new GuiIncubator(player, player.inventory, (TileEntityIncubator) world.getTileEntity(x, y, z));
		} else if (ID == treasure_chest) {
			return new GuiTreasureChest(player.inventory, (TileEntityTreasureChest) world.getTileEntity(x, y, z));
		} else if (ID == lore) {
			return new GuiLore(player.inventory);
		}

		return null;
	}

}