package com.darkbrook.library.util.packet;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_13_R1.CraftParticle;

import com.darkbrook.library.util.helper.math.Vector3f;

import net.minecraft.server.v1_13_R1.Packet;
import net.minecraft.server.v1_13_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_13_R1.ParticleParam;

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
		updatePacket();
	}

	@Override
	protected Packet<?>[] onPacketLoad()
	{
		Packet<?> packet = new PacketPlayOutWorldParticles(particle, isForced, location.x, location.y, location.z, direction.x, direction.y, direction.z, speed, count);
		return new Packet<?>[] {packet};
	}

}
