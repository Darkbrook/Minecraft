package com.darkbrook.anticheat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.darkbrookisland.DarkbrookIsland;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.config.MinecraftConfig;
import com.darkbrook.library.message.FormatMessage;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.library.plugin.PluginGrabber;

public class AntiCheat implements Listener {
			
	private static final Map<Player, Location> LOCATION_LAST = new HashMap<Player, Location>();
	private static final Map<Player, Location> LOCATION_NOW = new HashMap<Player, Location>();
	private static final CompressedSound ALERT_SOUND = new CompressedSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.0F);

	private static MinecraftConfig playerdata = DarkbrookIsland.playerdata;
	
	public static void register() {
		
		Plugin plugin = PluginGrabber.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(new AntiCheat(), plugin);
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {

				for(Player player : Bukkit.getOnlinePlayers()) {
					LOCATION_LAST.put(player, LOCATION_NOW.containsKey(player) ? LOCATION_NOW.get(player) : player.getLocation());
					LOCATION_NOW.put(player, player.getLocation());
					AntiFly.register(player, getFrom(player), getTo(player));
				}
				
			}
			
		}, 0, 10);
		
	}
	
	public static Location getFrom(Player player) {
		return LOCATION_LAST.containsKey(player) ? LOCATION_LAST.get(player) : player.getLocation();
	}
	
	public static Location getTo(Player player) {
		return LOCATION_NOW.containsKey(player) ? LOCATION_NOW.get(player) : player.getLocation();
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
				
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		String ip = playerdata.containsKey(uuid + ".ip") ? playerdata.getString(uuid + ".ip") : event.getAddress().getHostName();
		
		ip = ip.replace(".", "-");
		
		if(playerdata.containsKey(ip + ".ipbanned")) {
			
			String banMessage = playerdata.getString(ip + ".ipbanned");
			event.setKickMessage(banMessage);
			if(!playerdata.containsKey(uuid + ".banned")) addPersonalBan(player, ip, banMessage);	
			
			playerdata.setString(uuid + ".ip", ip.replace(".", "-"));
			
		} else if(playerdata.containsKey(uuid + ".banned")) {
			
			String banMessage = DarkbrookIsland.playerdata.getString(event.getPlayer().getUniqueId() + ".banned");
			event.setKickMessage(banMessage);		
			
			playerdata.setString(uuid + ".ip", ip.replace(".", "-"));	
			
		}
		
	}
	
	public static void alertOperator(Player target, String hack) {
	
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!player.isOp()) continue;			
			player.sendMessage(ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + target.getName() + " may be using " + hack + " hacks.");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + target.getName() + " may be using " + hack + " hacks.");
			ALERT_SOUND.play(player, true);
		}
		
	}
	
	public static void banIp(Player player, String banReason) {
		String banMessage = FormatMessage.getBanMessage(ChatColor.GOLD + "[AntiCheat] " + banReason);
		String ip = player.getAddress().getHostName();
		banIp(ip, banMessage, banReason);
	}
	
	private static void banIp(String ip, String banMessage, String banReason) {
		
		playerdata.setString(ip.replace(".", "-") + ".ipbanned", banMessage);
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(players.getAddress().getHostString().equals(ip)) {
				addPersonalBan(players, players.getAddress().getHostName(), banMessage);
				Bukkit.broadcastMessage(ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + "Banned " + players.getName() + ", " + banReason);
			}
		}
		
	}
	
	private static void addPersonalBan(Player player, String address, String banMessage) {
		playerdata.setString(player.getUniqueId() + ".banned", banMessage);
		Bukkit.getBanList(Type.NAME).addBan(player.getName(), banMessage, null, null);
		Bukkit.getBanList(Type.IP).addBan(address, banMessage, null, ChatColor.GOLD + "[AntiCheat]");
		player.kickPlayer(banMessage);
	}
	
}
