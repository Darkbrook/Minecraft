package com.darkbrook.library.chat.command.event;

import org.bukkit.command.CommandSender;

import com.darkbrook.library.chat.message.ChatMessage;
import com.darkbrook.library.event.DarkbrookEvent;

public class CommandEvent extends DarkbrookEvent
{
	protected CommandSender sender; 
	protected String[] arguments;
	protected String command;

	public CommandEvent(CommandSender sender, String[] arguments, String command) 
	{
		this.sender = sender;
		this.arguments = arguments;
		this.command = command;
	}
	
	public void sendMessage(ChatMessage message)
	{
		message.sendMessage(sender);
	}
	
	public void sendMessage(CommandSender sender, ChatMessage message)
	{
		message.sendMessage(sender);
	}
	
	public CommandEventTarget getCommandEventTarget(int targetIndex, int targetLength) 
	{
		return new CommandEventTarget(sender, arguments, targetIndex, targetLength);
	}
	
	public String[] getArguments() 
	{
		return arguments;
	}
	
	public String getCommand() 
	{
		return command;
	}
	
	public boolean isCommand(String command) 
	{
		return this.command.equalsIgnoreCase(command);
	}

}
