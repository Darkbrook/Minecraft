package com.darkbrook.library.chat.command;

import org.bukkit.event.Listener;

import com.darkbrook.library.plugin.registry.IRegistryValue;

public class Command implements Listener, IRegistryValue	
{
	
	protected String command;
	
	public Command(String command)
	{
		this.command = command;
	}
	
	public String getCommand()
	{
		return command;
	}

}
