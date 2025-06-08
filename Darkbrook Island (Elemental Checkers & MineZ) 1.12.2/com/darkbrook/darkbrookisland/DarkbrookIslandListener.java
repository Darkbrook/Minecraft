package com.darkbrook.darkbrookisland;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.library.command.basic.world.WorldLoader;
import com.darkbrook.library.config.MinecraftConfig;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.library.plugin.PluginGrabber;

import net.md_5.bungee.api.ChatColor;

public class DarkbrookIslandListener implements Listener {

	private static final DarkbrookRank STAFF = new DarkbrookRank(ChatColor.GOLD + "[Staff] " + ChatColor.GRAY, "", "rank.staff");
	private static final DarkbrookRank REGULAR = new DarkbrookRank("" + ChatColor.GRAY, "", "rank.regular");

	public static void register() {
		
		Plugin plugin = PluginGrabber.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(new DarkbrookIslandListener(), plugin);
		
		DarkbrookRank.scanOperatorRanks(STAFF, REGULAR);
				
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		GameMode mode = event.getPlayer().getGameMode();
		if(mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		GameMode mode = event.getPlayer().getGameMode();
		if((mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) && event.getBlock().getType() != Material.WEB) event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		GameMode mode = event.getPlayer().getGameMode();
		if(mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) event.setCancelled(true);
	}	
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		GameMode mode = event.getPlayer().getGameMode();
		if(mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) event.setCancelled(true);
	}	
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ": " + event.getMessage());
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
				
		String command = event.getCommand();
		
		if(command.startsWith("say") && command.length() > 4) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "[Console]" + ChatColor.GRAY + " wizzardcraftrock: " + command.substring(4, command.length()));
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
						
		DarkbrookRank.updateOperatorRank(player);
		
		for(Player playerOther : Bukkit.getServer().getOnlinePlayers()) playerOther.playSound(playerOther.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.0F);		
		event.setJoinMessage(ChatColor.GRAY + "'" + player.getDisplayName() + "' has awoken from their slumber.");
		
		DarkbrookIsland.updateUsername(player);
		DarkbrookIsland.updateIp(player);
		DarkbrookIsland.updateOpStatus(player);
				
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				MinecraftConfig config = DarkbrookIsland.playerdata;
				
				if(config.containsKey(player.getUniqueId() + ".logout")) {
					Location location = config.getLocation(player.getUniqueId() + ".logout");
					player.teleport(location.getWorld() != null ? location : WorldLoader.getWorld("DBI - Hub").getSpawnLocation());
				}
								
			}
			
		}, 1);
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		DarkbrookRank.updateOperatorRank(player);
		
		for(Player playerOther : Bukkit.getServer().getOnlinePlayers()) playerOther.playSound(playerOther.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 0.0F);		
		event.setQuitMessage(ChatColor.GRAY + "'" + player.getDisplayName() + "' has fell into a deep slumber.");
		
		DarkbrookIsland.updateOpStatus(player);
		DarkbrookIsland.updateLogoutLocation(player);
		
	}

}
