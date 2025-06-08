package com.darkbrook.island.common.util.packet;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.util.helper.EntityHelper;
import com.darkbrook.island.common.util.helper.NmsHelper;

import net.minecraft.server.v1_14_R1.Packet;
import net.minecraft.server.v1_14_R1.PlayerConnection;

public abstract class PacketWrapper
{

	private List<Packet<?>> packets;
	
	public void send(Location location, int radius)
	{
		send(EntityHelper.radial(Player.class, location, radius));
	}
	
	public void send(List<Player> players)
	{
		players.forEach(this::send);
	}
	
	public void send(Player player)
	{
		PlayerConnection connection = NmsHelper.player(player).playerConnection;
		packets.forEach(connection::sendPacket);
	}
	
	public void send()
	{
		Bukkit.getOnlinePlayers().forEach(this::send);
	}
	
	public void update()
	{
		packets = Arrays.asList(onPacketLoad());
	}
	
	protected abstract Packet<?>[] onPacketLoad();
	
}
