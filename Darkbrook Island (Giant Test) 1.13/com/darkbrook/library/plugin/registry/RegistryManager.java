package com.darkbrook.library.plugin.registry;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.darkbrook.library.chat.command.Command;
import com.darkbrook.library.chat.command.CommandRegistry;
import com.darkbrook.library.event.DarkbrookEvent;

public abstract class RegistryManager extends JavaPlugin
{
	
	private static final CommandRegistry COMMAND_REGISTRY = new CommandRegistry();
	
	public void executeEvent(DarkbrookEvent event)
	{
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public void register(IRegistryValue... values)
	{
		
		for(IRegistryValue value : values)
		{
			

			if(value instanceof Command)
			{
				registerCommand((Command) value);
				print(value, "Command", true);
			}
			else if(value instanceof Listener)
			{
				registerListener((Listener) value);
				print(value, "Listener", true);
			}
			else
			{
				print(value, "Unknown", true);
			}
			
		}
		
	}
	
	public void unregister(IRegistryValue... values)
	{
		
		for(IRegistryValue value : values) if(value instanceof Listener)
		{
			unregisterListener((Listener) value);
			print(value, "Listener", false);
		}
		
	}
	
	private void registerCommand(Command command) 
	{
		String name = command.getCommand();
		COMMAND_REGISTRY.addPrefix(name);
		
		getCommand(name).setExecutor(COMMAND_REGISTRY);	
		registerListener(command);
	}
	
	private void registerListener(Listener listener) 
	{
		getServer().getPluginManager().registerEvents(listener, this);	
	}
	
	private void unregisterListener(Listener listener)
	{
		HandlerList.unregisterAll(listener);		
	}
	
	private void print(IRegistryValue value, String type, boolean isRegistered)
	{
		getLogger().info((isRegistered ? "Registered " : "Unregistered ") + type + ": " + value.getClass().getSimpleName());
	}

}