package com.darkbrook.island.internal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.library.config.Config;
import com.darkbrook.island.library.config.WorldConfig;
import com.darkbrook.island.library.misc.LocationHandler;
import com.darkbrook.island.library.misc.PacketHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public class Respawn implements Listener {
	 
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new Respawn(), plugin);
	}
	
	public static void unload() {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) References.playerdata.setLocation(player.getUniqueId() + ".Logout", player.getLocation());
	}
	
	public static Config getConfig(World world) {
		return WorldConfig.getConfig(world, "spawndata");
	}
	
	public static void setRespawnLocation(Player player) {
		World world = player.getWorld();
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();
		float yaw = player.getLocation().getYaw();
		float pitch = player.getLocation().getPitch();
		player.getWorld().setSpawnLocation(x, y, z);
		getConfig(player.getWorld()).setLocation("Spawn." + world.getName(), new Location(world, x, y, z, yaw, pitch));
	}

	public static Location getRespawnLocation(World world) {
		Location respawnLocation = LocationHandler.getLocationCenterWithOffset(world.getSpawnLocation(), 0.5, 0.5, 0.5);
		if(getConfig(world).contains("Spawn." + world.getName())) respawnLocation = getConfig(world).getLocation("Spawn." + world.getName());
		return respawnLocation;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(getRespawnLocation(event.getPlayer().getWorld()));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!player.hasPlayedBefore()) player.teleport(getRespawnLocation(player.getWorld()));
		
		UpdateHandler.delay(new UpdateListener()  {

			@Override
			public void onUpdate() {
				if(References.playerdata.contains(event.getPlayer().getUniqueId() + ".Logout")) player.teleport(References.playerdata.getLocation(event.getPlayer().getUniqueId() + ".Logout"));				
				PacketHandler.sendTitleMessage(player, ChatColor.GREEN  + "Darkbrook Island");
				PacketHandler.sendSubTitleMessage(player, References.getFormat(ChatColor.DARK_AQUA, ChatColor.AQUA, "Lvl: " + (References.playerdata.contains(player.getUniqueId() + ".level") ? References.playerdata.getInt(player.getUniqueId() + ".level") : 1)));
				player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 2.0F, 2.0F);
			}
			
		}, 1);

	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		References.playerdata.setLocation(event.getPlayer().getUniqueId() + ".Logout", event.getPlayer().getLocation());
	}
	
}
