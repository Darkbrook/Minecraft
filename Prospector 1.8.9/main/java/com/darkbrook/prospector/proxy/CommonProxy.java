package com.darkbrook.prospector.proxy;

import com.darkbrook.prospector.init.ProspectorBlocks;
import com.darkbrook.prospector.init.ProspectorGuis;
import com.darkbrook.prospector.init.ProspectorItems;
import com.darkbrook.prospector.init.ProspectorRecipes;
import com.darkbrook.prospector.init.ProspectorTileEntities;
import com.darkbrook.prospector.init.ProspectorWorldGenerator;

public class CommonProxy 
{
	public void preInit()
	{
		ProspectorBlocks.register();
		ProspectorTileEntities.register();
		ProspectorItems.register();
		ProspectorWorldGenerator.register();
	}
	
	public void init()
	{
		ProspectorGuis.register();
		ProspectorRecipes.register();
	}
}
