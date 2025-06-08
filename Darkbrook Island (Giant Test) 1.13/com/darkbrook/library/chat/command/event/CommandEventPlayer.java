package com.darkbrook.library.chat.command.event;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.darkbrook.library.chat.message.ChatMessageError;
import com.darkbrook.library.chat.message.ChatMessageUsage;

public class CommandEventPlayer extends CommandEvent 
{

	public CommandEventPlayer(CommandSender sender, String[] arguments, String command) 
	{
		super(sender, arguments, command);
	}

	public Player getPlayer() 
	{
		return (Player) super.sender;
	}
	
	public boolean isValid(String command, boolean hasCondition, String... usage)
	{
		if(isCommand(command))
		{
			
			Player player = getPlayer();
			
			if(!player.isOp() && !player.hasPermission("darkbrook." + command))
			{
				sendMessage(new ChatMessageError("You do not have permission to execute this command."));
			}
			else if(!hasCondition)
			{
				sendMessage(new ChatMessageUsage(command, usage));
			}
			
			return hasCondition;
		}
		
		return false;
	}
	
}
