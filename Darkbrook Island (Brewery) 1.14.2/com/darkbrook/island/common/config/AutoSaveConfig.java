package com.darkbrook.island.common.config;

import org.bukkit.event.EventHandler;

import com.darkbrook.island.common.config.property.PropertyConfig;
import com.darkbrook.island.common.gameplay.event.ServerUpdateEvent;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;

public abstract class AutoSaveConfig extends PropertyConfig implements IRegistryHandler, IRegistryAction
{

	public AutoSaveConfig(String name) 
	{
		super(name);
	}

	@Override
	public void onEnable(RegistryPlugin plugin) 
	{
		load();
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
		save();
	}

	@EventHandler
	public void onServerUpdate(ServerUpdateEvent event)
	{
		if(event.getCurrentUpdate() % 60 == 0) save();
	}

}
