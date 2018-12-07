package com.legacy.aether.blocks.dungeon;

import java.util.Random;

import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.CommonProxy;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.network.AetherGuiHandler;
import com.legacy.aether.tileentity.TileEntityTreasureChest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTreasureChest extends BlockChest
{

	public BlockTreasureChest()
	{
		super(56);

		this.setBlockUnbreakable();
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
    	this.blockIcon = p_149651_1_.registerIcon(Aether.find("carved_stone"));
    }

	@Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
    	return new TileEntityTreasureChest();
    }

	@Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer playerIn, int side, float hitX, float hitY, float hitZ)
    {
		if (worldIn.isRemote)
		{
			return true;
		}

        TileEntityTreasureChest treasurechest = (TileEntityTreasureChest)worldIn.getTileEntity(x, y, z);

        ItemStack guiID = playerIn.getCurrentEquippedItem();

        if (treasurechest.isLocked())
        {
            if (guiID == null || guiID != null && guiID.getItem() != ItemsAether.dungeon_key)
            {
                return false;
            }

            treasurechest.unlock(guiID.getItemDamage());

            --guiID.stackSize;
        }
        else
        {
            playerIn.openGui(Aether.instance, AetherGuiHandler.treasure_chest, worldIn, x, y, z);
        }

        return true;
    }

	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

	@Override
    public int getRenderType()
    {
        return CommonProxy.treasureChestRenderID;
    }

}