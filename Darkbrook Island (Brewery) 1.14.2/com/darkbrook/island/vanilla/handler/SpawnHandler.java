package com.darkbrook.island.vanilla.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import com.darkbrook.island.common.registry.handler.IRegistryHandler;

public class SpawnHandler implements IRegistryHandler
{
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		event.setRespawnLocation(event.getRespawnLocation().getWorld().getSpawnLocation());
	}
	
	@EventHandler
	public void onPlayerSpawn(PlayerSpawnLocationEvent event)
	{
		if(!event.getPlayer().hasPlayedBefore()) event.setSpawnLocation(event.getSpawnLocation().getWorld().getSpawnLocation());
	}

}
