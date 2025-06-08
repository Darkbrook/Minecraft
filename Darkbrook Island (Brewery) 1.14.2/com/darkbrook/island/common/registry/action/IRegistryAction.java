package com.darkbrook.island.common.registry.action;

import com.darkbrook.island.common.registry.IRegistryValue;
import com.darkbrook.island.common.registry.RegistryPlugin;

public interface IRegistryAction extends IRegistryValue
{
	public void onEnable(RegistryPlugin plugin);
	
	public void onDisable(RegistryPlugin plugin);
}
