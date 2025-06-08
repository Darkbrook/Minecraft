package com.darkbrook.kingdoms.server.item.items;

import com.darkbrook.kingdoms.server.item.mmo.Category;
import com.darkbrook.kingdoms.server.item.mmo.Metal;

import net.minecraft.item.Items;

public class RingsItem extends MetalItem
{
	public RingsItem(Metal metal)
	{
		super(metal, Category.MATERIAL, String.format("%s Rings", metal), Items.CHAIN, Data.textured());
	}
}
