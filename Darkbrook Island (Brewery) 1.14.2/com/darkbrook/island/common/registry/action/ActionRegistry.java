package com.darkbrook.island.common.registry.action;

import com.darkbrook.island.common.registry.RegistryManager;
import com.darkbrook.island.common.registry.RegistryPlugin;

public class ActionRegistry extends RegistryManager<IRegistryAction> implements IRegistryAction
{
	
	public ActionRegistry() 
	{
		super(IRegistryAction.class);
	}

	@Override
	public void onEnable(RegistryPlugin plugin) 
	{
		values.forEach(action -> action.onEnable(plugin));
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
		values.forEach(action -> action.onDisable(plugin));
	}

}
