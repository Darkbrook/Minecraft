package com.darkbrook.island.common.registry.action;

import com.darkbrook.island.common.registry.IRegistryValue;
import com.darkbrook.island.common.registry.RegistryPlugin;

public interface IRegistryInstantAction extends IRegistryValue
{
	public void onInitialize(RegistryPlugin plugin);
}
