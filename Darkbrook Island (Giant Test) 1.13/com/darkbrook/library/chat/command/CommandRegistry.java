package com.darkbrook.library.chat.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.darkbrook.library.chat.command.event.CommandEventBlock;
import com.darkbrook.library.chat.command.event.CommandEventConsole;
import com.darkbrook.library.chat.command.event.CommandEventPlayer;
import com.darkbrook.library.plugin.DarkbrookPlugin;

public class CommandRegistry implements CommandExecutor
{

	private List<String> prefixes;	
	
	public CommandRegistry() 
	{
		this.prefixes = new ArrayList<String>();
	}
	
	public void addPrefix(String prefix) 
	{
		prefixes.add(prefix);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String prefix, String[] arguments) 
	{
		if(prefixes.contains(prefix)) 
		{
			DarkbrookPlugin.executeEventStatic(sender instanceof Player ? new CommandEventPlayer(sender, arguments, prefix) : (sender instanceof BlockCommandSender ? new CommandEventBlock(sender, arguments, prefix) : new CommandEventConsole(sender, arguments, prefix)));		
		}
		
		return false;
	}

}
