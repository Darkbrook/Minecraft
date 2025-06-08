package com.darkbrook.island.mmo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.internal.anticheat.AntiCheat;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.entity.HeartBeat;

public class GameHook implements Listener {
		
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new GameHook(), plugin);
	}
	
	public static void loadMMO(Plugin plugin) {
					
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				for(Player player : Bukkit.getServer().getOnlinePlayers()) HeartBeat.getHeartBeat(player).playSound();
			}
			
		}, 0, 20);
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(References.getFormat(ChatColor.DARK_AQUA, ChatColor.AQUA, event.getPlayer().getDisplayName() + " has awoken"));
		AntiCheat.dingOp();
		HeartBeat.addHeartBeat(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		event.setQuitMessage(References.getFormat(ChatColor.DARK_AQUA, ChatColor.AQUA, event.getPlayer().getDisplayName() + " has fell into a deep slumber"));
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {						
		
		Player player = event.getPlayer();
		
		if(player.getInventory().getItemInMainHand().getType() == Material.EMPTY_MAP) {
			player.updateInventory();
			event.setCancelled(true);
		} else if(player.getInventory().getItemInOffHand().getType() == Material.EMPTY_MAP) {
			player.updateInventory();
			event.setCancelled(true);
		} 	
				
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
	}	
	
	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		event.setFormat(ChatColor.GRAY + event.getPlayer().getName() + ": " + event.getMessage());
	}

}
