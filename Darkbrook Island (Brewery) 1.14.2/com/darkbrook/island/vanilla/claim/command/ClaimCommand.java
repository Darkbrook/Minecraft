package com.darkbrook.island.vanilla.claim.command;

import java.text.MessageFormat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.command.CommandUser;
import com.darkbrook.island.common.registry.command.IRegistryCommand;
import com.darkbrook.island.vanilla.claim.data.ClaimChunkData;

public class ClaimCommand implements IRegistryCommand
{

	@Override
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length) 
	{
		return user.isSyntax(user.isPlayer() && length == 1, arguments, (u, a, l) -> onPlayerCommand((Player) u, a, l));
	}
	
	public boolean onPlayerCommand(Player player, String[] arguments, int length) 
	{
		ClaimChunkData claim = new ClaimChunkData(player.getLocation().getChunk());
		
		if(!claim.exists() && claim.save())
		{
			claim.setProvider(arguments[0]);
			player.sendMessage(MessageFormat.format("{0}Chunk {1}{2}{0}, {1}{3}{0} added to claim {1}{4}{0}.", ChatColor.YELLOW, ChatColor.GOLD, claim.getChunkX(), claim.getChunkZ(), claim.getProvider()));
		}
		else
		{
			player.sendMessage(MessageFormat.format("{0}The claim {1}{2}{0} already protects this chunk.", ChatColor.RED, ChatColor.DARK_RED, claim.getProvider()));
		}
		
		return true;
	}

	@Override
	public String getPrefix() 
	{
		return "claim";
	}
	
}
