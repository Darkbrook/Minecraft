package com.darkbrook.anticheat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.anticheat.MovementHandler.MovementState;
import com.darkbrook.library.location.LocationHandler;

public class AntiFly {
	
	public static final WarnCatchBan WARN_CATCH_BAN = new WarnCatchBan(8, 4, 4);
	
	public static void register(Player player, Location from, Location to) {
		WARN_CATCH_BAN.register(player, ChatColor.RED + "Fly Violation ({catches} > {catch_limit})", "fly", isAntiCheatTriggered(player, from, to));
	}
	
	private static boolean isAntiCheatTriggered(Player player, Location from, Location to) {		
		GameMode mode = player.getGameMode();
		if(mode == GameMode.CREATIVE || mode == GameMode.SPECTATOR || (!isFlying(player, from, to) && !isHovering(player))) return false;
		return true;
	}
	
	private static boolean isFlying(Player player, Location from, Location to) {
		if((player.hasPotionEffect(PotionEffectType.LEVITATION) || !MovementHandler.hasMoved(from, to) || MovementHandler.getVelocityMovementState(player) == MovementState.STAGNANT || MovementHandler.getVelocityMovementState(player) == MovementHandler.getCoordinateMovementState(player, from, to))) return false;
		String blockIn = player.getLocation().getBlock().getType().toString().toLowerCase();
		if(blockIn.contains("water") || !blockIn.contains("lava") || !blockIn.contains("vine")) return false;
		return true;
	}
	
	public static boolean isHovering(Player player) {
		 if(!isMathFloating(player) || !isBodyFloating(player)) return false;
		 return true;
	}
	
	private static boolean isMathFloating(Player player) {
		return MovementHandler.getVelocityMovementState(player) == MovementState.DOWN && MovementHandler.getCoordinateMovementState(player, AntiCheat.getFrom(player), AntiCheat.getTo(player)) != MovementState.DOWN;
	}
	
	private static boolean isBodyFloating(Player player) {
		
		List<Location> locations = new ArrayList<Location>();
		int locationsAir = 0;
		
		for(int i = 1; i < 4; i++) locations.addAll(LocationHandler.getPlatform(player, 1, i));
		for(Location location : locations) if(location.getBlock().getType() == Material.AIR) locationsAir++;
		
		return locationsAir == locations.size();
		
	}
	
}
