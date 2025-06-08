package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;

import net.minecraft.item.Item;

public class OreItem extends MetalItem
{
	public OreItem(Metal metal, String displayName, Item item)
	{
		this(metal, displayName, item, Data.empty());
	}
	
	public OreItem(Metal metal, String displayName, Item item, Data data)
	{
		super(metal, Category.ORE, displayName, item, data);
	}
}
