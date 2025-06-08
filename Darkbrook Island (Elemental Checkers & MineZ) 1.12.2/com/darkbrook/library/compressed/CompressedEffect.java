package com.darkbrook.library.compressed;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class CompressedEffect {
	
	private Effect effect;
	private Material material;
	private float xOff;
	private float yOff;
	private float zOff;
	private float speed;
	private int data;
	private int count;

	public CompressedEffect(Effect effect, float xOff, float yOff, float zOff, float speed, int count) {
		this.effect = effect;
		this.xOff = xOff;
		this.yOff = yOff;
		this.zOff = zOff;
		this.speed = speed;
		this.count = count;
	}
	
	public CompressedEffect(Material material, int data, float xOff, float yOff, float zOff, float speed, int count) {
		this.material = material;
		this.xOff = xOff;
		this.yOff = yOff;
		this.zOff = zOff;
		this.speed = speed;
		this.data = data;
		this.count = count;
	}

	@SuppressWarnings("deprecation")
	public void play(Location location) {		
		
		Location effectLocation = new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY() + 0.5, location.getBlockZ() + 0.5);
		World world = location.getWorld();
		
		if(effect != null) {
			world.spigot().playEffect(effectLocation, effect, 0, 0, xOff, yOff, zOff, speed, count, 100);
		} else if(material != null) {
			world.spigot().playEffect(effectLocation, Effect.TILE_BREAK, material.getId(), data, xOff, yOff, zOff, speed, count, 100);
		}
		
	}

}
