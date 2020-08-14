package com.gildedgames.the_aether.tile_entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.gildedgames.the_aether.registry.AetherLootTables;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
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
        this.kind = par1nbtTagCompound.getInteger("dungeonType");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound)
    {
    	super.writeToNBT(par1nbtTagCompound);
 
    	par1nbtTagCompound.setBoolean("locked", this.locked);
    	par1nbtTagCompound.setInteger("dungeonType", this.kind);

        return par1nbtTagCompound;
    }
    
    @Override
    public void fillWithLoot(@Nullable EntityPlayer player)
    {
    	if (this.lootTable != null)
        {
    		LootTableManager loottablemanager = this.world.getLootTableManager();
    		if (loottablemanager == null)
    		{
    			// added this because sometimes the returned loot table manager is null
    			return;
    		}
            LootTable loottable = loottablemanager.getLootTableFromLocation(this.lootTable);
            this.lootTable = null;
            Random random;

            if (this.lootTableSeed == 0L)
            {
                random = new Random();
            }
            else
            {
                random = new Random(this.lootTableSeed);
            }

            LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);

            if (player != null)
            {
                lootcontext$builder.withLuck(player.getLuck()).withPlayer(player); // Forge: add player to LootContext
            }

            loottable.fillInventory(this, random, lootcontext$builder.build());
        }
    }

    public void unlock(int kind)
    {
        this.kind = kind;

        switch (kind)
        {
	        case 0:
	        {
	            this.lootTable = AetherLootTables.bronze_dungeon_reward;
	            break;
	        }
	        case 1:
	        {
	            this.lootTable = AetherLootTables.silver_dungeon_reward;
	            break;
	        }
	        case 2:
	        {
	            this.lootTable = AetherLootTables.gold_dungeon_reward;
	            break;
	        }
        }

        this.locked = false;

        if (!this.world.isRemote)
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
        return new SPacketUpdateTileEntity(this.pos, 191, var1);
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
    	super.openInventory(player);

    	if (player instanceof EntityPlayerMP)
    	{
    		((EntityPlayerMP)player).connection.sendPacket(this.getUpdatePacket());
    	}
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            --this.numPlayersUsing;
            this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
            this.world.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType(), false);
        }
    }

    private void sendToAllInOurWorld(SPacketUpdateTileEntity pkt)
    {
        PlayerList scm = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();

        scm.sendPacketToAllPlayers(pkt);
    }

    public boolean isLocked()
    {
        return this.locked || super.isLocked();
    }

    public int getKind()
    {
        return this.kind;
    }

}