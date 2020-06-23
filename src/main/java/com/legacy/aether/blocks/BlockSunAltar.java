package com.legacy.aether.blocks;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.world.AetherWorldProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
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
		if (player.dimension == AetherConfig.dimension.aether_dimension_id)
		{
			WorldProvider provider = world.provider;

			if (provider instanceof AetherWorldProvider)
			{
				AetherWorldProvider aetherProvider = (AetherWorldProvider) provider;

				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

				if (!aetherProvider.getIsEternalDay() && !aetherProvider.getShouldCycleCatchup())
				{
					if (server != null && ((server.isDedicatedServer() && server.getPlayerList().canSendCommands(player.getGameProfile())) || !server.isDedicatedServer()))
					{
						Aether.proxy.openSunAltar();
					}
					else if (world.isRemote)
					{
						if (player instanceof EntityPlayerSP && ((EntityPlayerSP)player).getPermissionLevel() > 3)
						{
							Aether.proxy.openSunAltar();
						}
						else
						{
							player.sendMessage(new TextComponentTranslation("gui.sun_altar.permission"));
						}
					}
				}
				else if (aetherProvider.getIsEternalDay())
				{
					if (world.isRemote)
					{
						player.sendMessage(new TextComponentTranslation("gui.sun_altar.eternal_day"));
					}
				}
				else if (aetherProvider.getShouldCycleCatchup())
				{
					if (world.isRemote)
					{
						player.sendMessage(new TextComponentTranslation("gui.sun_altar.cycle_catchup"));
					}
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