package com.legacy.aether.entities.block;

import io.netty.buffer.ByteBuf;

import java.util.List;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.blocks.util.BlockFloating;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFloatingBlock extends Entity implements IEntityAdditionalSpawnData {

	private Block block = Blocks.air;

	private int meta = 0;

	private int timeFloated = 0;

	public EntityFloatingBlock(World worldIn) {
		super(worldIn);

		this.setSize(0.98F, 0.98F);
	}

	public EntityFloatingBlock(World world, int x, int y, int z, Block block, int meta) {
		super(world);

		this.meta = meta;
		this.block = block;
		this.preventEntitySpawning = true;
		this.motionX = this.motionY = this.motionZ = 0;

		this.setSize(0.98F, 0.98F);
		this.setPosition(x + 0.5D, y, z + 0.5D);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	public void onUpdate() {
		if (this.getBlock() == null || this.getBlock() == Blocks.air) {
			this.setDead();
			return;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		++this.timeFloated;
		this.motionY += 0.04D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		Block block = this.getBlock();
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);

		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.0D, 1.0D, 0.0D));

		for (int stack = 0; stack < list.size(); ++stack) {
			Entity entity = (Entity) list.get(stack);

			if (entity instanceof EntityFallingBlock && block.canPlaceBlockAt(this.worldObj, i, j, k)) {
				this.worldObj.setBlock(i, j + 1, k, this.getBlock(), this.getMetadata(), 2);
				this.setDead();
			} else if (AetherConfig.shouldFloatWithBlock()) {
				entity.setPosition(entity.posX, this.posY + 2.6D, entity.posZ);
				entity.motionY = 0.0D;
				entity.onGround = true;
				entity.fallDistance = 0.0F;
			}
		}

		if (this.isCollidedVertically && !this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
			this.setDead();

			if (!block.canPlaceBlockAt(this.worldObj, i, j, k) || BlockFloating.canContinue(this.worldObj, i, j + 1, k) || !this.worldObj.setBlock(i, j, k, this.getBlock(), this.getMetadata(), 2)) {
				block.dropBlockAsItem(this.worldObj, i, j, k, this.getMetadata(), 0);
			}
		} else if (this.timeFloated > 100) {
			block.dropBlockAsItem(this.worldObj, i, j, k, this.getMetadata(), 0);

			this.setDead();
		}
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Block getBlock() {
		return this.block;
	}

	public void setMetadata(int meta) {
		this.meta = meta;
	}

	public int getMetadata() {
		return this.meta;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public void setDead() {
		super.setDead();
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setBlock(Block.getBlockById(compound.getInteger("blockId")));
		this.setMetadata(compound.getInteger("metadata"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("blockId", Block.getIdFromBlock(this.getBlock()));
		compound.setInteger("metadata", this.getMetadata());
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(Block.getIdFromBlock(this.getBlock()));
		buffer.writeInt(this.meta);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		this.setBlock(Block.getBlockById(buffer.readInt()));
		this.setMetadata(buffer.readInt());
	}

}