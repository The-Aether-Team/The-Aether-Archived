package com.gildedgames.the_aether.client.renders.items.definitions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.Aether;

public class PhoenixBowDefinition implements ItemMeshDefinition
{

	private ModelResourceLocation bow, stage_0, stage_1, stage_2;

	private Minecraft mc;

	public PhoenixBowDefinition()
	{
		this.mc = Minecraft.getMinecraft();
		String name = "phoenix_bow";
		this.bow = new ModelResourceLocation(Aether.modAddress() + name, "inventory");
		this.stage_0 = new ModelResourceLocation(Aether.modAddress() + name + "_pulling_0", "inventory");
		this.stage_1 = new ModelResourceLocation(Aether.modAddress() + name + "_pulling_1", "inventory");
		this.stage_2 = new ModelResourceLocation(Aether.modAddress() + name + "_pulling_2", "inventory");
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		if (this.mc.player == null)
		{
			return this.bow;
		}

		EntityPlayer player = this.mc.player;

		if (player.getActiveHand() != null && player.getActiveItemStack() == stack)
		{
			int useTime = stack.getMaxItemUseDuration() - player.getItemInUseCount();
			
			if (useTime >= 18)
			{
				return this.stage_2;
			}
			else if (useTime  > 13)
			{
				return this.stage_1;
			}
			else if (useTime > 0)
			{
				return this.stage_0;
			}
		}

		return this.bow;
	}

}