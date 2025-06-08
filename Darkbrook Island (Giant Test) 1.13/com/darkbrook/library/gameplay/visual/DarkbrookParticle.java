package com.darkbrook.library.gameplay.visual;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import com.darkbrook.library.util.helper.math.Vector3f;
import com.darkbrook.library.util.packet.PacketParticle;


public class DarkbrookParticle extends DarkbrookMultiVisual 
{
		
	private PacketParticle particle;
	private boolean isCentered;
	
	public DarkbrookParticle(Particle particle, Vector3f direction, float speed, int count, boolean isForced, boolean isCentered)
	{
		this.particle = new PacketParticle(particle, direction, speed, count, isForced);
		this.isCentered = isCentered;
	}
	
	public DarkbrookParticle(Particle particle, float direction, float speed, int count, boolean isForced, boolean isCentered)
	{
		this(particle, new Vector3f(direction, direction, direction), speed, count, isForced, isCentered);
	}

	@Override
	public void play(Player player, boolean isLocal) 
	{
		
		if(isLocal)
		{
			particle.setLocation(attemptCenter(player.getLocation()));
			particle.send(player);
		}
		else
		{
			play(player.getLocation());
		}
		
	}

	@Override
	public void play(Location location) 
	{
		particle.setLocation(attemptCenter(location));
		particle.send();
	}
	
	private Location attemptCenter(Location location)
	{
		return isCentered ? location.clone().add(0.5, 0.5, 0.5) : location;
	}
	
}
