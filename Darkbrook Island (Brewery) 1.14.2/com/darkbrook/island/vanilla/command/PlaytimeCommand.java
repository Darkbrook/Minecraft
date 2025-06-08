package com.darkbrook.island.vanilla.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import com.darkbrook.island.common.gameplay.event.PlayerUpdateEvent;
import com.darkbrook.island.common.registry.command.CommandUser;
import com.darkbrook.island.common.registry.command.IRegistryCommand;
import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.common.util.helper.TimeHelper;
import com.darkbrook.island.vanilla.playerdata.PlayerData;

public class PlaytimeCommand implements IRegistryCommand, IRegistryHandler
{
	
	private Map<Player, Long> cacheMapping;
	private PlayerData playerData;

	public PlaytimeCommand()
	{
		cacheMapping = new HashMap<Player, Long>();
		playerData = PlayerData.getInstance();
	}

	@Override
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length) 
	{
		return user.isSyntax(user.isPlayer() && length == 0, arguments, (u, a, l) -> onPlayerCommand((Player) u, a, l));
	}
	
	public boolean onPlayerCommand(Player player, String[] arguments, int length)
	{
		playerData.setPlayer(player);
		player.sendMessage(ChatColor.YELLOW + "You have a playtime of " + getPlaytime(playerData.getPlaytime()) + ".");
		return true;
	}

	@EventHandler
	public void onPlayerUpdate(PlayerUpdateEvent event)
	{
		Player player = event.getPlayer();
		long currentMs = System.currentTimeMillis();

		playerData.setPlayer(player);
		playerData.setPlaytime(playerData.getPlaytime() + (cacheMapping.containsKey(player) ? (currentMs - (cacheMapping.get(player))) : 0));
		cacheMapping.put(player, currentMs);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) 
	{
		cacheMapping.remove(event.getPlayer());
	}

	@Override
	public String getPrefix() 
	{
		return "playtime";
	}
	
	private String getPlaytime(long playtime)
	{
		String[] values = TimeHelper.stringArray(playtime, 1000, 60, 60, 24, 7);
		String[] suffixes = {"w", "d", "h", "m", "s"};
		String value;
		String formatted = "";
		
		for(int i = values.length - 1; i >= 0; i--) if((value = values[i]) != null)
		{
			formatted += ChatColor.GOLD + value + ChatColor.YELLOW + suffixes[values.length - 1 - i] + (i != 0 ? ", " : "");
		}
		
		return formatted;
	}
	
}
