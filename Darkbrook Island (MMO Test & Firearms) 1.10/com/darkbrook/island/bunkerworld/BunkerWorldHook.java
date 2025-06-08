package com.darkbrook.island.bunkerworld;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public class BunkerWorldHook implements Listener {
	
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new BunkerWorldHook(), plugin);
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				for(Player player : Bukkit.getServer().getOnlinePlayers()) GunType.update(player);								
			}
			
		}, 0, 1);
		
	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		GunType.shoot(event);
		GunType.zoom(event);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		GunType.damage(event);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		GunType.reload(event);
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		GunType.antiPearl(event);
	}
	
}

