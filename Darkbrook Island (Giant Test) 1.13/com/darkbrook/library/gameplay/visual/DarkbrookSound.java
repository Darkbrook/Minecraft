package com.darkbrook.library.gameplay.visual;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DarkbrookSound extends DarkbrookMultiVisual 
{
	
	public Sound sound;
	public float volume;
	public float pitch;
	
	public DarkbrookSound(Sound sound, float volume, float pitch) 
	{
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	public void play(Player player, boolean isLocal) 
	{
		
		if(isLocal) 
		{
			player.playSound(player.getLocation(), sound, volume, pitch);
		}
		else
		{
			play(player.getLocation());	
		}
		
	}

	@Override
	public void play(Location location) 
	{
		location.getWorld().playSound(location, sound, volume, pitch);
	}
	
}
