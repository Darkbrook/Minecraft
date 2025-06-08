package com.darkbrook.apoc;

import java.net.InetAddress;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.darkbrook.island.gameplay.player.PlayerProfileGeneric;
import com.darkbrook.library.data.config.ConfigMinecraft;
import com.darkbrook.library.plugin.PluginDate;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.ResourceLocation;

public class GenericListener implements Listener, IRegistryValue
{
		
	@EventHandler
	public void onAsyncPlayerLogin(AsyncPlayerPreLoginEvent event)
	{
		String uuid = event.getUniqueId().toString();
		ConfigMinecraft config = new ConfigMinecraft(new ResourceLocation("$data/player/" + uuid + ".txt"));
		
		InetAddress address = event.getAddress();
		
		config.setData(new Object[]
		{
			event.getName(),
			address.getHostAddress(),
			address.getHostName(),
			PluginDate.getDate()
		}, 
		uuid, "username/-address/hostname/lastjoin");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) 
	{
		event.setJoinMessage(PlayerProfileGeneric.getMapping(event.getPlayer()).join());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		event.setQuitMessage(PlayerProfileGeneric.getMapping(event.getPlayer()).quit());
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		event.setFormat(ChatColor.GRAY + player.getDisplayName() + ": %2$s");
		event.setMessage(ChatColor.WHITE + event.getMessage());
	}

}
