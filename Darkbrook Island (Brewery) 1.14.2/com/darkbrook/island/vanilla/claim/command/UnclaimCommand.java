package com.darkbrook.island.vanilla.claim.command;

import java.text.MessageFormat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.command.CommandUser;
import com.darkbrook.island.common.registry.command.IRegistryCommand;
import com.darkbrook.island.vanilla.claim.data.ClaimChunkData;

public class UnclaimCommand implements IRegistryCommand
{

	@Override
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length) 
	{
		return user.isSyntax(user.isPlayer() && length == 0, arguments, (u, a, l) -> onPlayerCommand((Player) u, a, l));
	}
	
	public boolean onPlayerCommand(Player player, String[] arguments, int length) 
	{
		ClaimChunkData claim = new ClaimChunkData(player.getLocation().getChunk());
		String provider = claim.getProvider();
		
		if(claim.delete())
		{
			player.sendMessage(MessageFormat.format("{0}Chunk {1}{2}{0}, {1}{3}{0} removed from claim {1}{4}{0}.", ChatColor.YELLOW, ChatColor.GOLD, claim.getChunkX(), claim.getChunkZ(), provider));
		}
		else
		{
			player.sendMessage(ChatColor.RED + "No claim currently protects this chunk.");
		}
		
		return true;
	}

	@Override
	public String getPrefix() 
	{
		return "unclaim";
	}

}
