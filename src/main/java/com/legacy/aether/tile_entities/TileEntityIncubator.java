package com.legacy.aether.tile_entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.passive.mountable.EntityMoa;
import com.legacy.aether.items.ItemMoaEgg;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.achievements.AchievementsAether;
import com.legacy.aether.tile_entities.util.AetherTileEntity;

public class TileEntityIncubator extends AetherTileEntity
{

    private NonNullList<ItemStack> incubatorItemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

	public EntityPlayer owner;

	public int torchPower;

	public int progress;

	public int secondsRequired = 1200;

	public TileEntityIncubator()
	{
		super("incubator");
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.incubatorItemStacks)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public int getSizeInventory()
	{
		return this.incubatorItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.incubatorItemStacks.get(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.incubatorItemStacks.get(i) != ItemStack.EMPTY)
		{
			if (this.incubatorItemStacks.get(i).getCount() <= j)
			{
				ItemStack itemstack = this.incubatorItemStacks.get(i);

				this.incubatorItemStacks.set(i, ItemStack.EMPTY);

				return itemstack;
			}
			else
			{
				ItemStack itemstack = this.incubatorItemStacks.get(i).splitStack(j);

				if (this.incubatorItemStacks.get(i).getCount() == 0)
				{
					this.incubatorItemStacks.set(i, ItemStack.EMPTY);
				}

				return itemstack;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.incubatorItemStacks.get(par1) != ItemStack.EMPTY)
		{
			ItemStack itemstack = this.incubatorItemStacks.get(par1);

			this.incubatorItemStacks.set(par1, ItemStack.EMPTY);

			return itemstack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.incubatorItemStacks.set(i, itemstack);

		if (itemstack != null && itemstack.getCount() > this.getInventoryStackLimit())
		{
			itemstack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		this.progress = nbttagcompound.getShort("IncubateTime");

		ItemStackHelper.loadAllItems(nbttagcompound, this.incubatorItemStacks);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("IncubateTime", (short) this.progress);

		ItemStackHelper.saveAllItems(nbttagcompound, this.incubatorItemStacks);

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

			if (this.getStackInSlot(1) != ItemStack.EMPTY)
			{
				this.progress++;
			}
		}

		if (this.getStackInSlot(1) == ItemStack.EMPTY || this.getStackInSlot(1).getItem() != ItemsAether.moa_egg)
		{
			this.progress = 0;
		}

		if (this.progress >= this.secondsRequired)
		{
			if (this.getStackInSlot(1).getItem() instanceof ItemMoaEgg)
			{
				if (!this.world.isRemote)
				{
					ItemMoaEgg moaEgg = (ItemMoaEgg) this.getStackInSlot(1).getItem();

					EntityMoa moa = new EntityMoa(this.world);

					moa.setPlayerGrown(true);
					moa.setGrowingAge(-24000);
					moa.setColor(moaEgg.getMoaColorFromItemStack(this.getStackInSlot(1)));

					for (int i = 0; this.world.getBlockState(pos.up(i)).getBlock() != Blocks.AIR; i++)
					{
						moa.setPositionAndUpdate(this.pos.getX() + 0.5D, this.pos.getY() + 1.5D, this.pos.getZ() + 0.5D);
					}

					this.world.spawnEntity(moa);

					if (this.owner != null)
					{
						this.owner.addStat(AchievementsAether.incubator);
					}
				}
			}

			if (!this.world.isRemote)
			{
				this.decrStackSize(1, 1);
			}

			this.progress = 0;
		}

		if (this.torchPower <= 0 && this.getStackInSlot(1).getItem() == ItemsAether.moa_egg && this.getStackInSlot(0).getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch))
		{
			this.torchPower += 1000;

			if (!this.world.isRemote)
			{
				this.decrStackSize(0, 1);
			}
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return this.world.getTileEntity(this.pos) != this ? false : par1EntityPlayer.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
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