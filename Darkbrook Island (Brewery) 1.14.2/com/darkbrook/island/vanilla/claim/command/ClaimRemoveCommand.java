package com.darkbrook.island.vanilla.claim.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.command.CommandUser;
import com.darkbrook.island.common.registry.command.IRegistryCommand;
import com.darkbrook.island.vanilla.claim.data.ClaimChunkData;
import com.darkbrook.island.vanilla.claim.data.ClaimProviderData;

public class ClaimRemoveCommand implements IRegistryCommand
{

	@Override
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length) 
	{
		return user.isSyntax(user.isPlayer() && length == 1, arguments, (u, a, l) -> onPlayerCommand((Player) u, a, l));
	}
	
	public boolean onPlayerCommand(Player player, String[] arguments, int length) 
	{
		ClaimChunkData claim = new ClaimChunkData(player.getLocation().getChunk());
		String providerName;
		
		if((providerName = claim.getProvider()) == null)
		{
			player.sendMessage(ChatColor.RED + "The chunk you are currently in does not belong to an existing claim.");
			return true;
		}
		
		ClaimProviderData provider = new ClaimProviderData(providerName);
		String playerName = arguments[0];
		
		if(!provider.hasProvider(playerName))
		{
			player.sendMessage(ChatColor.RED + "The specified player is already missing from the claim you are currently in.");
			return true;
		}
		
		provider.removeProvider(playerName);
		player.sendMessage(ChatColor.GOLD + playerName + ChatColor.YELLOW + " has been removed from the claim " + ChatColor.GOLD + providerName + ChatColor.YELLOW + ".");
		return true;
	}

	@Override
	public String getPrefix() 
	{
		return "claimremove";
	}

}
