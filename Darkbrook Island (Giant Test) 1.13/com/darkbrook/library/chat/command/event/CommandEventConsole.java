package com.darkbrook.library.chat.command.event;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandEventConsole extends CommandEvent 
{

	public CommandEventConsole(CommandSender sender, String[] arguments, String command) 
	{
		super(sender, arguments, command);
	}
	
	public ConsoleCommandSender getPlayer() 
	{
		return (ConsoleCommandSender) super.sender;
	}

}
