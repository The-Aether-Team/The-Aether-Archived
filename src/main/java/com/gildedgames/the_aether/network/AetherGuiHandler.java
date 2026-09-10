package com.gildedgames.the_aether.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gildedgames.the_aether.client.gui.GuiEnchanter;
import com.gildedgames.the_aether.client.gui.GuiFreezer;
import com.gildedgames.the_aether.client.gui.GuiIncubator;
import com.gildedgames.the_aether.client.gui.GuiLore;
import com.gildedgames.the_aether.client.gui.GuiTreasureChest;
import com.gildedgames.the_aether.client.gui.inventory.GuiAccessories;
import com.gildedgames.the_aether.inventory.ContainerAccessories;
import com.gildedgames.the_aether.inventory.ContainerEnchanter;
import com.gildedgames.the_aether.inventory.ContainerFreezer;
import com.gildedgames.the_aether.inventory.ContainerIncubator;
import com.gildedgames.the_aether.inventory.ContainerLore;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.tileentity.TileEntityEnchanter;
import com.gildedgames.the_aether.tileentity.TileEntityFreezer;
import com.gildedgames.the_aether.tileentity.TileEntityIncubator;
import com.gildedgames.the_aether.tileentity.TileEntityTreasureChest;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherGuiHandler implements IGuiHandler {

    public static final int accessories = 1, enchanter = 2, freezer = 3, incubator = 4, treasure_chest = 5, lore = 6;

    /**
     * The largest squared distance at which a tile-entity-bound GUI may be
     * opened. Vanilla's block activation reach is 6 blocks; the container
     * tick already enforces 8 blocks via isUseableByPlayer. 9 blocks leaves
     * headroom without allowing remote interaction.
     */
    private static final double MAX_OPEN_DISTANCE_SQ = 81.0D;

    private static boolean canReach(EntityPlayer player, int x, int y, int z) {
        return player.getDistanceSq(x + 0.5D, y + 0.5D, z + 0.5D) <= MAX_OPEN_DISTANCE_SQ;
    }

    private static boolean isWithinReach(EntityPlayer player, TileEntity tileEntity) {
        return tileEntity != null && canReach(player, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == accessories) {
            return new ContainerAccessories(
                PlayerAether.get(player)
                    .getAccessoryInventory(),
                player);
        } else if (ID == enchanter) {
            TileEntity enchanter = world.getTileEntity(x, y, z);

            // Defense in depth: only open the GUI when the tile entity is
            // actually an enchanter and the player is in reach. An unchecked
            // cast here would crash the server if the block was replaced
            // between the interaction and this call.
            return enchanter instanceof TileEntityEnchanter && isWithinReach(player, enchanter)
                ? new ContainerEnchanter(player.inventory, (TileEntityEnchanter) enchanter)
                : null;
        } else if (ID == freezer) {
            TileEntity freezer = world.getTileEntity(x, y, z);

            return freezer instanceof TileEntityFreezer && isWithinReach(player, freezer)
                ? new ContainerFreezer(player.inventory, (TileEntityFreezer) freezer)
                : null;
        } else if (ID == incubator) {
            TileEntity incubator = world.getTileEntity(x, y, z);

            return incubator instanceof TileEntityIncubator && isWithinReach(player, incubator)
                ? new ContainerIncubator(player, player.inventory, (TileEntityIncubator) incubator)
                : null;
        } else if (ID == treasure_chest) {
            TileEntity chest = world.getTileEntity(x, y, z);

            return chest instanceof IInventory && isWithinReach(player, chest)
                ? new ContainerChest(player.inventory, (IInventory) chest)
                : null;
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
            TileEntity enchanter = world.getTileEntity(x, y, z);

            return enchanter instanceof TileEntityEnchanter
                ? new GuiEnchanter(player.inventory, (TileEntityEnchanter) enchanter)
                : null;
        } else if (ID == freezer) {
            TileEntity freezer = world.getTileEntity(x, y, z);

            return freezer instanceof TileEntityFreezer ? new GuiFreezer(player.inventory, (TileEntityFreezer) freezer)
                : null;
        } else if (ID == incubator) {
            TileEntity incubator = world.getTileEntity(x, y, z);

            return incubator instanceof TileEntityIncubator
                ? new GuiIncubator(player, player.inventory, (TileEntityIncubator) incubator)
                : null;
        } else if (ID == treasure_chest) {
            TileEntity chest = world.getTileEntity(x, y, z);

            return chest instanceof TileEntityTreasureChest
                ? new GuiTreasureChest(player.inventory, (TileEntityTreasureChest) chest)
                : null;
        } else if (ID == lore) {
            return new GuiLore(player.inventory);
        }

        return null;
    }

}
