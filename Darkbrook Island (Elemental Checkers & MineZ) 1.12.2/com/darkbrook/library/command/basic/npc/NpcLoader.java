package com.darkbrook.library.command.basic.npc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.library.plugin.PluginGrabber;

public class NpcLoader implements Listener {

	public static void load() {
		Plugin plugin = PluginGrabber.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(new NpcLoader(), plugin);
		for(Player player : Bukkit.getServer().getOnlinePlayers()) Npc.loadNpcs(player);
	}
	
	public static void unload() {
		Npc.unloadNps();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Npc.loadNpcs(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Npc.loadNpcs(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Npc.reloadNpcs(event.getPlayer());
	}
	
}
