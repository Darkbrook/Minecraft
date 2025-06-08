package com.darkbrook.prospector;

import com.darkbrook.prospector.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Prospector.MODID, version = Prospector.VERSION)
public class Prospector 
{
	public static final String MODID = "prospector";
	public static final String VERSION = "indev";
	
	@SidedProxy
	(clientSide = "com.darkbrook.prospector.client.proxy.ClientProxy",
	 serverSide = "com.darkbrook.prospector.proxy.CommonProxy")
	private static CommonProxy proxy;
	
	@Instance
	public static Prospector instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}
}
