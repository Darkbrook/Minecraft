package com.darkbrook.island.vanilla.command;

import java.io.File;

import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.RegistryPlugin;
import com.darkbrook.island.common.registry.command.CommandUser;
import com.darkbrook.island.common.registry.command.IRegistryCommand;

public class WorldCommand implements IRegistryCommand
{
	
	@Override
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length) 
	{
		return user.isSyntax(user.isPlayer() && length == 1, arguments, (u, a, l) -> onPlayerCommand((Player) u, a, l));
	}
	
	public boolean onPlayerCommand(Player player, String[] arguments, int length)
	{
		File world = getWorldFile(arguments[0]);
		return true;
	}

	@Override
	public String getPrefix() 
	{
		return "world";
	}
	
	private File getWorldFile(String name)
	{
		File directory = RegistryPlugin.getInstance().getDataFolder().getAbsoluteFile();
		
		for(int i = 0; i < 3; i++)
		{
			directory = directory.getParentFile();
		}
		
		return new File(directory, "Worlds/" + name);
	}

}
