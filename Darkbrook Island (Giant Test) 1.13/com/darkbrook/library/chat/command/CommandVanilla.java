package com.darkbrook.library.chat.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVanilla 
{
	
	public static void execute(Player player, String command) 
	{
		execute(player, player.getWorld(), command);
	}
	
	public static void execute(World world, String command) 
	{
		execute(Bukkit.getConsoleSender(), world, command);
	}
	
	private static void execute(CommandSender sender, World world, String command) 
	{
		
		boolean isFeedbackSent = world.getGameRuleValue("sendCommandFeedback").equals("true");
		
		if(isFeedbackSent) 
		{
			world.setGameRuleValue("sendCommandFeedback", "false");
		}
		
		Bukkit.getServer().dispatchCommand(sender, command);
		
		if(isFeedbackSent) 
		{
			world.setGameRuleValue("sendCommandFeedback", "true");
		}
		
	}

}
