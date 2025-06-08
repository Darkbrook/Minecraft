package com.darkbrook.library.chat.command.event;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;

public class CommandEventBlock extends CommandEvent 
{

	public CommandEventBlock(CommandSender sender, String[] arguments, String command) 
	{
		super(sender, arguments, command);
	}
	
	public BlockCommandSender getCommandBlock() 
	{
		return (BlockCommandSender) super.sender;
	}

}
