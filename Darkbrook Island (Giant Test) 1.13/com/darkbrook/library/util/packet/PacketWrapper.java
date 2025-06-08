package com.darkbrook.library.util.packet;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.darkbrook.library.util.helper.EntityHelper;
import com.darkbrook.library.util.helper.NmsHelper;

import net.minecraft.server.v1_13_R1.Packet;
import net.minecraft.server.v1_13_R1.PlayerConnection;

public abstract class PacketWrapper
{

	private Packet<?>[] packets;

	public void send(List<Player> players)
	{
		for(Player player : players) send(player);
	}
	
	public void send(Player player)
	{
		
		PlayerConnection connection = NmsHelper.player(player).playerConnection;
		
		for(Packet<?> packet : packets) 
		{
			connection.sendPacket(packet);
		}
		
	}
	
	public void send(Location location, int radius)
	{
		send(EntityHelper.radial(Player.class, location, radius));
	}
	
	public void send()
	{
		for(Player player : Bukkit.getOnlinePlayers()) send(player);
	}
	
	public void updatePacket()
	{
		packets = onPacketLoad();
	}
	
	protected abstract Packet<?>[] onPacketLoad();
	
}
