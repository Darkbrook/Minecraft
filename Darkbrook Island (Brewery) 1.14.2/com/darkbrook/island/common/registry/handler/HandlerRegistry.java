package com.darkbrook.island.common.registry.handler;

import com.darkbrook.island.common.registry.RegistryManager;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;

public class HandlerRegistry extends RegistryManager<IRegistryHandler>  implements IRegistryAction
{

	public HandlerRegistry() 
	{
		super(IRegistryHandler.class);
	}

	@Override
	public void onEnable(RegistryPlugin plugin) 
	{
		values.forEach(plugin::register);
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
		values.forEach(plugin::unregister);
	}
	
}
