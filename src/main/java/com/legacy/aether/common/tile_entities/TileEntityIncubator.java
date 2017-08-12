package com.legacy.aether.common.tile_entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.entities.passive.mountable.EntityMoa;
import com.legacy.aether.common.events.MoaEggEvent;
import com.legacy.aether.common.items.ItemMoaEgg;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.registry.achievements.AchievementsAether;
import com.legacy.aether.common.tile_entities.util.AetherTileEntity;

public class TileEntityIncubator extends AetherTileEntity
{

	public EntityPlayer owner;

	private ItemStack IncubatorItemStacks[];

	public int torchPower;

	public int progress;

	public int secondsRequired = 1200;

	public TileEntityIncubator()
	{
		super("incubator");
		this.IncubatorItemStacks = new ItemStack[2];
		this.progress = 0;
	}

	@Override
	public int getSizeInventory()
	{
		return this.IncubatorItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.IncubatorItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.IncubatorItemStacks[i] != null)
		{
			if (this.IncubatorItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = this.IncubatorItemStacks[i];
				this.IncubatorItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = this.IncubatorItemStacks[i].splitStack(j);
			if (this.IncubatorItemStacks[i].stackSize == 0)
			{
				this.IncubatorItemStacks[i] = null;
			}
			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.IncubatorItemStacks[par1] != null)
		{
			ItemStack var2 = this.IncubatorItemStacks[par1];
			this.IncubatorItemStacks[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.IncubatorItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		this.IncubatorItemStacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < this.IncubatorItemStacks.length)
			{
				this.IncubatorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.progress = nbttagcompound.getShort("IncubateTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("IncubateTime", (short) this.progress);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.IncubatorItemStacks.length; i++)
		{
			if (this.IncubatorItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.IncubatorItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
		return super.writeToNBT(nbttagcompound);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int i)
	{
		return (this.progress * i) / this.secondsRequired;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int i)
	{
		return (this.torchPower * i) / 500;
	}

	public boolean isBurning()
	{
		return this.torchPower > 0;
	}

	@Override
	public void update()
	{
		if (this.torchPower > 0)
		{
			this.torchPower--;

			if (this.getStackInSlot(1) != null)
			{
				this.progress++;
			}
		}

		if (this.IncubatorItemStacks[1] == null || this.IncubatorItemStacks[1].getItem() != ItemsAether.moa_egg)
		{
			this.progress = 0;
		}

		if (this.progress >= this.secondsRequired)
		{
			if (this.IncubatorItemStacks[1] != null && this.IncubatorItemStacks[1].getItem() instanceof ItemMoaEgg)
			{
				ItemMoaEgg moaEgg = (ItemMoaEgg) this.IncubatorItemStacks[1].getItem();

				if (!this.worldObj.isRemote)
				{
					EntityMoa moa = new EntityMoa(this.worldObj);

					moa.setPlayerGrown(true);
					moa.setGrowingAge(-24000);
					moa.setColor(moaEgg.getMoaColorFromItemStack(this.IncubatorItemStacks[1]));

					for (int i = 0; this.worldObj.getBlockState(pos.up(i)).getBlock() != Blocks.AIR; i++)
					{
						moa.setPositionAndUpdate(this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D);
					}

					this.worldObj.spawnEntityInWorld(moa);

					if (this.owner != null)
					{
						this.owner.addStat(AchievementsAether.incubator);
					}
				}

				MinecraftForge.EVENT_BUS.post(new MoaEggEvent.Hatch(this, moaEgg.getMoaColorFromItemStack(this.IncubatorItemStacks[1])));
			}

			if (!this.worldObj.isRemote)
			{
				this.decrStackSize(1, 1);
			}

			this.progress = 0;
		}
		if (this.torchPower <= 0 && this.IncubatorItemStacks[1] != null && this.IncubatorItemStacks[1].getItem() == ItemsAether.moa_egg && this.getStackInSlot(0) != null && this.getStackInSlot(0).getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch))
		{
			this.torchPower += 1000;

			if (!this.worldObj.isRemote)
			{
				this.decrStackSize(0, 1);
			}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.worldObj.getTileEntity(this.pos) != this ? false : par1EntityPlayer.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
	}

	@Override
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

	@Override
	public boolean isValidSlotItem(int i, ItemStack itemstack)
	{
		return (i == 0 && itemstack.getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch) ? true : (i == 1 && itemstack.getItem() == ItemsAether.moa_egg));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {} : new int[] {0, 1};
	}

}