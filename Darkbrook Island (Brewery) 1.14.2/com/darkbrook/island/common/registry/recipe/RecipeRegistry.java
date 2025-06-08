package com.darkbrook.island.common.registry.recipe;

import com.darkbrook.island.common.registry.IRegistryManager;
import com.darkbrook.island.common.registry.IRegistryValue;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;
import com.darkbrook.island.common.registry.recipe.cooking.RecipeRegistryCooking;

public class RecipeRegistry implements IRegistryManager, IRegistryAction
{

	private RecipeRegistryCooking cooking;
	
	public RecipeRegistry()
	{
		cooking = new RecipeRegistryCooking();
	}
	
	@Override
	public void onEnable(RegistryPlugin plugin) 
	{
		cooking.onEnable(plugin);
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
		cooking.onDisable(plugin);
	}

	@Override
	public void initalize(IRegistryValue value) 
	{		
		cooking.initalize(value);
	}

}
