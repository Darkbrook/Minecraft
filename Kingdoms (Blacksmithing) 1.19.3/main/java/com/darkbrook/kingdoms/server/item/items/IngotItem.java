package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;

import net.minecraft.item.Item;

public class IngotItem extends MetalItem
{
	public IngotItem(Metal metal, Item item)
	{
		this(metal, item, Data.empty());
	}
	
	public IngotItem(Metal metal, Item item, Data data)
	{
		super(metal, Category.MATERIAL, String.format("%s Ingot", metal), item, data);
	}
}
