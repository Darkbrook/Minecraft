package com.darkbrook.island.common.registry.visual.local;

import org.bukkit.entity.Player;

import com.darkbrook.island.common.registry.RegistryManager;

public class VisualRegistryLocal extends RegistryManager<IRegistryVisualLocal> implements IRegistryVisualLocal
{

	public VisualRegistryLocal() 
	{
		super(IRegistryVisualLocal.class);
	}

	@Override
	public void playLocal(Player player) 
	{
		values.forEach(visual -> visual.playLocal(player));
	}
	
}
