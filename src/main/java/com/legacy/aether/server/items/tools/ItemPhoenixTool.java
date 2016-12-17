package com.legacy.aether.server.items.tools;

import com.legacy.aether.server.items.util.EnumAetherToolType;

public class ItemPhoenixTool extends ItemAetherTool
{

	public ItemPhoenixTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
		this.setCreativeTab(null);
	}

}