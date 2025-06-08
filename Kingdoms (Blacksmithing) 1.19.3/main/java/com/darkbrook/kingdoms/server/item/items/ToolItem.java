package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.common.item.nbt.CustomModelData;
import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;
import com.darkbrook.kingdoms.server.item.mmo.Tool;

import net.minecraft.item.Item;

public class ToolItem extends MetalItem
{
	protected final Tool tool;
	
	public ToolItem(Metal metal, Tool tool, Item item, Data data)
	{
		super(metal, Category.TOOL, String.format("%s %s", metal, tool), item, data);
		this.tool = tool;
	}
	
	public Tool getTool()
	{
		return tool;
	}
	
	@Override
	protected CustomModelData getTexture()
	{
		return new CustomModelData(metal, tool);
	}
}
