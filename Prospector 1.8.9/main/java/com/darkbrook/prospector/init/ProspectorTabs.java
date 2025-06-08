package com.darkbrook.prospector.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ProspectorTabs 
{
	public static final CreativeTabs all = new CreativeTabs("prospector")
	{
		@Override
		public Item getTabIconItem() 
		{
			return ProspectorItems.gold_pan;
		}
	};
}
