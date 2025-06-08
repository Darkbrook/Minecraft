package com.darkbrook.island.common.registry.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUser
{
	
	private CommandSender sender;
		
	public CommandUser(CommandSender sender) 
	{
		this.sender = sender;
	}

	public boolean isPlayer()
	{
		return sender instanceof Player;
	}
	
	public boolean isSyntax(boolean isSyntax, String[] arguments, ICommandProfile<CommandSender, String[], Integer, Boolean> profile)
	{
		return isSyntax ? profile.apply(sender, arguments, arguments.length) : false;
	}
	
}
