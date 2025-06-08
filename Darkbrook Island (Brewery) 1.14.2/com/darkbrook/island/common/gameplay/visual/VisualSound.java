package com.darkbrook.island.common.gameplay.visual;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.visual.global.IRegistryVisualGlobal;

public class VisualSound implements IRegistryVisualGlobal
{
	
	public Sound sound;
	public float volume;
	public float pitch;
	
	public VisualSound(Sound sound, float volume, float pitch) 
	{
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	public void playLocal(Player player) 
	{
		player.playSound(player.getLocation(), sound, volume, pitch);
	}

	@Override
	public void playGlobal(Player player) 
	{
		playGlobal(player.getLocation());			
	}

	@Override
	public void playGlobal(Location location) 
	{
		location.getWorld().playSound(location, sound, volume, pitch);
	}
	
}
