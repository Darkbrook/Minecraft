package com.darkbrook.island.common.registry.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.darkbrook.island.common.registry.RegistryManager;
import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.action.IRegistryAction;

public class CommandRegistry extends RegistryManager<IRegistryCommand> implements CommandExecutor, IRegistryAction
{

	private Map<String, IRegistryCommand> commandMapping;
	
	public CommandRegistry() 
	{
		super(IRegistryCommand.class);
		commandMapping = new HashMap<String, IRegistryCommand>();
	}

	@Override
	public void onEnable(RegistryPlugin plugin) 
	{
		values.forEach(command ->
		{
			plugin.register(command);
			commandMapping.put(command.getPrefix(), command);
		});
	}

	@Override
	public void onDisable(RegistryPlugin plugin) 
	{
		values.forEach(plugin::unregister);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String prefix, String[] arguments) 
	{
		IRegistryCommand registry = commandMapping.get(prefix);
		
		if(registry != null)
		{
			command.setPermissionMessage(ChatColor.RED + command.getPermissionMessage());
			command.setUsage(ChatColor.RED + command.getUsage());
		}
		
		return registry != null ? registry.onCommandExecute(new CommandUser(sender), arguments, arguments.length) : true;
	}
	
}
