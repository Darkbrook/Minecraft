package com.darkbrook.apoc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import com.darkbrook.library.plugin.registry.IRegistryValue;

public class ApocListener implements Listener, IRegistryValue
{
		
	@EventHandler
	public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event)
	{
		event.setCancelled(true);
	}
	
}
