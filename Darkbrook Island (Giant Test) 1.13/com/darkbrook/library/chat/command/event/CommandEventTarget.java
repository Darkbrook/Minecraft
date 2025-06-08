package com.darkbrook.library.chat.command.event;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEventTarget 
{
	
	private CommandSender sender;
	private Player receiver;
	private boolean isSelfSend;

	public CommandEventTarget(CommandSender sender, String[] arguments, int targetIndex, int targetLength) 
	{
		this.sender = sender;
		this.receiver = arguments.length == targetLength ? Bukkit.getPlayer(arguments[targetIndex]) : (sender instanceof Player ? (Player) sender : null);
		this.isSelfSend = sender instanceof Player && sender == receiver;
	}

	public CommandSender getSender() 
	{
		return sender;
	}

	public Player getReceiver() 
	{
		return receiver;
	}

	public boolean isSelfSend() 
	{
		return isSelfSend;
	}
	
}
