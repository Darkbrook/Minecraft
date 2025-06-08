package com.darkbrook.island.common.registry.visual.global;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.RegistryManager;

public class VisualRegistryGlobal extends RegistryManager<IRegistryVisualGlobal> implements IRegistryVisualGlobal
{

	public VisualRegistryGlobal() 
	{
		super(IRegistryVisualGlobal.class);
	}

	@Override
	public void playLocal(Player player) 
	{
		values.forEach(visual -> visual.playLocal(player));
	}

	@Override
	public void playGlobal(Player player) 
	{
		values.forEach(visual -> visual.playGlobal(player));
	}

	@Override
	public void playGlobal(Location location) 
	{
		values.forEach(visual -> visual.playGlobal(location));
	}

}
