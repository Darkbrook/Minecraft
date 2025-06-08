package com.darkbrook.island.internal.anticheat;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MovementHandler {
	
	public enum MovementState {
		STAGNANT, UP, DOWN;
	}
		
	private static double getVelocityY(Player player) {
		return player.getVelocity().getY();
	}
	
	public static boolean hasMoved(Location from, Location to) {
		return from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ();
	}
	
	public static MovementState getVelocityMovementState(Player player) {	
		return getVelocityY(player) == -0.0784000015258789 ? MovementState.STAGNANT : (getVelocityY(player) <= -0.1 ? MovementState.DOWN : (getVelocityY(player) >= 0.001 ? MovementState.UP : MovementState.STAGNANT));
	}	
	
	public static MovementState getCoordinateMovementState(Player player, Location from, Location to) {
		double ydif = to.getY() - from.getY();
		return ydif == 0.0 ? MovementState.STAGNANT : (ydif > 0 ? MovementState.UP : MovementState.DOWN);
	}

}
