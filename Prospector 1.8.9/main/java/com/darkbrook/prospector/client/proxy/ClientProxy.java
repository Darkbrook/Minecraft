package com.darkbrook.prospector.client.proxy;

import com.darkbrook.prospector.Prospector;
import com.darkbrook.prospector.client.init.StateMapperSluiceBox;
import com.darkbrook.prospector.init.ProspectorBlocks;
import com.darkbrook.prospector.init.ProspectorItems;
import com.darkbrook.prospector.proxy.CommonProxy;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		super.preInit();
		OBJLoader.instance.addDomain(Prospector.MODID);
		StateMapperSluiceBox.register();
	}
	
	@Override
	public void init()
	{
		super.init();
		ProspectorBlocks.registerItemModels();
		ProspectorItems.registerItemModels();
	}
}
