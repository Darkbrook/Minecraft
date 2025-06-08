package com.darkbrook.library.util.packet;

import org.bukkit.Location;
import org.bukkit.Material;

import com.darkbrook.library.util.helper.NmsHelper;

import net.minecraft.server.v1_13_R1.Block;
import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.IBlockData;
import net.minecraft.server.v1_13_R1.Packet;
import net.minecraft.server.v1_13_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_13_R1.World;

public class PacketBlockChange extends PacketWrapper
{
	
	private World world;
	private BlockPosition position;
	private IBlockData block;
	
	public PacketBlockChange(Location location, Material material)
	{
		this.world = NmsHelper.world(location);
		this.position = NmsHelper.position(location);
		this.block = Block.getByName(material.name().toLowerCase()).getBlockData();
		
		updatePacket();
	}
	
	public PacketBlockChange(Location location)
	{
		this(location, location.getBlock().getType());
	}

	@Override
	protected Packet<?>[] onPacketLoad()
	{
		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(world, position);
		packet.block = block;
		return new Packet<?>[] {packet};
	}
	
}
