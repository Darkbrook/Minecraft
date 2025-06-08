package com.darkbrook.island.internal.anticheat;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public class AntiCheat implements Listener {
			
	private static final HashMap<Player, Location> LOCATION_LAST = new HashMap<Player, Location>();
	private static final HashMap<Player, Location> LOCATION_NOW = new HashMap<Player, Location>();

	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new AntiCheat(), plugin);
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {

				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					
					LOCATION_LAST.put(player, LOCATION_NOW.containsKey(player) ? LOCATION_NOW.get(player) : player.getLocation());
					LOCATION_NOW.put(player, player.getLocation());

					AntiLevitation.register(player);
					AntiFly.registerAutoBan(player);
					
				}
				
			}
			
		}, 20, 20);
		
	}
	
	public static Location getFrom(Player player) {
		return LOCATION_LAST.containsKey(player) ? LOCATION_LAST.get(player) : player.getLocation();
	}
	
	public static Location getTo(Player player) {
		return LOCATION_NOW.containsKey(player) ? LOCATION_NOW.get(player) : player.getLocation();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {		
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getPlayer().getGameMode() != GameMode.SPECTATOR) {
			if(AntiFly.register(event.getPlayer(), event.getFrom(), event.getTo())) event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(References.playerdata.contains(event.getPlayer().getUniqueId() + ".banned")) event.setKickMessage(References.playerdata.getString(event.getPlayer().getUniqueId() + ".banned"));
	}
	
	public static void tellOp(String message) {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) if(p.isOp()) p.sendMessage(ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + message);
	}
	
	public static void dingOp() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) if(p.isOp()) p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_TOUCH, 1.0F, 0.0F);
	}
	
}
