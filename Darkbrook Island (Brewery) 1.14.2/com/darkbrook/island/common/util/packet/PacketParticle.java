package com.darkbrook.island.common.util.packet;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_14_R1.CraftParticle;

import com.darkbrook.island.common.util.helper.math.Vector3f;

import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_14_R1.ParticleParam;

public class PacketParticle extends PacketWrapper
{

	private ParticleParam particle;
	private Vector3f location;
	private Vector3f direction;
	private float speed;
	private int count;
	private boolean isForced;
		
	public PacketParticle(Particle particle, Vector3f direction, float speed, int count, boolean isForced)
	{
		this.particle = CraftParticle.toNMS(particle);
		this.direction = direction;
		this.speed = speed;
		this.count = count;
		this.isForced = isForced;
	}
	
	public void setLocation(Location location)
	{
		this.location = new Vector3f(location);
		update();
	}

	@Override
	protected Packet<?>[] onPacketLoad()
	{
		return new Packet<?>[] {new PacketPlayOutWorldParticles(particle, isForced, location.x, location.y, location.z, direction.x, direction.y, direction.z, speed, count)};
	}
	
}
