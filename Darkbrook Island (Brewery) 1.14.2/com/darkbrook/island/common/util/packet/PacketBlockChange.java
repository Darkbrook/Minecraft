package com.darkbrook.island.common.util.packet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;

import com.darkbrook.island.common.util.helper.NmsHelper;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.IBlockData;
import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_14_R1.World;

public class PacketBlockChange extends PacketWrapper
{
	
	private World world;
	private BlockPosition position;
	private IBlockData block;
	
	public PacketBlockChange(Location location, BlockData data)
	{
		this.world = NmsHelper.world(location);
		this.position = NmsHelper.position(location);
		this.block = ((CraftBlockData) data).getState();
		
		update();
	}
	
	public PacketBlockChange(Location location, Material material)
	{
		this(location, material.createBlockData());
	}
		
	public PacketBlockChange(Location location)
	{
		this(location, location.getBlock().getBlockData());
	}

	@Override
	protected Packet<?>[] onPacketLoad()
	{
		PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(world, position);
		packet.block = block;
		return new Packet<?>[] {packet};
	}
	
}
