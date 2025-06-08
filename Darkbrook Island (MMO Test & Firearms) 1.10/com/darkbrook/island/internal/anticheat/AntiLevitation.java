package com.darkbrook.island.internal.anticheat;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.darkbrook.island.internal.anticheat.MovementHandler.MovementState;
import com.darkbrook.island.library.misc.LocationHandler;

import net.md_5.bungee.api.ChatColor;

public class AntiLevitation {
	
	private static boolean isMathFloating(Player player) {
		return MovementHandler.getVelocityMovementState(player) == MovementState.DOWN && MovementHandler.getCoordinateMovementState(player, AntiCheat.getFrom(player), AntiCheat.getTo(player)) != MovementState.DOWN;
	}
	
	private static boolean isBodyFloating(Player player) {
		int i = 0;
		for(Location location : LocationHandler.getPlatform(player, 1, 3)) if(location.getBlock().getType() == Material.AIR) i++;
		return i == 9;
	}
	
	public static void register(Player player) {
		
		if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
			if(isMathFloating(player) && isBodyFloating(player)) {
				AntiCheat.tellOp(ChatColor.RED + player.getName() + " may be using fly hacks.");
				AntiFly.addCatch(player);
			}
		}
		
	}

}
