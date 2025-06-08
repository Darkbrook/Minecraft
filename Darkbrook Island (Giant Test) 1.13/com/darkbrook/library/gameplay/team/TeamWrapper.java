package com.darkbrook.library.gameplay.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.darkbrook.library.chat.message.CustomMessage;

public abstract class TeamWrapper 
{

	private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	
	private Team team;

	public TeamWrapper(String team, String prefix)
	{
		this.team = scoreboard.getTeam(team);
		
		if(this.team == null)
		{
			this.team = scoreboard.registerNewTeam(team);
		}

		this.team.setPrefix(new CustomMessage(prefix).toString());
	}
	
	public void add(Player player) 
	{
		team.addEntry(player.getName());
		updateDisplayName(player, true);
	}
	
	public void remove(Player player) 
	{
		team.removeEntry(player.getName());
		updateDisplayName(player, false);
	}
	
	public void update(Player player) 
	{
		updateDisplayName(player, contains(player));
	}
	
	public boolean contains(Player player)
	{
		return team.hasEntry(player.getName());
	}
	
	private void updateDisplayName(Player player, boolean hasPrefix)
	{
		String name = hasPrefix ? team.getPrefix() + player.getName() : player.getName();
		player.setDisplayName(name);
		player.setPlayerListName(name);	
	}
	
}
