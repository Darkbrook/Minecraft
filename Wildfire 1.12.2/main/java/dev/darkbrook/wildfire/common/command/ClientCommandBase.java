package dev.darkbrook.wildfire.common.command;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.IClientCommand;

public abstract class ClientCommandBase implements IClientCommand
{
	public ClientCommandBase()
	{
		ClientCommandHandler.instance.registerCommand(this);
	}
	
	@Override
	public List<String> getAliases()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender,
			String[] args, BlockPos targetPos)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

	@Override
	public int compareTo(ICommand command)
	{
		return getName().compareTo(command.getName());
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message)
	{
		return false;
	}
}
