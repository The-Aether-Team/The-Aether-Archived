package com.legacy.aether.blocks.container;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.blocks.BlocksAether;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSunAltar extends Block
{

    @SideOnly(Side.CLIENT)
    private IIcon blockIconTop;

	public BlockSunAltar()
	{
		super(Material.rock);

		this.setHardness(2.5F);
		this.setStepSound(soundTypeMetal);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry)
    {
    	this.blockIcon = registry.registerIcon(Aether.find("sun_altar_side"));
    	this.blockIconTop = registry.registerIcon(Aether.find("sun_altar_top"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? this.blockIconTop : (side == 0 ? BlocksAether.hellfire_stone.getBlockTextureFromSide(side) : this.blockIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        if (side == 1)
        {
            return this.blockIconTop;
        }
        else if (side == 0)
        {
            return BlocksAether.hellfire_stone.getBlockTextureFromSide(side);
        }

        return this.blockIcon;
    }

	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		if (server != null && ((server.isDedicatedServer() && server.getConfigurationManager().func_152596_g(player.getGameProfile()) && player.capabilities.isCreativeMode) || !server.isDedicatedServer()))
		{
			Aether.proxy.openSunAltar();
		}
		else if (world.isRemote)
		{
			if (player instanceof EntityPlayerSP && player.capabilities.isCreativeMode)
			{
				Aether.proxy.openSunAltar();
			}
		}

		return true;
    }

}