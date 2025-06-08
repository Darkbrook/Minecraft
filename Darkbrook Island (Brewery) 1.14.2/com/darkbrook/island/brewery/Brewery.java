package com.darkbrook.island.brewery;

import org.bukkit.Material;

import com.darkbrook.island.brewery.handler.AlcoholEffectHandler;
import com.darkbrook.island.brewery.registry.BreweryRegistry;
import com.darkbrook.island.brewery.registry.IFermentable;
import com.darkbrook.island.brewery.registry.IFermentableLiquid;
import com.darkbrook.island.brewery.util.GenericFermentable;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;
import com.darkbrook.island.common.registry.action.IRegistryInstantAction;

public class Brewery implements IRegistryInstantAction, IRegistryAction
{
	
	private static BreweryRegistry breweryRegistry;

	@Override
	public void onInitialize(RegistryPlugin plugin) 
	{
		plugin.initialize(new AlcoholEffectHandler());
		plugin.initialize(breweryRegistry = new BreweryRegistry());
	}

	@Override
	public void onEnable(RegistryPlugin plugin) 
	{
		Brewery.addFermentable("Sweet Berry Wine", Material.SWEET_BERRIES, BreweryLiquid.MAROON, 0.08f, 6);
		Brewery.addFermentable("Apple Wine", Material.APPLE, BreweryLiquid.AMBER, 0.16f, 12);
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{		
	}
	
	public static void addFermentable(String beverageName, Material source, IFermentableLiquid liquid, float beverageAbv, int fermentationDays)
	{
		Brewery.addFermentable(new GenericFermentable(beverageName, source, liquid, beverageAbv, fermentationDays));
	}
	
	public static void addFermentable(IFermentable fermentable)
	{
		breweryRegistry.initalize(fermentable);
	}

}
