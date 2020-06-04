package com.legacy.aether.blocks;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.world.AetherWorld;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.legacy.aether.Aether;

public class BlockSunAltar extends Block
{

	public BlockSunAltar()
	{
		super(Material.ROCK);

		this.setHardness(2.5F);
		this.setSoundType(SoundType.METAL);
	}

	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		if (player.dimension == AetherConfig.dimension.aether_dimension_id)
		{
			if (server != null && ((server.isDedicatedServer() && server.getPlayerList().canSendCommands(player.getGameProfile()) && player.capabilities.isCreativeMode) || !server.isDedicatedServer()))
			{
				Aether.proxy.openSunAltar();
			}
			else if (world.isRemote)
			{
				if (player instanceof EntityPlayerSP && ((EntityPlayerSP)player).getPermissionLevel() > 0 && player.capabilities.isCreativeMode)
				{
					Aether.proxy.openSunAltar();
				}
			}

		}
		else
		{
			if (world.isRemote)
			{
				player.sendMessage(new TextComponentTranslation("gui.sun_altar.message"));
			}

		}

		return true;
	}
}