package com.darkbrook.island.gameplay.player;

import org.bukkit.entity.Player;

import com.darkbrook.library.chat.message.CustomMessage;
import com.darkbrook.library.data.config.ConfigMinecraft;
import com.darkbrook.library.gameplay.player.PlayerMapping;
import com.darkbrook.library.plugin.PluginDate;
import com.darkbrook.library.util.ResourceLocation;

public class PlayerProfileGeneric extends PlayerMapping
{
	
	public static PlayerProfileGeneric getMapping(Player player)
	{
		return PlayerMapping.getMapping(PlayerProfileGeneric.class, player);
	}
	
	private ConfigMinecraft config;
	private String join;
	private String quit;
	
	public PlayerProfileGeneric(Player player)
	{
		super(player);
		
		config = new ConfigMinecraft(new ResourceLocation("$data/player/" + player.getUniqueId().toString() + ".txt"));
		join = new CustomMessage("{$gold}", player.getName(), "{$yellow} has awoken from their slumber.").toString();
		quit = new CustomMessage("{$gold}", player.getName(), "{$yellow} has fell into a deep slumber.").toString();
	}
	
	public String join()
	{
		player.setPlayerListName(new CustomMessage("{$gray}", player.getName()).toString());
		return join;
	}
	
	public String quit()
	{
		config.setValue(player.getUniqueId() + ".lastquit", PluginDate.getDate());
		return quit;
	}
	
}
