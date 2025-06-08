package com.darkbrook.island.common.gameplay.event;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;

public class UpdateEventHandler implements IRegistryHandler, IRegistryAction
{
	
	private PluginManager manager;
	private int updateTask;
	private int currentUpdate;

	@Override
	public void onEnable(RegistryPlugin plugin)
	{
		manager = Bukkit.getPluginManager();
		updateTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::onUpdate, 0, 20);		
	}

	@Override
	public void onDisable(RegistryPlugin plugin)
	{
		Bukkit.getScheduler().cancelTask(updateTask);
	}
	
	private void onUpdate()
	{
		Bukkit.getWorlds().forEach(world -> manager.callEvent(new WorldUpdateEvent(world)));
		Bukkit.getOnlinePlayers().forEach(player -> manager.callEvent(new PlayerUpdateEvent(player)));
		manager.callEvent(new ServerUpdateEvent(currentUpdate++));
	}

}
