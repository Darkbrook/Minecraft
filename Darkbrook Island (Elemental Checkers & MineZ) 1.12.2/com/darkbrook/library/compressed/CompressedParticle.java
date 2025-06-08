package com.darkbrook.library.compressed;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class CompressedParticle {
	
	private Particle particle;
	private float xOff;
	private float yOff;
	private float zOff;
	private int count;

	public CompressedParticle(Particle particle, float xOff, float yOff, float zOff, int count) {
		this.particle = particle;
		this.xOff = xOff;
		this.yOff = yOff;
		this.zOff = zOff;
		this.count = count;
	}
	
	public void play(Location location) {		
		Location particleLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.5, location.getBlockZ() + 0.5);
		location.getWorld().spawnParticle(particle, particleLocation, count, xOff, yOff, zOff);
	}
	
	public void play(Player player, boolean isLocal) {
		
		Location location = player.getLocation();
		Location particleLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.5, location.getBlockZ() + 0.5);
		
		if(isLocal) {
			player.spawnParticle(particle, particleLocation, count, xOff, yOff, zOff);
		} else {
			location.getWorld().spawnParticle(particle, particleLocation, count, xOff, yOff, zOff);
		}
		
	}

}
