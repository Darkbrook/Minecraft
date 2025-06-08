package com.darkbrook.island.internal.anticheat;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.References;
import com.darkbrook.island.internal.anticheat.MovementHandler.MovementState;

import net.md_5.bungee.api.ChatColor;

public class AntiFly {
		
	public static final HashMap<Player, Integer> CATCHES = new HashMap<Player, Integer>();
	public static final HashMap<Player, Integer> WARNINGS = new HashMap<Player, Integer>();
	public static final int CATCH_LIMIT = 4;
	public static final int WARN_LIMIT = 8;
	
	private static boolean cancle(Player player, Location from, Location to) {		
		return !player.hasPotionEffect(PotionEffectType.LEVITATION) && MovementHandler.hasMoved(from, to) && MovementHandler.getVelocityMovementState(player) != MovementState.STAGNANT && MovementHandler.getVelocityMovementState(player) != MovementHandler.getCoordinateMovementState(player, from, to);
	}
	
	public static void addCatch(Player player) {
		CATCHES.put(player, CATCHES.containsKey(player) ? (CATCHES.get(player) + 1) : 1);
	}
	
	public static boolean register(Player player, Location from, Location to) {
		
		WARNINGS.put(player, cancle(player, from, to) ? (WARNINGS.containsKey(player) ? WARNINGS.get(player) + 1 : 1) : 0);
		
		if(WARNINGS.get(player) > WARN_LIMIT) {
			AntiCheat.tellOp(ChatColor.RED + player.getName() + " may be using fly hacks.");
			WARNINGS.remove(player);
			addCatch(player);
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static void registerAutoBan(Player player) {
		
		AntiFly.CATCHES.put(player, AntiFly.CATCHES.containsKey(player) && AntiFly.CATCHES.get(player) > 1 ? AntiFly.CATCHES.put(player, AntiFly.CATCHES.get(player) - 1) : 0);
		if(AntiFly.CATCHES.containsKey(player) && AntiFly.CATCHES.get(player) > CATCH_LIMIT){
			player.setBanned(true);
			player.kickPlayer(ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + "AntiFly Violation Reached: (" + AntiFly.CATCHES.get(player) + " > " + AntiFly.CATCH_LIMIT + ")");
			Bukkit.broadcastMessage(ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + "Banned " + player.getName() + ", AntiFly Violation Reached: (" + AntiFly.CATCHES.get(player) + " > " + AntiFly.CATCH_LIMIT + ")");
			References.playerdata.set(player.getUniqueId() + ".banned", ChatColor.GOLD + "[AntiCheat] " + ChatColor.RED + "AntiFly Violation Reached: (" + AntiFly.CATCHES.get(player) + " > " + AntiFly.CATCH_LIMIT + ")");
		}
		
	}

}
