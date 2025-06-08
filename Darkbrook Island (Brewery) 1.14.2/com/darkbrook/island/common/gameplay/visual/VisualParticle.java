package com.darkbrook.island.common.gameplay.visual;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.visual.global.IRegistryVisualGlobal;
import com.darkbrook.island.common.util.helper.math.Vector3f;
import com.darkbrook.island.common.util.packet.PacketParticle;


public class VisualParticle implements IRegistryVisualGlobal
{
		
	private PacketParticle particle;
	private boolean isCentered;
	
	public VisualParticle(Particle particle, Vector3f direction, float speed, int count, boolean isForced, boolean isCentered)
	{
		this.particle = new PacketParticle(particle, direction, speed, count, isForced);
		this.isCentered = isCentered;
	}
	
	public VisualParticle(Particle particle, float direction, float speed, int count, boolean isForced, boolean isCentered)
	{
		this(particle, new Vector3f(direction, direction, direction), speed, count, isForced, isCentered);
	}

	@Override
	public void playLocal(Player player) 
	{
		particle.setLocation(centerLocation(player.getLocation()));
		particle.send(player);
	}

	@Override
	public void playGlobal(Player player) 
	{
		playGlobal(player.getLocation());		
	}

	@Override
	public void playGlobal(Location location) 
	{
		particle.setLocation(centerLocation(location));
		particle.send();		
	}
	
	private Location centerLocation(Location location)
	{
		return isCentered ? location.clone().add(0.5d, 0.5d, 0.5d) : location;
	}
	
}
