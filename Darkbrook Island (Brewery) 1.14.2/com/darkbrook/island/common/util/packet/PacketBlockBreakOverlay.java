package com.darkbrook.island.common.util.packet;

import org.bukkit.Location;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;

public class PacketBlockBreakOverlay extends PacketWrapper
{
	
	private BlockPosition position;
	private int damage;
	private int identity;

	
	public PacketBlockBreakOverlay(Location location, int damage)
	{
		this.damage = damage;
		
		position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		identity = String.valueOf(position.asLong()).hashCode();

		update();
	}
	
	public PacketBlockBreakOverlay(Location location, int damage, int identity)
	{
		this.damage = damage;
		this.identity = identity;
		
		position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		update();
	}
	
	@Override
	protected Packet<?>[] onPacketLoad()
	{ 
		return new Packet[] {new PacketPlayOutBlockBreakAnimation(identity, position, damage)};
	}

}
