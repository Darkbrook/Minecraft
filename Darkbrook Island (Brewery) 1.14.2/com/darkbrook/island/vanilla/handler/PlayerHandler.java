package com.darkbrook.island.vanilla.handler;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.darkbrook.island.common.registry.handler.IRegistryHandler;

public class PlayerHandler implements IRegistryHandler
{
	
	@EventHandler
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event)
	{
		if(event.getPlayer().getGameMode() == GameMode.SPECTATOR || event.getNewGameMode() == GameMode.SPECTATOR) ConjurationHelper.getInstance().playEffect(event.getPlayer());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) 
	{		
		Player player = event.getPlayer();
		String displayName = player.getDisplayName();
		
		player.setPlayerListName(ChatColor.GRAY + displayName);
		event.setJoinMessage(ChatColor.GOLD + displayName + ChatColor.YELLOW + " has awoken from their slumber.");
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		event.setQuitMessage(ChatColor.GOLD + event.getPlayer().getDisplayName() + ChatColor.YELLOW + " has fallen into a deep slumber.");
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		event.setFormat(ChatColor.GRAY + player.getDisplayName() + ": %2$s");
		event.setMessage(ChatColor.WHITE + event.getMessage());
	}
	
}
