package com.darkbrook.island.vanilla.handler;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;

public class NetherHandler implements IRegistryHandler
{
	
	public static final String KICK_MESSAGE = ChatColor.YELLOW + "When leaving the nether you will be kicked from the server. This action is required due to a glitch that is currently present in version 1.14.1";

	private Map<String, Integer> taskMapping;
	
	public NetherHandler()
	{
		taskMapping = new HashMap<String, Integer>();
	}
	
	@EventHandler
	public void onEntityPortal(EntityPortalEvent event)
	{
		if(event.getEntityType() != EntityType.PLAYER) event.setCancelled(true);		
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		Player player;
		String name;
		
		if(event.getCause() != TeleportCause.NETHER_PORTAL || event.getFrom().getWorld().getEnvironment() != Environment.NETHER || taskMapping.containsKey(name = (player = event.getPlayer()).getName())) 
		{
			return;
		}
				
		taskMapping.put(name, Bukkit.getScheduler().scheduleSyncDelayedTask(RegistryPlugin.getInstance(), () -> onPlayerNetherQuit(name), 320));
		player.kickPlayer(KICK_MESSAGE);
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		String name = event.getPlayer().getName();
		
		if(!taskMapping.containsKey(name))
		{
			return;
		}
		
		Bukkit.getScheduler().cancelTask(taskMapping.remove(name));
		event.setJoinMessage(ChatColor.DARK_PURPLE + name + ChatColor.LIGHT_PURPLE + " exited the nether and lived to tell the tale.");
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if(taskMapping.containsKey(event.getPlayer().getName())) event.setQuitMessage(null);
	}
		
	private void onPlayerNetherQuit(String name)
	{
		taskMapping.remove(name);
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + name + ChatColor.LIGHT_PURPLE + " exited the nether and was never seen again.");
	}
	
}
