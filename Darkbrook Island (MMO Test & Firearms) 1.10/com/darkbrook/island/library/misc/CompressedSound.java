package com.darkbrook.island.library.misc;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CompressedSound {
	
	public Sound sound;
	public float volume;
	public float pitch;
	
	public CompressedSound(Sound sound, float volume, float pitch) {
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	public void playSound(Location location) {
		location.getWorld().playSound(location, sound, volume, pitch);
	}
	
	public void playSound(Player player, boolean local) {
		if(local) player.playSound(player.getLocation(), sound, volume, pitch); else playSound(player.getLocation());
	}

}
