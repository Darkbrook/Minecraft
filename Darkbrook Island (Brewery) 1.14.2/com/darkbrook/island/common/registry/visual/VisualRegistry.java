package com.darkbrook.island.common.registry.visual;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.IRegistryManager;
import com.darkbrook.island.common.registry.IRegistryValue;
import com.darkbrook.island.common.registry.visual.global.VisualRegistryGlobal;
import com.darkbrook.island.common.registry.visual.global.IRegistryVisualGlobal;
import com.darkbrook.island.common.registry.visual.local.VisualRegistryLocal;

public class VisualRegistry implements IRegistryManager, IRegistryVisualGlobal
{

	private VisualRegistryGlobal global;
	private VisualRegistryLocal local;
		
	public VisualRegistry() 
	{
		global = new VisualRegistryGlobal();
		local = new VisualRegistryLocal();
	}

	@Override
	public void playLocal(Player player) 
	{
		global.playLocal(player);
		local.playLocal(player);
	}

	@Override
	public void playGlobal(Player player)
	{
		global.playGlobal(player);
	}

	@Override
	public void playGlobal(Location location) 
	{
		global.playGlobal(location);
	}

	@Override
	public void initalize(IRegistryValue value) 
	{
		global.initalize(value);
		local.initalize(value);
	}

}
