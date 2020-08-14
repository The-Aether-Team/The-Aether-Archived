package com.gildedgames.the_aether.blocks.dungeon;

import java.util.Random;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.CommonProxy;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.network.AetherGuiHandler;
import com.gildedgames.the_aether.tileentity.TileEntityTreasureChest;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTreasureChest extends BlockChest {

	public BlockTreasureChest() {
		super(56);

		this.setHardness(-1.0F);
		this.setResistance(2000.0F);
	}

	@Override
	public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_)
	{
		TileEntityTreasureChest treasurechest = (TileEntityTreasureChest) p_149712_1_.getTileEntity(p_149712_2_, p_149712_3_, p_149712_4_);

		if (treasurechest != null)
		{
			return treasurechest.isLocked() ? this.blockHardness : 5.0F;
		}
		else {
			return this.blockHardness;
		}
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		TileEntityTreasureChest treasurechest = (TileEntityTreasureChest) world.getTileEntity(x, y, z);

		return treasurechest.isLocked() ? this.blockResistance : 10.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(Aether.find("carved_stone"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTreasureChest();
	}

	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer playerIn, int side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		TileEntityTreasureChest treasurechest = (TileEntityTreasureChest) worldIn.getTileEntity(x, y, z);

		ItemStack guiID = playerIn.getCurrentEquippedItem();

		if (treasurechest.isLocked()) {
			if (guiID == null || guiID != null && guiID.getItem() != ItemsAether.dungeon_key) {
				return false;
			}

			treasurechest.unlock(guiID.getItemDamage());

			--guiID.stackSize;
		} else {
			playerIn.openGui(Aether.instance, AetherGuiHandler.treasure_chest, worldIn, x, y, z);
		}

		return true;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public int getRenderType() {
		return CommonProxy.treasureChestRenderID;
	}

}