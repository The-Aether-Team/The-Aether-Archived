package com.legacy.aether.server.tile_entities;

import java.util.Random;

import com.legacy.aether.server.world.dungeon.*;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityTreasureChest extends TileEntityChest
{

    private boolean locked = true;

    private int kind = 0;

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound)
    {
        super.readFromNBT(par1nbtTagCompound);
        this.locked = par1nbtTagCompound.getBoolean("locked");
        this.kind = par1nbtTagCompound.getInteger("kind");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound)
    {
        par1nbtTagCompound.setBoolean("locked", this.locked);
        par1nbtTagCompound.setInteger("kind", this.kind);
        return super.writeToNBT(par1nbtTagCompound);
    }

    public void unlock(int kind)
    {
        this.kind = kind;
        Random random = new Random();
        int p;

        if (kind == 0)
        {
            for (p = 0; p < 5 + random.nextInt(1); ++p)
            {
                this.setInventorySlotContents(random.nextInt(this.getSizeInventory()), BronzeDungeon.getBronzeLoot(random));
            }
        }

        if (kind == 1)
        {
            for (p = 0; p < 5 + random.nextInt(1); ++p)
            {
                this.setInventorySlotContents(random.nextInt(this.getSizeInventory()), SilverDungeon.getSilverLoot(random));
            }
        }

        if (kind == 2)
        {
            for (p = 0; p < 5 + random.nextInt(1); ++p)
            {
                this.setInventorySlotContents(random.nextInt(this.getSizeInventory()), GoldenDungeon.getGoldLoot(random));
            }
        }

        if (kind == 3)
        {
        	for (p = 0; p < 5 + random.nextInt(1); ++p)
        	{
        		this.setInventorySlotContents(random.nextInt(this.getSizeInventory()), PlatinumDungeon.getPlatinumLoot(random));
        	}
        }

        this.locked = false;

        if (!this.worldObj.isRemote)
        {
            this.sendToAllInOurWorld(this.getUpdatePacket());
        }
    }

	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new SPacketUpdateTileEntity(this.pos, 1, var1);
    }

    private void sendToAllInOurWorld(SPacketUpdateTileEntity pkt)
    {
        PlayerList scm = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();

        scm.sendPacketToAllPlayers(pkt);
    }

    public boolean isLocked()
    {
        return this.locked;
    }

    public int getKind()
    {
        return this.kind;
    }

}