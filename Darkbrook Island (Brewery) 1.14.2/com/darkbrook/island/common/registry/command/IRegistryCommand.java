package com.darkbrook.island.common.registry.command;

import com.darkbrook.island.common.registry.IRegistryValue;

public interface IRegistryCommand extends IRegistryValue
{	
	public boolean onCommandExecute(CommandUser user, String[] arguments, int length);
	
	public String getPrefix();
}
